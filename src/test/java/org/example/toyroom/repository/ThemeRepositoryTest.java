package org.example.toyroom.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.entity.Theme;
import org.example.entity.ToyRoomEntity;
import org.example.repository.ThemeRepository;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ThemeRepositoryTest {

    private static EntityManagerFactory emf;
    private EntityManager em;
    private ThemeRepository themeRepository;

    @BeforeAll
    static void setupAll() {
        emf = Persistence.createEntityManagerFactory("toyroomPU"); // your persistence unit name
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
        themeRepository = new ThemeRepository(em);

        // Clean up tables before each test (optional, depends on your setup)
        em.getTransaction().begin();
        em.createQuery("DELETE FROM ToyEntity").executeUpdate();
        em.createQuery("DELETE FROM ToyRoomEntity").executeUpdate();
        em.createQuery("DELETE FROM Theme").executeUpdate();
        em.getTransaction().commit();
    }

    @AfterEach
    void tearDown() {
        if (em != null) {
            em.close();
        }
    }

    @Test
    void saveAndFindById() {
        Theme theme = new Theme();
        theme.setName("TestTheme");
        theme.setImage("image.png");

        themeRepository.save(theme);

        Theme found = themeRepository.findById(theme.getId());
        assertNotNull(found);
        assertEquals("TestTheme", found.getName());
    }

    @Test
    void findByNameReturnsTheme() {
        Theme theme = new Theme();
        theme.setName("MyTheme");
        theme.setImage("image.png");
        themeRepository.save(theme);

        Theme found = themeRepository.findByName("MyTheme");
        assertNotNull(found);
        assertEquals("MyTheme", found.getName());
    }

    @Test
    void findByNameReturnsNullIfNotFound() {
        Theme found = themeRepository.findByName("NonExistentTheme");
        assertNull(found);
    }

    @Test
    void deleteByIdFailsIfThemeInUse() {
        // Create and save a theme
        Theme theme = new Theme();
        theme.setName("ThemeInUse");
        theme.setImage("image.png");

        em.getTransaction().begin();
        em.persist(theme);
        em.getTransaction().commit();

        // Create a ToyRoomEntity referencing this theme
        ToyRoomEntity toyRoom = new ToyRoomEntity();
        toyRoom.setName("Room1");
        toyRoom.setTheme(theme);
        toyRoom.setBudget(100);
        toyRoom.setCreatedAt(LocalDateTime.now());
        toyRoom.setUpdatedAt(LocalDateTime.now());

        em.getTransaction().begin();
        em.persist(toyRoom);
        em.getTransaction().commit();

        // Try to delete the theme
        boolean result = themeRepository.deleteById(theme.getId());
        assertFalse(result);

        // Verify theme still exists
        Theme stillThere = themeRepository.findById(theme.getId());
        assertNotNull(stillThere);
    }

    @Test
    void deleteByIdSucceedsIfThemeNotInUse() {
        Theme theme = new Theme();
        theme.setName("UnusedTheme");
        theme.setImage("image.png");

        em.getTransaction().begin();
        em.persist(theme);
        em.getTransaction().commit();

        boolean result = themeRepository.deleteById(theme.getId());
        assertTrue(result);

        Theme deleted = themeRepository.findById(theme.getId());
        assertNull(deleted);
    }
}
