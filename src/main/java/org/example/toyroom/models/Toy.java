package org.example.toyroom.models;

import javafx.beans.property.*;

public class Toy {
    private int id;

    private final StringProperty type = new SimpleStringProperty();
    private final ObjectProperty<Size> size = new SimpleObjectProperty<>();
    private final ObjectProperty<Color> color = new SimpleObjectProperty<>();
    private final StringProperty material = new SimpleStringProperty();
    private final DoubleProperty price = new SimpleDoubleProperty();

//    private String type;
//    private Size size;
//    private Color color;
//    private String material;

    public Toy(){}

    public Toy(String type, Size size, Color color, String material, double price) {
        this.type.set(type);
        this.size.set(size);
        this.color.set(color);
        this.material.set(material);
        this.price.set(price);
    }

    // ======= ID — тільки для БД =======
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // ======= Type =======
    public String getType() {
        return type.get();
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public StringProperty typeProperty() {
        return type;
    }

    // ======= Size =======
    public Size getSize() {
        return size.get();
    }

    public void setSize(Size size) {
        this.size.set(size);
    }

    public ObjectProperty<Size> sizeProperty() {
        return size;
    }

    // ======= Color =======
    public Color getColor() {
        return color.get();
    }

    public void setColor(Color color) {
        this.color.set(color);
    }

    public ObjectProperty<Color> colorProperty() {
        return color;
    }

    // ======= Material =======
    public String getMaterial() {
        return material.get();
    }

    public void setMaterial(String material) {
        this.material.set(material);
    }

    public StringProperty materialProperty() {
        return material;
    }

    // ======== Price =========
    public double getPrice() {
        return price.get();
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    @Override
    public String toString() {
        return "\n" +
                "type=" + type.get() +
                ", size=" + size.get() +
                ", color=" + color.get() +
                ", material=" + material.get() +
                ", price=" + price.get();
    }
}
