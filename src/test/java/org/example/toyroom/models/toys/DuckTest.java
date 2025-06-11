package org.example.toyroom.models.toys;

import org.example.toyroom.models.MyColor;
import org.example.toyroom.models.Size;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DuckTest {

    @Test
    void testDuckConstructorSetsCorrectFields() {
        Size size = Size.LARGE;
        MyColor color = new MyColor("#00FFFF");
        String material = "Rubber";

        Duck duck = new Duck(size, color, material);

        assertEquals("duck", duck.getType());
        assertEquals(size, duck.getSize());
        assertEquals(color, duck.getColor());
        assertEquals(material, duck.getMaterial());
        assertEquals("/images/duck.png", duck.getImagePath());
    }

    @Test
    void testDefaultConstructor() {
        Duck duck = new Duck();
        assertNotNull(duck);
    }
}
