package com.example;

import java.io.BufferedInputStream;
import java.util.Scanner;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.example.config.AppConfig;
import com.example.repository.OrderRepository;
import com.example.service.OrderService;

public class Main {
    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        OrderService orderService = context.getBean(OrderService.class);
        // OrderService orderService=new OrderService();
        // orderService.checkout("Order 001", "Hamim ", "cig", 50.0);
        // orderService.orderHistory();
        // orderService.checkout("Order 01", " Hamim ", " cigarette ", 13.0);
        // orderService.checkout("Order 02", " Hamim ", " coffe ", 8.0);
        // orderService.checkout("Order 02", " Hamim ", " coffe ", 8.0);
        // orderService.orderHistory();
        Scanner s = new Scanner(System.in);
        System.out.println("Enter order id");
        String orderid = s.nextLine();
        System.out.println("Enter customer name");
        String cus_name = s.nextLine();
        System.out.println("Enter item name");
        String item = s.nextLine();
        System.out.println("Enter amount like 100.0");
        double amount = s.nextDouble();
        System.out.println("Enter payment method bkash/card - case insensitive");
        s.nextLine();
        String paymentMethod = s.nextLine();

        orderService.checkout(orderid, cus_name, item, amount, paymentMethod);

        OrderRepository orderRepository = context.getBean(OrderRepository.class);
        System.out.println("Total order" + orderRepository.count());
        // System.out.println("Hello world!");
        // int num = System.in.read();
        // System.out.println(num-48);
        context.close();
    }
}