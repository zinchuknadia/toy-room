package org.example.toyroom.models.toys;

import org.example.toyroom.models.MyColor;
import org.example.toyroom.models.Size;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarTest {

    @Test
    void testCarConstructorSetsCorrectFields() {
        Size size = Size.MEDIUM;
        MyColor color = new MyColor("#123456");
        String material = "Plastic";

        Car car = new Car(size, color, material);

        assertEquals("car", car.getType());
        assertEquals(size, car.getSize());
        assertEquals(color, car.getColor());
        assertEquals(material, car.getMaterial());
        assertEquals("/images/car.png", car.getImagePath());
    }

    @Test
    void testDefaultConstructor() {
        Car car = new Car();
        assertNotNull(car);
    }
}
