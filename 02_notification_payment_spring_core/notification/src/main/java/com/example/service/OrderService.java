package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.model.Order;
import com.example.repository.OrderRepository;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Service
public class OrderService {
    private final OrderRepository orderRepo;
    private final PaymentService payment;
    private final Notification notify;

    @Autowired

    public OrderService(OrderRepository orderRepo, @Qualifier("bkashPayment") PaymentService payment,
            Notification notify) {
        this.orderRepo = orderRepo;
        this.payment = payment;
        this.notify = notify;
    }

    @PostConstruct
    public void init() {
        System.out.println("Lifecycle[] OrderService initiated");
    }

    @PreDestroy
    public void cleanup() {
        System.out.println("Lifecycle[] destroyed");
    }

    public void checkout(String orderId, String customerName, String item, double amount) {
        boolean isCompletePayment = payment.processPayment(amount);
        if (isCompletePayment) {
            Order o = new Order(orderId, customerName, item, amount);
            orderRepo.save(o);
            notify.sendNotification(customerName, item);
        } else {
            System.out.println("Payment failed");
        }

    }

}
