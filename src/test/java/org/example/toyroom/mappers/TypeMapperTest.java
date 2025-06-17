package org.example.toyroom.mappers;

import org.example.entity.Type;
import org.example.toyroom.models.TypeInfo;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TypeMapperTest {

    @Test
    public void testToModel() {
        Type type = new Type();
        type.setId(1L);
        type.setName("Animal");
        type.setImage("animal.png");
        type.setPrice(99.99);

        TypeInfo dto = TypeMapper.toModel(type);

        assertEquals(1L, dto.getId());
        assertEquals("Animal", dto.getName());
        assertEquals("animal.png", dto.getImage());
        assertEquals(99.99, dto.getPrice());
    }

    @Test
    public void testToEntity() {
        TypeInfo dto = new TypeInfo();
        dto.setId(2L);
        dto.setName("Vehicle");
        dto.setImage("vehicle.jpg");
        dto.setPrice(150.0);

        Type type = TypeMapper.toEntity(dto);

        assertEquals(2L, type.getId());
        assertEquals("Vehicle", type.getName());
        assertEquals("vehicle.jpg", type.getImage());
        assertEquals(150.0, type.getPrice());
    }
}
