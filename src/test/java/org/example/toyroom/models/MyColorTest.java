package org.example.toyroom.models;

import org.junit.jupiter.api.Test;
import java.awt.Color;
import static org.junit.jupiter.api.Assertions.*;

class MyColorTest {

    @Test
    void constructor_shouldNormalizeHexCode() {
        MyColor color1 = new MyColor("#123456");
        assertEquals("#123456", color1.getHexCode());

        MyColor color2 = new MyColor("123456");
        assertEquals("#123456", color2.getHexCode());
    }

    @Test
    void toAwtColor_shouldReturnCorrectColor() {
        MyColor myColor = new MyColor("#FF0000");
        Color awtColor = myColor.toAwtColor();
        assertEquals(Color.RED.getRed(), awtColor.getRed());
        assertEquals(Color.RED.getGreen(), awtColor.getGreen());
        assertEquals(Color.RED.getBlue(), awtColor.getBlue());
    }

    @Test
    void equals_and_hashCode_shouldBehaveCorrectly() {
        MyColor c1 = new MyColor("#abcdef");
        MyColor c2 = new MyColor("abcdef");
        MyColor c3 = new MyColor("#123456");

        assertEquals(c1, c2);
        assertEquals(c1.hashCode(), c2.hashCode());

        assertNotEquals(c1, c3);
        assertNotEquals(c1.hashCode(), c3.hashCode());
    }

    @Test
    void toString_shouldReturnHexCode() {
        MyColor c = new MyColor("#789abc");
        assertEquals("#789abc", c.toString());
    }
}
