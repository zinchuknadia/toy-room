package org.example.toyroom.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ColorTest {

    @Test
    void testConstructorAddsHash() {
        Color color = new Color("FF0000");
        assertEquals("#FF0000", color.getHexCode());
    }

    @Test
    void testConstructorKeepsHash() {
        Color color = new Color("#00FF00");
        assertEquals("#00FF00", color.getHexCode());
    }

    @Test
    void testGetHexCode() {
        Color color = new Color("0000FF");
        assertEquals("#0000FF", color.getHexCode());
    }

    @Test
    void testToAwtColor() {
        Color color = new Color("FF00FF");
        java.awt.Color awtColor = color.toAwtColor();
        assertEquals(255, awtColor.getRed());
        assertEquals(0, awtColor.getGreen());
        assertEquals(255, awtColor.getBlue());
    }

    @Test
    void testToString() {
        Color color = new Color("CCCCCC");
        assertEquals("#CCCCCC", color.toString());
    }
}
