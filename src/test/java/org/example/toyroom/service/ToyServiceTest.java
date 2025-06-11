package org.example.toyroom.service;

import org.example.toyroom.ToyRoom;
import org.example.toyroom.models.Size;
import org.example.toyroom.models.toys.Toy;
import org.example.toyroom.repository.ToyRepository;
import org.example.toyroom.service.ToyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ToyServiceTest {

    private ToyRoom toyRoom;
    private ToyRepository toyRepository;
    private ToyService toyService;

    @BeforeEach
    public void setUp() {
        toyRoom = new ToyRoom(50.0); // бюджет 50
        toyRepository = mock(ToyRepository.class);
        toyService = new ToyService(toyRoom, toyRepository);
    }

    @Test
    public void testBuyToy_SufficientBudget() {
        Toy toy = mock(Toy.class);
        when(toy.getPrice()).thenReturn(30.0);

        boolean result = toyService.buyToy(toy);

        assertTrue(result);
        assertEquals(20.0, toyRoom.getBudget());
        verify(toyRepository, times(1)).add(toy);
    }

    @Test
    public void testBuyToy_InsufficientBudget() {
        Toy toy = mock(Toy.class);
        when(toy.getPrice()).thenReturn(100.0);

        boolean result = toyService.buyToy(toy);

        assertFalse(result);
        assertEquals(50.0, toyRoom.getBudget());
        verify(toyRepository, never()).add(toy);
    }

    @Test
    public void testGetAllToys() {
        Toy toy = mock(Toy.class);
        when(toyRepository.findAll()).thenReturn(List.of(toy));

        List<Toy> result = toyService.getAllToys();

        assertEquals(1, result.size());
        verify(toyRepository).findAll();
    }

    @Test
    public void testDeleteToy() {
        when(toyRepository.deleteById(1)).thenReturn(true);

        boolean result = toyService.deleteToy(1);

        assertTrue(result);
        verify(toyRepository).deleteById(1);
    }

    @Test
    public void testSearchAndSortToys_ByType() {
        Toy toy1 = mock(Toy.class);
        Toy toy2 = mock(Toy.class);

        when(toy1.getType()).thenReturn("Car");
        when(toy1.getSize()).thenReturn(Size.SMALL);
        when(toy1.getMaterial()).thenReturn("Plastic");
        when(toy1.getPrice()).thenReturn(20.0);

        when(toy2.getType()).thenReturn("Doll");
        when(toy2.getSize()).thenReturn(Size.LARGE);
        when(toy2.getMaterial()).thenReturn("Fabric");
        when(toy2.getPrice()).thenReturn(30.0);

        when(toyRepository.findAll()).thenReturn(List.of(toy1, toy2));

        List<Toy> result = toyService.searchAndSortToys("", List.of("type"));

        assertEquals(2, result.size());
        assertEquals("Car", result.get(0).getType());
        assertEquals("Doll", result.get(1).getType());
    }

    @Test
    public void testSearchAndSortToys_WithKeyword() {
        Toy toy1 = mock(Toy.class);
        Toy toy2 = mock(Toy.class);

        when(toy1.getType()).thenReturn("Car");
        when(toy1.getSize()).thenReturn(Size.SMALL);
        when(toy1.getMaterial()).thenReturn("Plastic");
        when(toy1.getPrice()).thenReturn(20.0);

        when(toy2.getType()).thenReturn("Doll");
        when(toy2.getSize()).thenReturn(Size.LARGE);
        when(toy2.getMaterial()).thenReturn("Fabric");
        when(toy2.getPrice()).thenReturn(30.0);

        when(toyRepository.findAll()).thenReturn(List.of(toy1, toy2));

        // Keyword is "fabric", should only match toy2
        List<Toy> result = toyService.searchAndSortToys("fabric", List.of());

        assertEquals(1, result.size());
        assertEquals("Doll", result.get(0).getType());
    }

}
