package com.example.gundamstore.service;

import com.example.gundamstore.model.Order;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private List<Order> orders = new ArrayList<>();

    public List<Order> getAllOrders() {
        return orders;
    }

    public Order getOrderById(String id) {
        Optional<Order> order = orders.stream().filter(o -> o.getId().equals(id)).findFirst();
        return order.orElse(null);
    }

    public Order createOrder(Order order) {
        orders.add(order);
        return order;
    }

    public Order updateOrder(String id, Order orderDetails) {
        Order order = getOrderById(id);
        if (order != null) {
            order.setCustomer(orderDetails.getCustomer());
            order.setGundams(orderDetails.getGundams());
            order.setOrderDate(orderDetails.getOrderDate());
            order.setTotalAmount(orderDetails.getTotalAmount());
            return order;
        }
        return null;
    }

    public boolean deleteOrder(String id) {
        return orders.removeIf(order -> order.getId().equals(id));
    }
}
