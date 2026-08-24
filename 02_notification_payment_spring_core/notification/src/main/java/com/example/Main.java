package com.example;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.example.config.AppConfig;
import com.example.service.OrderService;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        OrderService orderService = context.getBean(OrderService.class);
        orderService.checkout("Order 01", "Hamim", "cigarette", 13.0);
        orderService.checkout("Order", "Hamim", "coffe", 8.0);
        System.out.println("Hello world!");
        context.close();
    }
}