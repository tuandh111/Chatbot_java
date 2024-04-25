package com.tuandhpc05076.core;

import com.tuandhpc05076.client.TelegramDelivery;
import com.tuandhpc05076.config.TelegramConfig;
import com.tuandhpc05076.contacts.Conversation;
import com.tuandhpc05076.core.knowledge.KnowledgeBase;
import com.tuandhpc05076.core.lifecycle.DecisionEngine;
import com.tuandhpc05076.core.nlp.TextAnalyzer;
import com.tuandhpc05076.helper.MessageParser;
import com.tuandhpc05076.helper.TextUtils;
import com.tuandhpc05076.persistent.RDS;
import com.pengrad.telegrambot.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

@EnableScheduling
@Component
public class Agent {

    @Autowired
    DecisionEngine decisionEngine;

    @Autowired
    KnowledgeBase knowledgeBase;

    @Autowired
    MessageParser messageParser;

    @Autowired
    TelegramDelivery telegramDelivery;

    @Autowired
    TextUtils textUtils;

    @Autowired
    RDS rds;

    @Autowired
    TextAnalyzer textAnalyzer;
    String answer;

    public void generateReply(Message message) throws Exception {
        String conversationState = decisionEngine.decideCurrentConversationState(message.text()).getName();
        Conversation conversation = new Conversation(String.valueOf(message.chat().id()), message.chat().username(), conversationState);
        switch (conversationState) {
            case "Greetings" -> {
                answer = KnowledgeBase.HELLO_MESSAGE;
            }
            case "Question" -> {
                System.out.println("this is question");
                answer = knowledgeBase.getdataFromSolr(textUtils.extractWordsAfterAbout(message.text()));
                if (answer.isEmpty()) {
                    answer = KnowledgeBase.DEFAULT_IDK;
                    telegramDelivery.sendMessage(conversation, answer);
                    answer = knowledgeBase.searchForMoreKnowledge(message.text());
                }else {
                    answer = textAnalyzer.extractRelevantSentences(answer, message.text(), 1).get(0);
                }
            }
            default -> answer = KnowledgeBase.DEFAULT_ANSWER;
        }

        // print to console
        messageParser.printMessage(TelegramConfig.MESSAGE_RECEIVED, message);

        // save to database
        //rds.saveMessage(conversation, answer, TelegramConfig.MESSAGE_RECEIVED);

        // deliver the message
        telegramDelivery.sendMessage(conversation, answer);

        telegramDelivery.sendMessage(conversation, decisionEngine.sentimentAnalyzerMessage(message.text()));


    }

}
