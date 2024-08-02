package com.tuandhpc05076.rest;


import com.tuandhpc05076.client.TelegramDelivery;
import com.tuandhpc05076.contacts.Conversation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TelegramReq {

    @Autowired
    TelegramDelivery telegramDelivery;

    @GetMapping("/hi/{inputMessage}")
    public String hello(@PathVariable("inputMessage") String inputMessage)
            throws Exception {
        telegramDelivery.sendMessage(
                new Conversation("185790419", "", "manual"),
                inputMessage);
        return "hi";
    }


}
