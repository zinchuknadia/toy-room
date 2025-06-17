package org.example.toyroom.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.entity.ToyRoomEntity;
import org.example.repository.ToyRoomRepository;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ToyRoomRepositoryTest {

    private static EntityManagerFactory emf;
    private EntityManager em;
    private ToyRoomRepository repo;

    @BeforeAll
    static void setupClass() {
        emf = Persistence.createEntityManagerFactory("toyroomPU");
    }

    @AfterAll
    static void tearDownClass() {
        if (emf != null) emf.close();
    }

    @BeforeEach
    void setup() {
        em = emf.createEntityManager();
        repo = new ToyRoomRepository(em);

        // Clear tables before each test for isolation
        em.getTransaction().begin();
        em.createQuery("DELETE FROM ToyEntity").executeUpdate();
        em.createQuery("DELETE FROM ToyRoomEntity").executeUpdate();
        em.createQuery("DELETE FROM Theme").executeUpdate();
        em.getTransaction().commit();
    }

    @AfterEach
    void tearDown() {
        if (em != null) em.close();
    }

    @Test
    void saveOrUpdate_shouldPersistNewToyRoom() {
        ToyRoomEntity room = new ToyRoomEntity();
        room.setName("Room 1");
        room.setBudget(1000);
        room.setCreatedAt(LocalDateTime.now());
        room.setUpdatedAt(LocalDateTime.now());

        ToyRoomEntity saved = repo.saveOrUpdate(room);
        assertNotNull(saved.getId());

        ToyRoomEntity fetched = repo.findById(saved.getId());
        assertEquals("Room 1", fetched.getName());
        assertEquals(1000, fetched.getBudget());
    }

    @Test
    void findAll_shouldReturnAllToyRooms() {
        ToyRoomEntity room1 = new ToyRoomEntity();
        room1.setName("Room 1");
        room1.setBudget(100);

        ToyRoomEntity room2 = new ToyRoomEntity();
        room2.setName("Room 2");
        room2.setBudget(200);

        repo.saveOrUpdate(room1);
        repo.saveOrUpdate(room2);

        List<ToyRoomEntity> rooms = repo.findAll();
        assertEquals(2, rooms.size());
    }

    @Test
    void deleteById_shouldRemoveToyRoom() {
        ToyRoomEntity room = new ToyRoomEntity();
        room.setName("ToDelete");
        room.setBudget(50);
        ToyRoomEntity saved = repo.saveOrUpdate(room);

        repo.deleteById(saved.getId());
        ToyRoomEntity fetched = repo.findById(saved.getId());
        assertNull(fetched);
    }

    @Test
    void updateBudget_shouldChangeBudget() {
        ToyRoomEntity room = new ToyRoomEntity();
        room.setName("BudgetRoom");
        room.setBudget(300);
        ToyRoomEntity saved = repo.saveOrUpdate(room);

        repo.updateBudget(saved.getId(), 500);

        ToyRoomEntity updated = repo.findById(saved.getId());
        assertEquals(500, updated.getBudget());
    }
}
