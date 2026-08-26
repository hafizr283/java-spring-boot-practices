package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.model.Order;
import com.example.repository.OrderRepository;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepo;
    @Autowired
    // @Qualifier("Nagad")

    private Map<String, PaymentService> payment;
    @Autowired
    private List<Notification> notify = new ArrayList<>();
    private Map<String, PaymentService> lookup;

    // @Autowired

    // public OrderService(OrderRepository orderRepo, @Qualifier("cardPayment")
    // PaymentService payment,
    // Notification notify) {
    // this.orderRepo = orderRepo;
    // this.payment = payment;
    // this.notify = notify;
    // }
    // @Autowired
    // public void setOrderRepo(OrderRepository orderRepo) {
    // this.orderRepo = orderRepo;
    // }

    // @Autowired
    // @Qualifier("cardPayment")
    // public void setPayment(PaymentService payment) {
    // this.payment = payment;
    // }

    // @Autowired
    // public void setNotify(Notification notify) {
    // System.out.println(notify);
    // System.out.println("set Notify function called with value");
    // this.notify = notify;
    // }

    @PostConstruct
    public void init() {
        if (payment.size() == 0)
            System.out.println("Nothing in the map");
        System.out.println(payment.size());
        System.out.println("----Lifecycle[] OrderService initiated---------");
        lookup();
    }

    public void lookup() {
        lookup = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        lookup.putAll(payment);
        System.out.println("--------Another postconstruct------");
    }

    @PreDestroy
    public void cleanup() {
        System.out.println("----- Lifecycle[] OrderService destroyed ------");
    }

    public void orderHistory() {
        List<Order> orders = orderRepo.getOrders();
        for (Order order : orders) {
            System.out.println(
                    order.getOrderId() + " | " +
                            order.getCustomerName() + " | " +
                            order.getItem() + " | " +
                            order.getAmount());
        }

    }

    public void checkout(String orderId, String customerName, String item, double amount, String paymentMethod) {
        PaymentService ps = lookup.get(paymentMethod);
        if (ps == null) {
            System.out.println("Unknown method");
            System.out.println("Available methods" + lookup.keySet());

        } else {
            boolean isCompletePayment = ps.processPayment(amount);
            if (isCompletePayment) {
                // ps.processPayment(amount);

                Order o = new Order(orderId, customerName, item, amount);
                orderRepo.save(o);
                for (Notification noti : notify) {

                    noti.sendNotification(customerName, item);
                    if (noti instanceof Email mail) {
                        mail.sendFormatterd(item, amount);
                    }
                }
            } else {
                System.out.println("Payment failed");
            }

        }

    }

}
