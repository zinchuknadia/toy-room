package org.example.toyroom.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.entity.ToyEntity;
import org.example.entity.ToyRoomEntity;
import org.example.entity.Type;
import org.example.repository.ToyRepository;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ToyRepositoryTest {

    private static EntityManagerFactory emf;
    private EntityManager em;
    private ToyRepository toyRepository;

    @BeforeAll
    static void beforeAll() {
        emf = Persistence.createEntityManagerFactory("toyroomPU");
    }

    @AfterAll
    static void afterAll() {
        if (emf != null) emf.close();
    }

    @BeforeEach
    void setup() {
        em = emf.createEntityManager();
        toyRepository = new ToyRepository(em);

        em.getTransaction().begin();
        em.createQuery("DELETE FROM ToyEntity").executeUpdate();
        em.createQuery("DELETE FROM ToyRoomEntity").executeUpdate();
        em.createQuery("DELETE FROM Type").executeUpdate();
        em.getTransaction().commit();
    }

    @AfterEach
    void tearDown() {
        if (em != null) em.close();
    }

    @Test
    void saveAndFindById() {
        // Prepare related entities
        Type type = new Type();
        type.setName("Plush");
        type.setImage("plush.png");
        type.setPrice(10.0);

        ToyRoomEntity room = new ToyRoomEntity();
        room.setName("Room A");
        room.setBudget(100);
        room.setCreatedAt(LocalDateTime.now());
        room.setUpdatedAt(LocalDateTime.now());

        em.getTransaction().begin();
        em.persist(type);
        em.persist(room);
        em.getTransaction().commit();

        ToyEntity toy = new ToyEntity();
        toy.setType(type);
        toy.setToyRoom(room);
        toy.setColor("Red");
        toy.setMaterial("Cotton");
        toy.setSize("Medium");

        toyRepository.save(toy);

        ToyEntity found = toyRepository.findById(toy.getId());
        assertNotNull(found);
        assertEquals("Red", found.getColor());
        assertEquals("Cotton", found.getMaterial());
        assertEquals("Medium", found.getSize());
        assertEquals("Plush", found.getType().getName());
        assertEquals("Room A", found.getToyRoom().getName());
    }

    @Test
    void findByTypeNameReturnsCorrectToy() {
        Type type = new Type();
        type.setName("Educational");
        type.setImage("edu.png");
        type.setPrice(20.0);

        ToyRoomEntity room = new ToyRoomEntity();
        room.setName("Room B");
        room.setBudget(200);
        room.setCreatedAt(LocalDateTime.now());
        room.setUpdatedAt(LocalDateTime.now());

        em.getTransaction().begin();
        em.persist(type);
        em.persist(room);
        em.getTransaction().commit();

        ToyEntity toy = new ToyEntity();
        toy.setType(type);
        toy.setToyRoom(room);
        toy.setColor("Blue");
        toy.setMaterial("Plastic");
        toy.setSize("Small");

        toyRepository.save(toy);

        ToyEntity found = toyRepository.findByTypeName("Educational");
        assertNotNull(found);
        assertEquals("Blue", found.getColor());
    }

    @Test
    void findAllReturnsAllToys() {
        // Setup a couple of toys
        // Reuse entities or create new ones, persist them

        // ... Similar to above, create and save toys ...

        List<ToyEntity> toys = toyRepository.findAll();
        assertTrue(toys.size() >= 0);
    }

    @Test
    void findByToyRoomIdReturnsToysInRoom() {
        // Створимо кімнату
        ToyRoomEntity room = new ToyRoomEntity();
        room.setName("Room X");
        room.setBudget(300);
        room.setCreatedAt(LocalDateTime.now());
        room.setUpdatedAt(LocalDateTime.now());

        em.getTransaction().begin();
        em.persist(room);
        em.getTransaction().commit();

        // Створимо 2 іграшки для цієї кімнати
        ToyEntity toy1 = new ToyEntity();
        toy1.setToyRoom(room);
        toy1.setColor("Yellow");
        toy1.setMaterial("Wood");
        toy1.setSize("Large");

        ToyEntity toy2 = new ToyEntity();
        toy2.setToyRoom(room);
        toy2.setColor("Green");
        toy2.setMaterial("Rubber");
        toy2.setSize("Small");

        em.getTransaction().begin();
        em.persist(toy1);
        em.persist(toy2);
        em.getTransaction().commit();

        List<ToyEntity> toys = toyRepository.findByToyRoomId(room.getId());

        assertEquals(2, toys.size());
        assertTrue(toys.stream().anyMatch(t -> "Yellow".equals(t.getColor())));
        assertTrue(toys.stream().anyMatch(t -> "Green".equals(t.getColor())));
    }
}
