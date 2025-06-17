package org.example.toyroom.service;

import org.example.entity.Theme;
import org.example.entity.ToyRoomEntity;
import org.example.toyroom.mappers.ToyRoomMapper;
import org.example.toyroom.models.Toy;
import org.example.toyroom.models.ToyRoom;
import org.example.repository.ToyRoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ToyRoomServiceTest {

    private ToyRoomRepository repository;
    private ToyRoomMapper mapper;
    private ToyRoomService service;

    @BeforeEach
    void setUp() {
        repository = mock(ToyRoomRepository.class);
        mapper = mock(ToyRoomMapper.class);
        service = new ToyRoomService(repository, mapper);
    }

    @Test
    void createToyRoom_savesNewRoomWithCorrectFields() {
        Theme theme = new Theme();
        theme.setName("Adventure");

        service.createToyRoom("Fun Room", theme, 1000);

        ArgumentCaptor<ToyRoomEntity> captor = ArgumentCaptor.forClass(ToyRoomEntity.class);
        verify(repository).saveOrUpdate(captor.capture());

        ToyRoomEntity saved = captor.getValue();
        assertEquals("Fun Room", saved.getName());
        assertEquals(theme, saved.getTheme());
        assertEquals(1000, saved.getBudget());
        assertNotNull(saved.getCreatedAt());
        assertNotNull(saved.getUpdatedAt());
    }

    @Test
    void saveToyRoom_mapsAndSavesEntity() {
        ToyRoom model = new ToyRoom();
        ToyRoomEntity entity = new ToyRoomEntity();

        when(mapper.toEntity(model)).thenReturn(entity);

        service.saveToyRoom(model);

        verify(mapper).toEntity(model);
        verify(repository).saveOrUpdate(entity);
    }

    @Test
    void getById_returnsEntityFromRepository() {
        ToyRoomEntity entity = new ToyRoomEntity();
        when(repository.findById(5L)).thenReturn(entity);

        ToyRoomEntity result = service.getById(5L);

        assertEquals(entity, result);
        verify(repository).findById(5L);
    }

    @Test
    void getAll_returnsAllMappedToyRooms() {
        ToyRoomEntity entity1 = new ToyRoomEntity();
        ToyRoomEntity entity2 = new ToyRoomEntity();
        ToyRoom model1 = new ToyRoom();
        ToyRoom model2 = new ToyRoom();

        when(repository.findAll()).thenReturn(List.of(entity1, entity2));
        when(mapper.toModel(entity1)).thenReturn(model1);
        when(mapper.toModel(entity2)).thenReturn(model2);

        List<ToyRoom> rooms = service.getAll();

        assertEquals(2, rooms.size());
        assertTrue(rooms.contains(model1));
        assertTrue(rooms.contains(model2));
    }

    @Test
    void deleteById_callsRepositoryDelete() {
        service.deleteById(10L);
        verify(repository).deleteById(10L);
    }

    @Test
    void updateBudget_callsRepositoryUpdateBudget() {
        ToyRoom room = new ToyRoom();
        room.setId(2L);
        room.setBudget(500);

        service.updateBudget(room);

        verify(repository).updateBudget(2L, 500);
    }

    @Test
    void updateUpdatedAt_findsEntityAndUpdatesTime() {
        ToyRoomEntity entity = new ToyRoomEntity();
        when(repository.findById(3L)).thenReturn(entity);

        service.updateUpdatedAt(3L);

        assertNotNull(entity.getUpdatedAt());
        verify(repository).saveOrUpdate(entity);
    }

    @Test
    void updateUpdatedAt_whenEntityNotFound_doesNothing() {
        when(repository.findById(3L)).thenReturn(null);

        service.updateUpdatedAt(3L);

        verify(repository, never()).saveOrUpdate(any());
    }

    @Test
    void updateToyRoom_mapsEntityUpdatesTimeAndSaves() {
        ToyRoom model = new ToyRoom();
        ToyRoomEntity entity = new ToyRoomEntity();

        when(mapper.toEntity(model)).thenReturn(entity);

        service.updateToyRoom(model);

        assertNotNull(entity.getUpdatedAt());
        verify(repository).saveOrUpdate(entity);
    }

    @Test
    void getSortedToyRooms_sortsByBudgetDescending() {
        ToyRoom room1 = new ToyRoom();
        room1.setBudget(100);
        ToyRoom room2 = new ToyRoom();
        room2.setBudget(300);
        ToyRoom room3 = new ToyRoom();
        room3.setBudget(200);

        List<ToyRoom> rooms = new ArrayList<>(List.of(room1, room2, room3));

        ToyRoomService spyService = spy(service);
        doReturn(rooms).when(spyService).getAll();

        List<ToyRoom> sorted = spyService.getSortedToyRooms(List.of("budget"));

        assertEquals(3, sorted.size());
        assertEquals(300, sorted.get(0).getBudget());
        assertEquals(200, sorted.get(1).getBudget());
        assertEquals(100, sorted.get(2).getBudget());
    }

    @Test
    void getSortedToyRooms_sortsBySizeDescending() {
        ToyRoom room1 = new ToyRoom();
        room1.setToys(List.of(new Toy(), new Toy())); // size 2
        ToyRoom room2 = new ToyRoom();
        room2.setToys(List.of(new Toy())); // size 1
        ToyRoom room3 = new ToyRoom();
        room3.setToys(List.of(new Toy(), new Toy(), new Toy())); // size 3

        List<ToyRoom> rooms = new ArrayList<>(List.of(room1, room2, room3));

        ToyRoomService spyService = spy(service);
        doReturn(rooms).when(spyService).getAll();

        List<ToyRoom> sorted = spyService.getSortedToyRooms(List.of("size"));

        assertEquals(3, sorted.size());
        assertEquals(3, sorted.get(0).getToys().size());
        assertEquals(2, sorted.get(1).getToys().size());
        assertEquals(1, sorted.get(2).getToys().size());
    }

    @Test
    void getSortedToyRooms_sortsByLastModifiedDescending() {
        ToyRoom room1 = new ToyRoom();
        room1.setUpdatedAt(LocalDateTime.now().minusDays(1));
        ToyRoom room2 = new ToyRoom();
        room2.setUpdatedAt(LocalDateTime.now());
        ToyRoom room3 = new ToyRoom();
        room3.setUpdatedAt(LocalDateTime.now().minusDays(2));

        List<ToyRoom> rooms = new ArrayList<>(List.of(room1, room2, room3));

        ToyRoomService spyService = spy(service);
        doReturn(rooms).when(spyService).getAll();

        List<ToyRoom> sorted = spyService.getSortedToyRooms(List.of("last modified"));

        assertEquals(3, sorted.size());
        assertEquals(room2, sorted.get(0));
        assertEquals(room1, sorted.get(1));
        assertEquals(room3, sorted.get(2));
    }

    @Test
    void getSortedToyRooms_returnsUnsortedIfCriterionUnknown() {
        ToyRoom room = new ToyRoom();
        List<ToyRoom> rooms = List.of(room);

        ToyRoomService spyService = spy(service);
        doReturn(rooms).when(spyService).getAll();

        List<ToyRoom> sorted = spyService.getSortedToyRooms(List.of("unknown"));

        assertEquals(rooms, sorted);
    }
}
