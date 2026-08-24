package com.example.service;

import org.springframework.stereotype.Component;

@Component
public class Email implements Notification {
    @Override
    public void sendNotification(String msg, String recipient) {
        System.out.println("Email: " + msg + "sent to" + recipient);
    }

}
