package org.example.toyroom.models;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ToyRoomTest {

    @Test
    void defaultConstructorSetsBudgetToZero() {
        ToyRoom toyRoom = new ToyRoom();
        assertEquals(0.0, toyRoom.getBudget());
    }

    @Test
    void constructorWithBudgetSetsBudget() {
        ToyRoom toyRoom = new ToyRoom(150.5);
        assertEquals(150.5, toyRoom.getBudget());
    }

    @Test
    void constructorWithNameThemeBudgetInitializesFields() {
        ToyRoom toyRoom = new ToyRoom("MyRoom", "Space", 200.0);
        assertEquals("MyRoom", toyRoom.getName());
        assertEquals("Space", toyRoom.getThemeName());
        assertEquals(200.0, toyRoom.getBudget());
        assertNotNull(toyRoom.getCreatedAt());
        assertNotNull(toyRoom.getUpdatedAt());
    }

    @Test
    void idGetterSetter() {
        ToyRoom toyRoom = new ToyRoom();
        toyRoom.setId(123L);
        assertEquals(123L, toyRoom.getId());
    }

    @Test
    void namePropertyGetter() {
        ToyRoom toyRoom = new ToyRoom();
        assertNotNull(toyRoom.nameProperty());
        toyRoom.setName("TestName");
        assertEquals("TestName", toyRoom.nameProperty().get());
    }

    @Test
    void themeNamePropertyGetter() {
        ToyRoom toyRoom = new ToyRoom();
        assertNotNull(toyRoom.themeNameProperty());
        toyRoom.setThemeName("Ocean");
        assertEquals("Ocean", toyRoom.themeNameProperty().get());
    }

    @Test
    void themeImagePropertyGetter() {
        ToyRoom toyRoom = new ToyRoom();
        assertNotNull(toyRoom.themeImageProperty());
        toyRoom.setThemeImage("image.png");
        assertEquals("image.png", toyRoom.themeImageProperty().get());
    }

    @Test
    void budgetPropertyGetter() {
        ToyRoom toyRoom = new ToyRoom();
        assertNotNull(toyRoom.budgetProperty());
        toyRoom.setBudget(1234.56);
        assertEquals(1234.56, toyRoom.budgetProperty().get());
    }

    @Test
    void createdAtPropertyGetter() {
        ToyRoom toyRoom = new ToyRoom();
        LocalDateTime now = LocalDateTime.now();
        toyRoom.setCreatedAt(now);
        assertEquals(now, toyRoom.createdAtProperty().get());
    }

    @Test
    void updatedAtPropertyGetter() {
        ToyRoom toyRoom = new ToyRoom();
        LocalDateTime now = LocalDateTime.now();
        toyRoom.setUpdatedAt(now);
        assertEquals(now, toyRoom.updatedAtProperty().get());
    }

    @Test
    void toysGetterSetter() {
        ToyRoom toyRoom = new ToyRoom();
        List<Toy> toys = List.of(new Toy("Car", Size.SMALL, new MyColor("#FFFFFF"), "Plastic"));
        toyRoom.setToys(toys);
        assertEquals(toys, toyRoom.getToys());
    }

    @Test
    void toStringReturnsNameAndBudget() {
        ToyRoom toyRoom = new ToyRoom("Room1", "Theme1", 999.99);
        String expected = "ToyRoom: Room1, Budget: $999.99";
        assertEquals(expected, toyRoom.toString());
    }
}
