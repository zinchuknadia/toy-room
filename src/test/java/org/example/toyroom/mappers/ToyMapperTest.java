package org.example.toyroom.mappers;

import org.example.entity.ToyEntity;
import org.example.entity.ToyRoomEntity;
import org.example.entity.Type;
import org.example.toyroom.factory.ToyFactory;
import org.example.toyroom.models.MyColor;
import org.example.toyroom.models.Size;
import org.example.toyroom.models.Toy;
import org.example.toyroom.service.ToyRoomService;
import org.example.toyroom.service.TypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ToyMapperTest {

    private ToyRoomService toyRoomService;
    private TypeService typeService;
    private ToyMapper toyMapper;

    @BeforeEach
    void setup() {
        toyRoomService = mock(ToyRoomService.class);
        typeService = mock(TypeService.class);
        ToyFactory toyFactory = mock(ToyFactory.class);
        toyMapper = new ToyMapper(toyRoomService, typeService, toyFactory);
    }

    @Test
    void testToModel() {
        // given
        ToyEntity entity = new ToyEntity();
        entity.setId(1L);
        entity.setSize("SMALL");
        entity.setColor("#FF0000");
        entity.setMaterial("Plastic");

        Type type = new Type();
        type.setName("Car");
        entity.setType(type);

        ToyRoomEntity room = new ToyRoomEntity();
        room.setId(10L);
        entity.setToyRoom(room);

        // when
        Toy model = toyMapper.toModel(entity);

        // then
        assertNotNull(model);
        assertEquals(1L, model.getId());
        assertEquals(10L, model.getRoomId());
        assertEquals("Car", model.getType());
        assertEquals(Size.SMALL, model.getSize());
        assertEquals("#FF0000", model.getColor().getHexCode());
        assertEquals("Plastic", model.getMaterial());
    }

    @Test
    void testToEntity() {
        // given
        Toy toy = new Toy("Doll", Size.MEDIUM, new MyColor("#00FF00"),"Wood");

        ToyRoomEntity roomEntity = new ToyRoomEntity();
        roomEntity.setId(20L);
        when(toyRoomService.getById(20L)).thenReturn(roomEntity);

        Type type = new Type();
        type.setName("Doll");
        when(typeService.getTypeByName("Doll")).thenReturn(type);

        // when
        ToyEntity entity = toyMapper.toEntity(toy);

        // then
        assertNotNull(entity);
        assertEquals(2L, entity.getId());
        assertEquals(roomEntity, entity.getToyRoom());
        assertEquals(type, entity.getType());
        assertEquals("MEDIUM", entity.getSize());
        assertEquals("#00FF00", entity.getColor());
        assertEquals("Wood", entity.getMaterial());

        verify(toyRoomService).getById(20L);
        verify(typeService).getTypeByName("Doll");
    }
}
