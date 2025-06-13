package org.example.toyroom;

import javafx.beans.property.DoubleProperty;
import org.example.toyroom.models.Size;
import org.example.toyroom.models.ToyRoom;
import org.example.toyroom.service.ToyService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class ToyRoomTest {

    @Test
    public void testConstructor_InitializesToyServiceAndBudget() {
        ToyRoom toyRoom = new ToyRoom(100.0);
        assertNotNull(toyRoom.getToyService());
        assertEquals(100.0, toyRoom.getBudget());
    }

    @Test
    public void testBudgetProperty_SetAndGet() {
        ToyRoom toyRoom = new ToyRoom();
        toyRoom.setBudget(50.0);
        assertEquals(50.0, toyRoom.getBudget());
    }

    @Test
    public void testParseSize_ValidSizes() {
        assertEquals(Size.LARGE, ToyRoom.parseSize("large"));
        assertEquals(Size.MEDIUM, ToyRoom.parseSize("medium"));
        assertEquals(Size.SMALL, ToyRoom.parseSize("small"));
    }

    @Test
    public void testParseSize_InvalidSize_ThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            ToyRoom.parseSize("gigantic");
        });
        assertTrue(exception.getMessage().contains("Invalid Size"));
    }
    @Test
    public void testConstructor_WithToyServiceOnly() {
        ToyService mockService = mock(ToyService.class);
        ToyRoom toyRoom = new ToyRoom(mockService);

        assertEquals(mockService, toyRoom.getToyService());
        assertEquals(0.0, toyRoom.getBudget());
    }
    @Test
    public void testConstructor_WithToyServiceAndBudget() {
        ToyService mockService = mock(ToyService.class);
        ToyRoom toyRoom = new ToyRoom(mockService, 75.0);

        assertEquals(mockService, toyRoom.getToyService());
        assertEquals(75.0, toyRoom.getBudget());
    }
    @Test
    public void testBudgetProperty_ObservableProperty() {
        ToyRoom toyRoom = new ToyRoom();
        DoubleProperty budgetProperty = toyRoom.budgetProperty();

        assertNotNull(budgetProperty);
        assertEquals(0.0, budgetProperty.get());

        budgetProperty.set(42.5);
        assertEquals(42.5, toyRoom.getBudget());
    }

}
