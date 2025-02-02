package com.example.gundamstore.model;

import java.util.Date;
import java.util.List;

public class Order {
    private String id;
    private int customerId;
    private Customer customer;
    private List<Gundam> gundams;
    private Date orderDate;
    private double totalAmount;

    // Constructors
    public Order() {
    }

    public Order(String id, Customer customer, List<Gundam> gundams, Date orderDate, double totalAmount) {
        this.id = id;
        this.customer = customer;
        this.gundams = gundams;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Gundam> getGundams() {
        return gundams;
    }

    public void setGundams(List<Gundam> gundams) {
        this.gundams = gundams;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    // toString method
    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", customerId=" + customerId +
                ", customer=" + customer +
                ", gundams=" + gundams +
                ", orderDate=" + orderDate +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
