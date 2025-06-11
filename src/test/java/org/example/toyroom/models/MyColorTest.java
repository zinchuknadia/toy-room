package org.example.toyroom.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyColorTest {

    @Test
    void testConstructorAddsHash() {
        MyColor color = new MyColor("FF0000");
        assertEquals("#FF0000", color.getHexCode());
    }

    @Test
    void testConstructorKeepsHash() {
        MyColor color = new MyColor("#00FF00");
        assertEquals("#00FF00", color.getHexCode());
    }

    @Test
    void testGetHexCode() {
        MyColor color = new MyColor("0000FF");
        assertEquals("#0000FF", color.getHexCode());
    }

    @Test
    void testToAwtColor() {
        MyColor color = new MyColor("FF00FF");
        java.awt.Color awtColor = color.toAwtColor();
        assertEquals(255, awtColor.getRed());
        assertEquals(0, awtColor.getGreen());
        assertEquals(255, awtColor.getBlue());
    }

    @Test
    void testToString() {
        MyColor color = new MyColor("CCCCCC");
        assertEquals("#CCCCCC", color.toString());
    }
}
