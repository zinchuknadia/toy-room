package org.example.toyroom.models.toys;

import org.example.toyroom.models.MyColor;
import org.example.toyroom.models.Size;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ToyTest {

    @Test
    void testConstructorWithoutPriceSetsFieldsCorrectly() {
        MyColor color = new MyColor("FF0000");
        Size size = Size.MEDIUM;
        String imagePath = "images/bear.png";
        Toy toy = new Toy("Bear", size, color, "Cotton", imagePath);

        assertEquals("Bear", toy.getType());
        assertEquals(size, toy.getSize());
        assertEquals(color, toy.getColor());
        assertEquals("Cotton", toy.getMaterial());
        assertEquals(imagePath, toy.getImagePath());
        assertEquals(0.0, toy.getPrice()); // default
    }

    @Test
    void testConstructorWithPriceSetsFieldsCorrectly() {
        MyColor color = new MyColor("00FF00");
        Size size = Size.LARGE;
        String imagePath = "images/car.png";
        double price = 29.99;
        Toy toy = new Toy("Car", size, color, "Plastic", price, imagePath);

        assertEquals("Car", toy.getType());
        assertEquals(size, toy.getSize());
        assertEquals(color, toy.getColor());
        assertEquals("Plastic", toy.getMaterial());
        assertEquals(price, toy.getPrice());
        assertEquals(imagePath, toy.getImagePath());
    }

    @Test
    void testSettersAndGetters() {
        Toy toy = new Toy();

        toy.setType("Doll");
        toy.setSize(Size.SMALL);
        toy.setColor(new MyColor("#123456"));
        toy.setMaterial("Wool");
        toy.setPrice(15.50);
        toy.setImagePath("images/doll.png");

        assertEquals("Doll", toy.getType());
        assertEquals(Size.SMALL, toy.getSize());
        assertEquals("#123456", toy.getColor().getHexCode());
        assertEquals("Wool", toy.getMaterial());
        assertEquals(15.50, toy.getPrice());
        assertEquals("images/doll.png", toy.getImagePath());
    }

    @Test
    void testIdGetterSetter() {
        Toy toy = new Toy();
        toy.setId(101);
        assertEquals(101, toy.getId());
    }

    @Test
    void testPropertiesNotNull() {
        Toy toy = new Toy();

        assertNotNull(toy.typeProperty());
        assertNotNull(toy.sizeProperty());
        assertNotNull(toy.colorProperty());
        assertNotNull(toy.materialProperty());
        assertNotNull(toy.priceProperty());
    }

    @Test
    void testToStringContainsAllKeyFields() {
        Toy toy = new Toy("Plane", Size.LARGE, new MyColor("#ABCDEF"), "Metal", 49.99, "images/plane.png");
        String str = toy.toString();

        assertTrue(str.contains("Plane"));
        assertTrue(str.contains("LARGE"));
        assertTrue(str.contains("#ABCDEF"));
        assertTrue(str.contains("Metal"));
        assertTrue(str.contains("49.99"));
        assertTrue(str.contains("images/plane.png"));
    }
}
