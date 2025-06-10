package org.example.toyroom.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ToyTest {

    @Test
    void testConstructorSetsAllFields() {
        Color color = new Color("FF0000");
        Size size = Size.MEDIUM;
        Toy toy = new Toy("Bear", size, color, "Cotton");

        assertEquals("Bear", toy.getType());
        assertEquals(size, toy.getSize());
        assertEquals(color, toy.getColor());
        assertEquals("Cotton", toy.getMaterial());
    }

    @Test
    void testSettersAndGetters() {
        Toy toy = new Toy();

        toy.setType("Car");
        toy.setSize(Size.SMALL);
        toy.setColor(new Color("#00FF00"));
        toy.setMaterial("Plastic");

        assertEquals("Car", toy.getType());
        assertEquals(Size.SMALL, toy.getSize());
        assertEquals("#00FF00", toy.getColor().getHexCode());
        assertEquals("Plastic", toy.getMaterial());
    }

    @Test
    void testIdGetterSetter() {
        Toy toy = new Toy();
        toy.setId(42);
        assertEquals(42, toy.getId());
    }

    @Test
    void testPropertyNotNull() {
        Toy toy = new Toy();
        assertNotNull(toy.typeProperty());
        assertNotNull(toy.sizeProperty());
        assertNotNull(toy.colorProperty());
        assertNotNull(toy.materialProperty());
    }

    @Test
    void testToStringContainsKeyFields() {
        Toy toy = new Toy("Doll", Size.LARGE, new Color("123456"), "Wool");
        String str = toy.toString();

        assertTrue(str.contains("Doll"));
        assertTrue(str.contains("LARGE"));
        assertTrue(str.contains("#123456"));
    }
}
