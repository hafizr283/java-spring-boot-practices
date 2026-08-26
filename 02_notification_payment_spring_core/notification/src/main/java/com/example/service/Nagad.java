package com.example.service;

import org.springframework.stereotype.Component;

@Component("Nagad")
public class Nagad implements PaymentService {
    @Override
    public boolean processPayment(double amount) {
        System.out.println("From Nagad");
        return true;
    }
}
