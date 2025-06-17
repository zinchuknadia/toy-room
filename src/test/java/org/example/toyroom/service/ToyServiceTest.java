package org.example.toyroom.service;

import org.example.entity.ToyEntity;
import org.example.toyroom.models.Size;
import org.example.toyroom.models.Toy;
import org.example.toyroom.models.ToyRoom;
import org.example.repository.ToyRepository;
import org.example.toyroom.mappers.ToyMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ToyServiceTest {

    private ToyRepository repository;
    private ToyMapper toyMapper;
    private ToyService service;

    @BeforeEach
    void setUp() {
        repository = mock(ToyRepository.class);
        toyMapper = mock(ToyMapper.class);
        service = new ToyService(repository, toyMapper);
    }

    @Test
    void saveToy_shouldConvertAndSaveEntity() {
        Toy toy = new Toy();
        ToyEntity entity = new ToyEntity();

        when(toyMapper.toEntity(toy)).thenReturn(entity);

        service.saveToy(toy, null);

        verify(toyMapper).toEntity(toy);
        verify(repository).save(entity);
    }

    @Test
    void getById_shouldReturnMappedToy() {
        ToyEntity entity = new ToyEntity();
        Toy toy = new Toy();

        when(repository.findById(1L)).thenReturn(entity);
        when(toyMapper.toModel(entity)).thenReturn(toy);

        Toy result = service.getById(1L);

        assertEquals(toy, result);
        verify(repository).findById(1L);
        verify(toyMapper).toModel(entity);
    }

    @Test
    void getAllToys_shouldReturnAllMappedToys() {
        ToyEntity entity1 = new ToyEntity();
        ToyEntity entity2 = new ToyEntity();
        Toy toy1 = new Toy();
        Toy toy2 = new Toy();

        when(repository.findAll()).thenReturn(List.of(entity1, entity2));
        when(toyMapper.toModel(entity1)).thenReturn(toy1);
        when(toyMapper.toModel(entity2)).thenReturn(toy2);

        List<Toy> toys = service.getAllToys();

        assertEquals(2, toys.size());
        assertTrue(toys.contains(toy1));
        assertTrue(toys.contains(toy2));
    }

    @Test
    void getToysByRoomId_shouldReturnMappedToysByRoom() {
        Long roomId = 5L;
        ToyEntity entity1 = new ToyEntity();
        ToyEntity entity2 = new ToyEntity();
        Toy toy1 = new Toy();
        Toy toy2 = new Toy();

        when(repository.findByToyRoomId(roomId)).thenReturn(List.of(entity1, entity2));
        when(toyMapper.toModel(entity1)).thenReturn(toy1);
        when(toyMapper.toModel(entity2)).thenReturn(toy2);

        List<Toy> toys = service.getToysByRoomId(roomId);

        assertEquals(2, toys.size());
        assertTrue(toys.contains(toy1));
        assertTrue(toys.contains(toy2));
    }

    @Test
    void getToyByTypeName_shouldReturnMappedToy() {
        String typeName = "Robot";
        ToyEntity entity = new ToyEntity();
        Toy toy = new Toy();

        when(repository.findByTypeName(typeName)).thenReturn(entity);
        when(toyMapper.toModel(entity)).thenReturn(toy);

        Toy result = service.getToyByTypeName(typeName);

        assertEquals(toy, result);
        verify(repository).findByTypeName(typeName);
        verify(toyMapper).toModel(entity);
    }

    @Test
    void deleteById_shouldCallRepositoryDelete() {
        service.deleteById(3L);
        verify(repository).deleteById(3L);
    }

    @Test
    void buyToy_shouldSaveAndDecreaseBudget_whenEnoughBudget() {
        Toy toy = new Toy();
        toy.setPrice(50);

        ToyRoom room = new ToyRoom();
        room.setBudget(100);

        when(toyMapper.toEntity(toy)).thenReturn(new ToyEntity());

        boolean result = service.buyToy(toy, room);

        assertTrue(result);
        assertEquals(50, room.getBudget());
        verify(repository).save(any());
    }

    @Test
    void buyToy_shouldNotSaveAndReturnFalse_whenNotEnoughBudget() {
        Toy toy = new Toy();
        toy.setPrice(150);

        ToyRoom room = new ToyRoom();
        room.setBudget(100);

        boolean result = service.buyToy(toy, room);

        assertFalse(result);
        assertEquals(100, room.getBudget());
        verify(repository, never()).save(any());
    }

    @Test
    void searchAndSortToys_filtersByKeywordAndSortsByCriteria() {
        ToyRoom toyRoom = new ToyRoom();
        toyRoom.setId(1L);

        Toy toy1 = new Toy();
        toy1.setType("Car");
        toy1.setMaterial("Plastic");
        toy1.setPrice(20);
        toy1.setSize(Size.SMALL);

        Toy toy2 = new Toy();
        toy2.setType("Doll");
        toy2.setMaterial("Cloth");
        toy2.setPrice(50);
        toy2.setSize(Size.MEDIUM);

        Toy toy3 = new Toy();
        toy3.setType("Car");
        toy3.setMaterial("Metal");
        toy3.setPrice(70);
        toy3.setSize(Size.LARGE);

        List<Toy> toys = List.of(toy1, toy2, toy3);

        // Mock getToysByRoomId to return this list
        ToyService spyService = spy(service);
        doReturn(toys).when(spyService).getToysByRoomId(1L);

        // Filter keyword "car" and sort by price descending
        List<Toy> filteredSorted = spyService.searchAndSortToys(toyRoom, "car", List.of("price"));

        // Should return toys with type containing "car", sorted by price descending
        assertEquals(2, filteredSorted.size());
        assertEquals(70, filteredSorted.get(0).getPrice());
        assertEquals(20, filteredSorted.get(1).getPrice());
    }
}
