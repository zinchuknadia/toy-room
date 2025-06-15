package org.example.toyroom.models;

import org.example.toyroom.factory.ToyFactory;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ToyFactoryTest {

    @Test
    void testGetToyTypesReturnsExpectedTypes() {
        Set<String> types = ToyFactory.getToyTypes();
        assertEquals(4, types.size());
        assertTrue(types.containsAll(List.of("Doll", "Car", "Ball", "Duck")));
    }

    @Test
    void testCreateToyReturnsCorrectSubclass() {
        Toy doll = ToyFactory.createToy("Doll", Size.SMALL, new MyColor("#FFFFFF"), "Plastic");
        assertTrue(doll instanceof Doll);

        Toy car = ToyFactory.createToy("Car", Size.MEDIUM, new MyColor("#000000"), "Metal");
        assertTrue(car instanceof Car);
    }

    @Test
    void testCreateToyThrowsOnUnknownType() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                ToyFactory.createToy("Robot", Size.LARGE, new MyColor("#123456"), "Steel")
        );
        assertTrue(exception.getMessage().contains("Unknown toy type"));
    }

    @Test
    void testGetPriceReturnsCorrectValues() {
        assertEquals(15.0, ToyFactory.getPrice("Doll"));
        assertEquals(25.0, ToyFactory.getPrice("Car"));
        assertEquals(10.0, ToyFactory.getPrice("Ball"));
        assertEquals(5.0, ToyFactory.getPrice("Duck"));
        assertEquals(0.0, ToyFactory.getPrice("Robot"));
    }

    @Test
    void testGetImagePathReturnsCorrectPaths() {
        assertEquals("/images/doll.png", ToyFactory.getImagePath("Doll"));
        assertEquals("/images/car.png", ToyFactory.getImagePath("Car"));
        assertEquals("/images/ball.png", ToyFactory.getImagePath("Ball"));
        assertEquals("/images/duck.png", ToyFactory.getImagePath("Duck"));
        assertEquals("", ToyFactory.getImagePath("Robot"));
    }
}
