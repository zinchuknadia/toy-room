package org.example.toyroom.models;

import lombok.Getter;
import lombok.Setter;

public class Toy {

    @Setter
    private int id;
    @Setter
    private String type;
    @Getter @Setter
    private Size size;
    @Getter @Setter
    private String color;

    public Toy(){}

    public Toy(String type, Size size, String color){
        this.type = type;
        this.size = size;
        this.color = color;
    }

    @Override
    public String toString() {
        return "\n" +
                "type= " + type +
                ", Size= " + size +
                ", color=" + color;
    }
}
