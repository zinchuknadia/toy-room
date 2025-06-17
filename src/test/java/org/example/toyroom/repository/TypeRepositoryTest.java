package org.example.toyroom.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.entity.ToyEntity;
import org.example.entity.ToyRoomEntity;
import org.example.entity.Type;
import org.example.repository.TypeRepository;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TypeRepositoryTest {

    private static EntityManagerFactory emf;
    private EntityManager em;
    private TypeRepository typeRepository;

    @BeforeAll
    static void setupAll() {
        emf = Persistence.createEntityManagerFactory("toyroomPU");
    }

    @AfterAll
    static void tearDownAll() {
        if (emf != null) {
            emf.close();
        }
    }

    @BeforeEach
    void setup() {
        em = emf.createEntityManager();
        typeRepository = new TypeRepository(em);

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
        Type type = new Type();
        type.setName("Plush");
        type.setImage("plush.png");
        type.setPrice(15.5);

        typeRepository.save(type);

        Type found = typeRepository.findById(type.getId());
        assertNotNull(found);
        assertEquals("Plush", found.getName());
        assertEquals(15.5, found.getPrice());
    }

    @Test
    void findByNameReturnsType() {
        Type type = new Type();
        type.setName("Educational");
        type.setImage("edu.png");
        type.setPrice(20.0);
        typeRepository.save(type);

        Type found = typeRepository.findByName("Educational");
        assertNotNull(found);
        assertEquals("Educational", found.getName());
    }

    @Test
    void findByNameReturnsNullWhenNotFound() {
        Type found = typeRepository.findByName("NonExistent");
        assertNull(found);
    }

    @Test
    void findAllReturnsAllTypes() {
        Type t1 = new Type();
        t1.setName("Plush");
        t1.setImage("plush.png");
        t1.setPrice(15.5);

        Type t2 = new Type();
        t2.setName("Educational");
        t2.setImage("edu.png");
        t2.setPrice(20.0);

        typeRepository.save(t1);
        typeRepository.save(t2);

        List<Type> allTypes = typeRepository.findAll();
        assertEquals(2, allTypes.size());
    }

    @Test
    void deleteByIdFailsIfTypeUsedByToy() {
        // Save type
        Type type = new Type();
        type.setName("Plush");
        type.setImage("plush.png");
        type.setPrice(15.5);

        em.getTransaction().begin();
        em.persist(type);
        em.getTransaction().commit();

        // Create toy room (needed for toy)
        ToyRoomEntity toyRoom = new ToyRoomEntity();
        toyRoom.setName("Room1");
        toyRoom.setBudget(100);
        toyRoom.setCreatedAt(LocalDateTime.now());
        toyRoom.setUpdatedAt(LocalDateTime.now());

        em.getTransaction().begin();
        em.persist(toyRoom);
        em.getTransaction().commit();

        // Create toy referencing the type and toy room
        ToyEntity toy = new ToyEntity();
        toy.setType(type);
        toy.setToyRoom(toyRoom);
        toy.setColor("Red");
        toy.setMaterial("Cotton");
        toy.setSize("Medium");

        em.getTransaction().begin();
        em.persist(toy);
        em.getTransaction().commit();

        // Attempt delete - should fail
        boolean deleted = typeRepository.deleteById(type.getId());
        assertFalse(deleted);

        Type stillThere = typeRepository.findById(type.getId());
        assertNotNull(stillThere);
    }

    @Test
    void deleteByIdSucceedsIfTypeUnused() {
        Type type = new Type();
        type.setName("Educational");
        type.setImage("edu.png");
        type.setPrice(20.0);

        em.getTransaction().begin();
        em.persist(type);
        em.getTransaction().commit();

        boolean deleted = typeRepository.deleteById(type.getId());
        assertTrue(deleted);

        Type deletedType = typeRepository.findById(type.getId());
        assertNull(deletedType);
    }
}
