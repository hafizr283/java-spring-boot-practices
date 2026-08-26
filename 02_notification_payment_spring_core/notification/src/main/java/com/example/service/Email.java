package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.MessageCodeFormatter;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Component
public class Email implements Notification {
    private final MassegeFormatter formatter;

    @Override
    public void sendNotification(String msg, String recipient) {
        System.out.println("Email: " + msg + "sent to" + recipient);
    }

    public Email(MassegeFormatter formatter) {
        this.formatter = formatter;
    }

    public void sendFormatterd(String msg, double amount) {
        String s = formatter.getFormatedMessage(msg, amount);
        System.out.println(s);
    }

    @PostConstruct
    void init() {
        System.out.println("In Email class initiated");
    }

    @PreDestroy
    void clean() {
        System.out.println("In Email class destroyed");
    }

}
