package com.example.service;

public class SendSms implements Notification {
    @Override
    public void sendNotification(String msg, String recipient) {
        System.out.println("SMS send to:" + recipient + msg);
    }

}
