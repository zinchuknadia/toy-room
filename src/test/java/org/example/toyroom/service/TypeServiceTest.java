package org.example.toyroom.service;

import org.example.entity.Type;
import org.example.toyroom.models.TypeInfo;
import org.example.repository.TypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TypeServiceTest {

    private TypeRepository repository;
    private TypeService service;

    @BeforeEach
    void setUp() {
        repository = mock(TypeRepository.class);
        service = new TypeService(repository);
    }

    @Test
    void createType_savesTypeWithCorrectNameAndImage() {
        service.createType("Car", "car.png");

        ArgumentCaptor<Type> captor = ArgumentCaptor.forClass(Type.class);
        verify(repository).save(captor.capture());

        Type saved = captor.getValue();
        assertEquals("Car", saved.getName());
        assertEquals("car.png", saved.getImage());
    }

    @Test
    void getById_returnsTypeFromRepository() {
        Type type = new Type();
        type.setId(1L);
        type.setName("Doll");
        type.setImage("doll.png");

        when(repository.findById(1L)).thenReturn(type);

        Type result = service.getById(1L);

        assertNotNull(result);
        assertEquals("Doll", result.getName());
        assertEquals("doll.png", result.getImage());
    }

    @Test
    void getAllTypes_returnsMappedTypeInfoList() {
        Type type1 = new Type();
        type1.setName("Robot");
        type1.setImage("robot.png");

        Type type2 = new Type();
        type2.setName("Puzzle");
        type2.setImage("puzzle.png");

        when(repository.findAll()).thenReturn(List.of(type1, type2));

        List<TypeInfo> result = service.getAllTypes();

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(t -> t.getName().equals("Robot")));
        assertTrue(result.stream().anyMatch(t -> t.getName().equals("Puzzle")));
    }

    @Test
    void getAllTypeNames_returnsListOfNames() {
        Type type1 = new Type();
        type1.setName("Robot");
        Type type2 = new Type();
        type2.setName("Puzzle");

        when(repository.findAll()).thenReturn(List.of(type1, type2));

        List<String> names = service.getAllTypeNames();

        assertEquals(2, names.size());
        assertTrue(names.contains("Robot"));
        assertTrue(names.contains("Puzzle"));
    }

    @Test
    void getTypeByName_returnsTypeFromRepository() {
        Type type = new Type();
        type.setName("Robot");

        when(repository.findByName("Robot")).thenReturn(type);

        Type result = service.getTypeByName("Robot");

        assertNotNull(result);
        assertEquals("Robot", result.getName());
        verify(repository).findByName("Robot");
    }

    @Test
    void deleteById_returnsTrueIfRepositoryDeletes() {
        when(repository.deleteById(1L)).thenReturn(true);

        boolean deleted = service.deleteById(1L);

        assertTrue(deleted);
        verify(repository).deleteById(1L);
    }

    @Test
    void deleteById_returnsFalseIfRepositoryDoesNotDelete() {
        when(repository.deleteById(1L)).thenReturn(false);

        boolean deleted = service.deleteById(1L);

        assertFalse(deleted);
        verify(repository).deleteById(1L);
    }

    @Test
    void saveType_savesMappedEntity() {
        TypeInfo info = new TypeInfo();
        info.setName("Car");
        info.setImage("car.png");

        service.saveType(info);

        ArgumentCaptor<Type> captor = ArgumentCaptor.forClass(Type.class);
        verify(repository).save(captor.capture());

        Type saved = captor.getValue();
        assertEquals("Car", saved.getName());
        assertEquals("car.png", saved.getImage());
    }
}
