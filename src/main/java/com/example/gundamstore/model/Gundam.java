package com.example.gundamstore.model;

public class Gundam {
    private String id;
    private String name;
    private String series;
    private String model;
    private double price;
    private int stock;

    // Constructors
    public Gundam() {
    }

    public Gundam(String id, String name, String series, double price, int stock) {
        this.id = id;
        this.name = name;
        this.series = series;
        this.model = model;
        this.price = price;
        this.stock = stock;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    // toString method
    @Override
    public String toString() {
        return "Gundam{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", series='" + series + '\'' +
                ", model='" + model + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                '}';
    }
}
