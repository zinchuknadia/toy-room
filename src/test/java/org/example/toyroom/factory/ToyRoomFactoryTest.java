package org.example.toyroom.factory;

import org.example.entity.Theme;
import org.example.toyroom.models.ToyRoom;
import org.example.toyroom.service.ThemeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ToyRoomFactoryTest {

    private ThemeService themeService;
    private ToyRoomFactory factory;

    @BeforeEach
    void setUp() {
        themeService = mock(ThemeService.class);
        factory = new ToyRoomFactory(themeService);
    }

    @Test
    void createToyRoom_ShouldCreateToyRoomWithCorrectFields() {
        // Arrange
        String name = "Adventure Room";
        String themeName = "Space";
        double budget = 500.0;

        Theme mockTheme = new Theme();
        mockTheme.setImage("space.png");

        when(themeService.getThemeByName(themeName)).thenReturn(mockTheme);

        // Act
        ToyRoom toyRoom = factory.createToyRoom(name, themeName, budget);

        // Assert
        assertEquals(name, toyRoom.getName());
        assertEquals(themeName, toyRoom.getThemeName());
        assertEquals("space.png", toyRoom.getThemeImage());
        assertEquals(budget, toyRoom.getBudget());
        assertNotNull(toyRoom.getCreatedAt());
        assertNotNull(toyRoom.getUpdatedAt());
        assertTrue(toyRoom.getCreatedAt() instanceof LocalDateTime);
        assertTrue(toyRoom.getUpdatedAt() instanceof LocalDateTime);

        verify(themeService, times(1)).getThemeByName(themeName);
    }
}
