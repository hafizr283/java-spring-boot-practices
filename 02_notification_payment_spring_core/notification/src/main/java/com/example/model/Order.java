package com.example.model;

public class Order {
    private String orderId;
    private String customerName;
    private String item;
    private double amount;

    public Order(String orderId, String customerName, String item, double amount) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.item = item;
        this.amount = amount;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getItem() {
        return item;
    }

    public double getAmount() {
        return amount;
    }

}
