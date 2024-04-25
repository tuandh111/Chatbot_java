package com.tuandhpc05076.client;


import com.tuandhpc05076.config.TelegramConfig;
import com.tuandhpc05076.core.Agent;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TelegramInbox {

    TelegramBot bot = new TelegramBot(TelegramConfig.TOKEN);

    @Autowired
    Agent agent;
    // check message inbox

    @PostConstruct
    public void getUnreadMessages() {
        bot.setUpdatesListener(new UpdatesListener() {
            @Override
            public int process(List<Update> list) {
                for (Update update : list) {
                    try {
                        agent.generateReply(update.message());
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("warning");
                    }
                }
                return -1;
            }
        });
    }

}
