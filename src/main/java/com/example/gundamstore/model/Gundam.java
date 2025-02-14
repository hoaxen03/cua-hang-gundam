package com.example.gundamstore.model;

public class Gundam {
    private String id;
    private String name;
    private String series;
    private String model;
    private double price;
    private int stock;
    private String imageUrl;
    private String description; 

    // Constructors
    public Gundam() {
    }

    public Gundam(String id, String name, String series, double price, int stock ,String imageUrl, String description) {
        this.id = id;
        this.name = name;
        this.series = series;
        this.model = model;
        this.price = price;
        this.stock = stock;
        this.imageUrl =imageUrl;
        this.description = description;
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
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
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
                ", imageUrl='"+ imageUrl + '\''+
                ", description='"+ description +'\''+
                '}';
    }
}
