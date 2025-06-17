package org.example.toyroom.mappers;

import org.example.entity.Theme;
import org.example.entity.ToyEntity;
import org.example.entity.ToyRoomEntity;
import org.example.toyroom.models.MyColor;
import org.example.toyroom.models.Size;
import org.example.toyroom.models.Toy;
import org.example.toyroom.models.ToyRoom;
import org.example.toyroom.service.ThemeService;
import org.example.toyroom.service.ToyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ToyRoomMapperTest {

    private ThemeService themeService;
    private ToyService toyService;
    private ToyMapper toyMapper;

    private ToyRoomMapper toyRoomMapper;

    @BeforeEach
    public void setup() {
        themeService = mock(ThemeService.class);
        toyService = mock(ToyService.class);
        toyMapper = mock(ToyMapper.class);

        toyRoomMapper = new ToyRoomMapper(themeService, toyService, toyMapper);
    }

    @Test
    public void testToModel() {
        Theme theme = new Theme();
        theme.setName("Nature");
        theme.setImage("nature.jpg");

        ToyEntity toyEntity = new ToyEntity();
        toyEntity.setId(1L);
        toyEntity.setSize("SMALL");
        toyEntity.setColor("#123456");
        toyEntity.setMaterial("Plastic");

        ToyRoomEntity entity = new ToyRoomEntity();
        entity.setId(10L);
        entity.setName("Test Room");
        entity.setTheme(theme);
        entity.setCreatedAt(LocalDateTime.now().minusDays(1));
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setBudget(100.0);
        entity.setToys(List.of(toyEntity));

        Toy mockToy = new Toy(1L, 10L, "Car", Size.SMALL, new MyColor("#123456"), "Plastic");
        when(toyMapper.toModel(toyEntity)).thenReturn(mockToy);

        ToyRoom model = toyRoomMapper.toModel(entity);

        assertEquals(entity.getId(), model.getId());
        assertEquals(entity.getName(), model.getName());
        assertEquals(theme.getName(), model.getThemeName());
        assertEquals(theme.getImage(), model.getThemeImage());
        assertEquals(entity.getBudget(), model.getBudget());
        assertEquals(1, model.getToys().size());
        assertEquals(mockToy, model.getToys().get(0));
    }

    @Test
    public void testToEntityWithId() {
        ToyRoom model = new ToyRoom();
        model.setId(10L);
        model.setName("Room X");
        model.setThemeName("Ocean");
        model.setCreatedAt(LocalDateTime.now().minusDays(1));
        model.setUpdatedAt(LocalDateTime.now());
        model.setBudget(200.0);

        Toy toy = new Toy(1L, 10L, "Boat", Size.LARGE, new MyColor("#0000FF"), "Wood");
        List<Toy> mockToys = List.of(toy);
        List<ToyEntity> mockToyEntities = List.of(new ToyEntity());

        when(themeService.getThemeByName("Ocean")).thenReturn(new Theme());
        when(toyService.getToysByRoomId(10L)).thenReturn(mockToys);
        when(toyMapper.toEntity(toy)).thenReturn(mockToyEntities.get(0));

        ToyRoomEntity entity = toyRoomMapper.toEntity(model);

        assertEquals(model.getId(), entity.getId());
        assertEquals(model.getName(), entity.getName());
        assertEquals(model.getBudget(), entity.getBudget());
        assertEquals(1, entity.getToys().size());
    }

    @Test
    public void testToEntityWithoutId() {
        ToyRoom model = new ToyRoom();
        model.setName("New Room");
        model.setThemeName("Space");
        model.setCreatedAt(LocalDateTime.now().minusDays(2));
        model.setUpdatedAt(LocalDateTime.now());
        model.setBudget(500.0);

        when(themeService.getThemeByName("Space")).thenReturn(new Theme());

        ToyRoomEntity entity = toyRoomMapper.toEntity(model);

        assertNull(entity.getId()); // Since no ID set
        assertEquals(model.getName(), entity.getName());
        assertEquals(0, entity.getToys().size()); // No toys if ID is null
    }
}
