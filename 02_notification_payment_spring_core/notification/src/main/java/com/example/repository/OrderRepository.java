package com.example.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.model.Order;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Repository
public class OrderRepository {

    private final List<Order> database = new ArrayList<>();

    @PostConstruct
    void init() {
        System.out.println("In OrderRepository class initiated");
    }

    @PreDestroy
    void clean() {
        System.out.println("In OrderRepository class destroyed");
    }

    public void save(Order o) {
        boolean isOrderIdExist = false;
        for (Order order : database) {
            if (order.getOrderId().equals(o.getOrderId())) {
                isOrderIdExist = true;
                break;
            }
        }
        if (isOrderIdExist)
            System.out.println("Order Id exists already. Try again");
        else {
            database.add(o);
            System.out.println("Order ID added successfully");
        }

    }

    public int count() {
        return database.size();
    }

    public List<Order> getOrders() {
        return this.database;
    }
}
