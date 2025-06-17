package org.example.toyroom.models;

public class TypeInfo {
    private Long id;
    private String name;
    private String image;
    private double price;

    public TypeInfo(String name, String image, double price) {
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public TypeInfo(){}

    // Getters and setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    @Override
    public String toString() {
        return getName();
    }
}
