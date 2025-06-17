package org.example.toyroom.mappers;

import org.example.entity.Theme;
import org.example.toyroom.models.ThemeInfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ThemeMapperTest {

    @Test
    void testToModel() {
        // Arrange
        Theme theme = new Theme();
        theme.setId(1L);
        theme.setName("Jungle");
        theme.setImage("jungle.png");

        // Act
        ThemeInfo themeInfo = ThemeMapper.toModel(theme);

        // Assert
        assertEquals(1L, themeInfo.getId());
        assertEquals("Jungle", themeInfo.getName());
        assertEquals("jungle.png", themeInfo.getImage());
    }

    @Test
    void testToEntity() {
        // Arrange
        ThemeInfo themeInfo = new ThemeInfo();
        themeInfo.setId(2L);
        themeInfo.setName("Ocean");
        themeInfo.setImage("ocean.jpg");

        // Act
        Theme theme = ThemeMapper.toEntity(themeInfo);

        // Assert
        assertEquals(2L, theme.getId());
        assertEquals("Ocean", theme.getName());
        assertEquals("ocean.jpg", theme.getImage());
    }
}
