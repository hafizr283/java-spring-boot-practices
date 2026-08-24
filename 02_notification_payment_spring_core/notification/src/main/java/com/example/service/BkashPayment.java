package com.example.service;

import org.springframework.stereotype.Component;

@Component("bkashPayment")
public class BkashPayment implements PaymentService {
    @Override
    public boolean processPayment(double amount) {
        System.out.println("Bkash payment" + amount);
        return true;
    }

}
