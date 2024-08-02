package com.tuandhpc05076.client;


import com.tuandhpc05076.config.TelegramConfig;
import com.tuandhpc05076.contacts.Conversation;
import com.tuandhpc05076.helper.MessageParser;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TelegramDelivery {


    @Autowired
    MessageParser messageParser;
    TelegramBot bot = new TelegramBot(TelegramConfig.TOKEN);

    // send message
    public void sendMessage(Conversation conversation, String rawMessage) {
        SendMessage sendRequest = new SendMessage(conversation.getChatId(), rawMessage);
        SendResponse sendResponse = bot.execute(sendRequest);
        if (sendResponse.isOk()) {
            messageParser.printMessage(TelegramConfig.MESSAGE_SENT, sendResponse.message());
        } else {
            System.out.println("Could not send message at this moment.");
        }

    }


}
