package org.example.toyroom.models.toys;

import org.example.toyroom.models.MyColor;
import org.example.toyroom.models.Size;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DollTest {

    @Test
    void testDollConstructorSetsCorrectFields() {
        Size size = Size.SMALL;
        MyColor color = new MyColor("#FF00FF");
        String material = "Fabric";

        Doll doll = new Doll(size, color, material);

        assertEquals("doll", doll.getType());
        assertEquals(size, doll.getSize());
        assertEquals(color, doll.getColor());
        assertEquals(material, doll.getMaterial());
        assertEquals("/images/doll.png", doll.getImagePath());
    }

    @Test
    void testDefaultConstructor() {
        Doll doll = new Doll();
        assertNotNull(doll);
    }
}
