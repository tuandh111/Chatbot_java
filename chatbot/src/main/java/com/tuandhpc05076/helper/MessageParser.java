package com.tuandhpc05076.helper;

import com.pengrad.telegrambot.model.Message;
import org.springframework.stereotype.Service;

@Service
public class MessageParser {

    public void printMessage(String messageType, Message message) {
        System.out.println(message.chat().username());
        String hrMessage = "messageType "+messageType +"Id "+
                message.chat().id() +
                ", " +"Username "+
                message.chat().username() +
                " -> " +"Text "+
                message.text();
        System.out.println(hrMessage);
    }
}
