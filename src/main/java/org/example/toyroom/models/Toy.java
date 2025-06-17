package org.example.toyroom.models;

import javafx.beans.property.*;

public class Toy {
    private Long id;
    private Long roomId;

    private final StringProperty type = new SimpleStringProperty();
    private final ObjectProperty<Size> size = new SimpleObjectProperty<>();
    private final ObjectProperty<MyColor> color = new SimpleObjectProperty<>();
    private final StringProperty material = new SimpleStringProperty();
    private final DoubleProperty price = new SimpleDoubleProperty();
    private String imagePath;

    public Toy() {}

    public Toy(Long id, Long roomId, String type, Size size, MyColor color, String material) {
        this.id = id;
        this.roomId = roomId;
        this.type.set(type);
        this.size.set(size);
        this.color.set(color);
        this.material.set(material);
    }

    public Toy(String type, Size size, MyColor color, String material) {
        this.type.set(type);
        this.size.set(size);
        this.color.set(color);
        this.material.set(material);
    }

    public String getImagePath(){
        return imagePath;
    }

    public void setImagePath(String imagePath){
        this.imagePath = imagePath;
    }

    // ======= ID =======
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
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
    public MyColor getColor() {
        return color.get();
    }

    public void setColor(MyColor color) {
        this.color.set(color);
    }

    public ObjectProperty<MyColor> colorProperty() {
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
                ", size=" + size.get().name() +
                ", color=" + color.get() +
                ", material=" + material.get() +
                ", price=" + price.get() +
                ", imagePath=" + imagePath;
    }
}
