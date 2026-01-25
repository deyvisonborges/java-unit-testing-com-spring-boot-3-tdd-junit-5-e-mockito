package org.java;

import java.time.LocalDateTime;
import java.util.UUID;

public class OrderService {
    public Order createOrder(String productName, Long amount, String orderId) {
        Order order = new Order();
        order.setId(orderId == null ? UUID.randomUUID().toString() : orderId);
        order.setCreationDate(LocalDateTime.now());
        order.setAmount(amount);
        order.setProductName(productName);
        return order;
    }
}
