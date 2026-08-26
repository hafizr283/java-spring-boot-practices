package com.example.service;

import org.springframework.stereotype.Component;

@Component
public class MassegeFormatter {

    public String getFormatedMessage(String item, double amount) {
        return item + amount + " is formatted";
    }

}
