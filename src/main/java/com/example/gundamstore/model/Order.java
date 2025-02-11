package com.example.gundamstore.model;

import java.util.Date;

public class Order {
    private int id;
    private int customerId;
    private Date orderDate;
    private double totalAmount;
    private String customer;
    private String gundams;

    // Constructors, getters, and setters

    public Order() {
    }

    public Order(int id, int customerId, Date orderDate, double totalAmount, String customer, String gundams) {
        this.id = id;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.customer = customer;
        this.gundams = gundams;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
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

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getGundams() {
        return gundams;
    }

    public void setGundams(String gundams) {
        this.gundams = gundams;
    }

    // toString method
    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", orderDate=" + orderDate +
                ", totalAmount=" + totalAmount +
                ", customer='" + customer + '\'' +
                ", gundams='" + gundams + '\'' +
                '}';
    }
}