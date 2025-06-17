package org.example.toyroom.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ToyTest {

    @Test
    void testConstructorAndGetters() {
        MyColor color = new MyColor("#FF0000");
        Toy toy = new Toy(1L, 2L, "Car", Size.MEDIUM, color, "Plastic");
        toy.setPrice(9.99);
        toy.setImagePath("car.png");

        assertEquals(1L, toy.getId());
        assertEquals(2L, toy.getRoomId());
        assertEquals("Car", toy.getType());
        assertEquals(Size.MEDIUM, toy.getSize());
        assertEquals(color, toy.getColor());
        assertEquals("Plastic", toy.getMaterial());
        assertEquals(9.99, toy.getPrice());
        assertEquals("car.png", toy.getImagePath());
    }

    @Test
    void testSetters() {
        Toy toy = new Toy();

        MyColor color = new MyColor("#00FF00");
        toy.setId(10L);
        toy.setRoomId(20L);
        toy.setType("Doll");
        toy.setSize(Size.SMALL);
        toy.setColor(color);
        toy.setMaterial("Fabric");
        toy.setPrice(15.5);
        toy.setImagePath("doll.png");

        assertEquals(10L, toy.getId());
        assertEquals(20L, toy.getRoomId());
        assertEquals("Doll", toy.getType());
        assertEquals(Size.SMALL, toy.getSize());
        assertEquals(color, toy.getColor());
        assertEquals("Fabric", toy.getMaterial());
        assertEquals(15.5, toy.getPrice());
        assertEquals("doll.png", toy.getImagePath());
    }

    @Test
    void testProperties() {
        Toy toy = new Toy();
        toy.setType("Robot");
        toy.setSize(Size.LARGE);
        toy.setColor(new MyColor("#123456"));
        toy.setMaterial("Metal");
        toy.setPrice(20.0);

        assertEquals("Robot", toy.typeProperty().get());
        assertEquals(Size.LARGE, toy.sizeProperty().get());
        assertEquals(new MyColor("#123456"), toy.colorProperty().get());
        assertEquals("Metal", toy.materialProperty().get());
        assertEquals(20.0, toy.priceProperty().get());
    }

    @Test
    void testToString() {
        Toy toy = new Toy("Plane", Size.SMALL, new MyColor("#ABCDEF"), "Wood");
        toy.setPrice(12.34);
        toy.setImagePath("plane.png");

        String expected = "\n" +
                "type=Plane" +
                ", size=SMALL" +
                ", color=" + new MyColor("#ABCDEF") +
                ", material=Wood" +
                ", price=12.34" +
                ", imagePath=plane.png";

        assertEquals(expected, toy.toString());
    }
}
