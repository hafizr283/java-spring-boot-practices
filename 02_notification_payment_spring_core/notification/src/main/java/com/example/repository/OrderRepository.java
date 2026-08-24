package com.example.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.model.Order;

@Repository
public class OrderRepository {
    private final List<Order> database = new ArrayList<>();

    public void save(Order o) {
        database.add(o);
        System.out.println("Order " + o.getOrderId());
    }

    public int count() {
        return database.size();
    }
}
