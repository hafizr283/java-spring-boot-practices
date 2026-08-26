package com.example.service;

import org.springframework.stereotype.Component;

@Component("Card")
public class CardPayment implements PaymentService {
    @Override
     public boolean processPayment(double amount){
        System.out.println("Card Payment"+amount);
        return true;
     }
}
