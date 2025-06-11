package org.example.toyroom.repository;

import org.example.toyroom.models.MyColor;
import org.example.toyroom.models.Size;
import org.example.toyroom.models.toys.Toy;
import org.junit.jupiter.api.*;

import java.sql.*;
import org.example.DatabaseConnector;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ToyRepositoryTest {

    private ToyRepository toyRepository;

    @BeforeAll
    static void setupDatabase() throws SQLException {
        // Set DB config to in-memory H2
        DatabaseConnector.setTestConfig(
                "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1", "sa", ""
        );

        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement()) {

            // Create toys table
            stmt.execute("CREATE TABLE toys (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "type VARCHAR(255), " +
                    "size VARCHAR(255), " +
                    "color_code VARCHAR(255), " +
                    "material VARCHAR(255)" +
                    ")");
        }
    }

    @BeforeEach
    void init() {
        toyRepository = new ToyRepository();
    }

    @BeforeEach
    void cleanDatabase() throws Exception {
        try (Connection conn = org.example.DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM toys");
        }
    }

    @Test
    void testAddAndFindAll() {
        Toy toy = new Toy("Car", Size.MEDIUM, new MyColor("#FF0000"), "Plastic");
        toyRepository.add(toy);

        List<Toy> allToys = toyRepository.findAll();

        assertEquals(1, allToys.size());
        Toy savedToy = allToys.get(0);

        assertEquals("Car", savedToy.getType());
        assertEquals(Size.MEDIUM, savedToy.getSize());
        assertEquals("#FF0000", savedToy.getColor().getHexCode());
        assertEquals("Plastic", savedToy.getMaterial());
    }

    @Test
    void testFindAllSortedByColor() {
        toyRepository.add(new Toy("Ball", Size.SMALL, new MyColor("#00FF00"), "Rubber"));
        toyRepository.add(new Toy("Doll", Size.LARGE, new MyColor("#0000FF"), "Fabric"));
        toyRepository.add(new Toy("Car", Size.MEDIUM, new MyColor("#FF0000"), "Plastic"));

        List<Toy> toys = toyRepository.findAllSortedByColor();

        assertEquals(3, toys.size());
        // Sorted by color code: #0000FF, #00FF00, #FF0000
        assertEquals("#0000FF", toys.get(0).getColor().getHexCode());
        assertEquals("#00FF00", toys.get(1).getColor().getHexCode());
        assertEquals("#FF0000", toys.get(2).getColor().getHexCode());
    }

    @Test
    void testFindAllSortedBySize() {
        toyRepository.add(new Toy("Ball", Size.SMALL, new MyColor("#00FF00"), "Rubber"));
        toyRepository.add(new Toy("Doll", Size.LARGE, new MyColor("#0000FF"), "Fabric"));
        toyRepository.add(new Toy("Car", Size.MEDIUM, new MyColor("#FF0000"), "Plastic"));

        List<Toy> toys = toyRepository.findAllSortedBySize();

        assertEquals(3, toys.size());
        // Assuming enum order SMALL < MEDIUM < LARGE
        assertEquals(Size.SMALL, toys.get(0).getSize());
        assertEquals(Size.MEDIUM, toys.get(1).getSize());
        assertEquals(Size.LARGE, toys.get(2).getSize());
    }

    @Test
    public void testSaveToys_shouldInsertAllToys() throws SQLException {
        // Arrange
        List<Toy> toys = new ArrayList<>();
        toys.add(new Toy("Car", Size.MEDIUM, new MyColor("#FF0000"), "Plastic"));
        toys.add(new Toy("Doll", Size.SMALL, new MyColor("#00FF00"), "Fabric"));

        // Act
        toyRepository.saveToys(toys);

        // Assert
        List<Toy> savedToys = new ArrayList<>();
        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM toys")) {

            while (rs.next()) {
                String type = rs.getString("type");
                String size = rs.getString("size");
                String color = rs.getString("color_code");
                String material = rs.getString("material");

                savedToys.add(new Toy(type, Size.valueOf(size), new MyColor(color), material));
            }
        }

        assertEquals(2, savedToys.size());

        assertEquals("Car", savedToys.get(0).getType());
        assertEquals("#FF0000", savedToys.get(0).getColor().getHexCode());

        assertEquals("Doll", savedToys.get(1).getType());
        assertEquals("#00FF00", savedToys.get(1).getColor().getHexCode());
    }

    @Test
    void testDeleteById() {
        Toy toy = new Toy("Train", Size.LARGE, new MyColor("#123456"), "Wood");
        toyRepository.add(toy);

        List<Toy> toysBefore = toyRepository.findAll();
        assertEquals(1, toysBefore.size());

        // Retrieve the id of inserted toy
        int idToDelete = toysBefore.get(0).getId();

        boolean deleted = toyRepository.deleteById(idToDelete);
        assertTrue(deleted);

        List<Toy> toysAfter = toyRepository.findAll();
        assertEquals(0, toysAfter.size());
    }

    @Test
    void testFindByColor() {
        Toy toy1 = new Toy("Car", Size.MEDIUM, new MyColor("#AAAAAA"), "Plastic");
        Toy toy2 = new Toy("Ball", Size.SMALL, new MyColor("#BBBBBB"), "Rubber");
        Toy toy3 = new Toy("Doll", Size.LARGE, new MyColor("#AAAAAA"), "Fabric");

        toyRepository.add(toy1);
        toyRepository.add(toy2);
        toyRepository.add(toy3);

        List<Toy> grayToys = toyRepository.findByColor(new MyColor("#AAAAAA"));

        assertEquals(2, grayToys.size());
        for (Toy t : grayToys) {
            assertEquals("#AAAAAA", t.getColor().getHexCode());
        }
    }

    @Test
    void testFindByType() {
        Toy toy1 = new Toy("Car", Size.MEDIUM, new MyColor("#AAAAAA"), "Plastic");
        Toy toy2 = new Toy("Ball", Size.SMALL, new MyColor("#BBBBBB"), "Rubber");
        Toy toy3 = new Toy("Car", Size.LARGE, new MyColor("#CCCCCC"), "Metal");

        toyRepository.add(toy1);
        toyRepository.add(toy2);
        toyRepository.add(toy3);

        List<Toy> cars = toyRepository.findByType("Car");

        assertEquals(2, cars.size());
        for (Toy t : cars) {
            assertEquals("Car", t.getType());
        }
    }

    @Test
    void testFindBySize() {
        Toy toy1 = new Toy("Car", Size.MEDIUM, new MyColor("#AAAAAA"), "Plastic");
        Toy toy2 = new Toy("Ball", Size.SMALL, new MyColor("#BBBBBB"), "Rubber");
        Toy toy3 = new Toy("Doll", Size.MEDIUM, new MyColor("#CCCCCC"), "Fabric");

        toyRepository.add(toy1);
        toyRepository.add(toy2);
        toyRepository.add(toy3);

        List<Toy> mediumToys = toyRepository.findBySize(Size.MEDIUM);

        assertEquals(2, mediumToys.size());
        for (Toy t : mediumToys) {
            assertEquals(Size.MEDIUM, t.getSize());
        }
    }

    @Test
    void testMapRowToToy() throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        when(rs.getInt("id")).thenReturn(1);
        when(rs.getString("type")).thenReturn("Car");
        when(rs.getString("color_code")).thenReturn("#FF0000");
        when(rs.getString("size")).thenReturn("MEDIUM");
        when(rs.getString("material")).thenReturn("Plastic");

        Toy toy = toyRepository.mapRowToToy(rs);

        assertEquals(1, toy.getId());
        assertEquals("Car", toy.getType());
        assertEquals("#FF0000", toy.getColor().getHexCode());
        assertEquals(Size.MEDIUM, toy.getSize());
        assertEquals("Plastic", toy.getMaterial());
    }
    @Test
    void testDeleteById_notFound() {
        boolean result = toyRepository.deleteById(9999); // nonexistent ID
        assertEquals(false, result);
    }
    @Test
    void testDeleteById_sqlException() throws SQLException {
        Connection mockConn = mock(Connection.class);
        when(mockConn.prepareStatement(any())).thenThrow(new SQLException("DB error"));

        // Inject mockConn into repository (requires refactoring to allow injecting Connection)
        // Then call deleteById and assert it returns false
    }
}
