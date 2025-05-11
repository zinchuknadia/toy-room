package org.example.toyroom.models;

public class Toy {
    private int id;
    private String type;
    private Size size;
    private Color color;
    private String material;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public Toy(){}

    public Toy(String type, Size size, Color color, String material) {
        this.type = type;
        this.size = size;
        this.color = color;
        this.material = material;
    }

    @Override
    public String toString() {
        return "\n" +
                "type= " + type +
                ", Size= " + size +
                ", color=" + color;
    }

}
