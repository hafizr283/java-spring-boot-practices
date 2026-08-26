package com.example.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component("bkash")
@Primary
public class BkashPayment implements PaymentService {
    @Override
    public boolean processPayment(double amount) {
        if (amount >= 500.0) {
            System.out.println("Greater that 500.0 not accepted, your amount " + amount);
            return false;
        }

        System.out.println("Bkash payment" + amount);

        return true;
    }

}
