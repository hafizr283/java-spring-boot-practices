package com.example.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class SendSms implements Notification {
    @Override
    public void sendNotification(String msg, String recipient) {
        System.out.println("SMS send to:" + recipient + msg);
    }

}
