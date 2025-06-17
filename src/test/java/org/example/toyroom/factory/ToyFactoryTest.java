package org.example.toyroom.factory;

import org.example.entity.Type;
import org.example.toyroom.models.MyColor;
import org.example.toyroom.models.Size;
import org.example.toyroom.models.Toy;
import org.example.toyroom.models.TypeInfo;
import org.example.toyroom.service.TypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ToyFactoryTest {

    private TypeService typeService;
    private ToyFactory toyFactory;

    @BeforeEach
    public void setUp() {
        typeService = mock(TypeService.class);
        toyFactory = new ToyFactory(typeService);
    }

    @Test
    public void testCreateToyWithTypeInfo_shouldCreateCorrectToy() {
        // Arrange
        Long id = 1L;
        Long roomId = 2L;
        TypeInfo typeInfo = new TypeInfo("Car", "car.png", 15.5);
        Size size = Size.MEDIUM;
        MyColor color = new MyColor("#ABCDEF");
        String material = "Plastic";

        // Act
        Toy toy = toyFactory.createToy(id, roomId, typeInfo, size, color, material);

        // Assert
        assertEquals(id, toy.getId());
        assertEquals(roomId, toy.getRoomId());
        assertEquals("Car", toy.getType());
        assertEquals(15.5, toy.getPrice());
        assertEquals("car.png", toy.getImagePath());
        assertEquals(Size.MEDIUM, toy.getSize());
        assertEquals(color.getHexCode(), toy.getColor().getHexCode());
        assertEquals("Plastic", toy.getMaterial());
    }

    @Test
    public void testCreateToyByName_shouldFetchTypeAndCreateToy() {
        // Arrange
        String typeName = "Doll";
        Size size = Size.SMALL;
        MyColor color = new MyColor("#00FF00");
        String material = "Wood";

        Type type = new Type();
        type.setName("Doll");
        type.setImage("doll.png");
        type.setPrice(9.99);

        when(typeService.getTypeByName("Doll")).thenReturn(type);

        // Act
        Toy toy = toyFactory.createToy(typeName, size, color, material);

        // Assert
        assertEquals("Doll", toy.getType());
        assertEquals("doll.png", toy.getImagePath());
        assertEquals(9.99, toy.getPrice());
        assertEquals(Size.SMALL, toy.getSize());
        assertEquals("#00FF00", toy.getColor().getHexCode());
        assertEquals("Wood", toy.getMaterial());
        verify(typeService).getTypeByName("Doll");
    }
}
