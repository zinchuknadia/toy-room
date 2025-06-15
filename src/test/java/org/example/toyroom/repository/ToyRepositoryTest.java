package org.example.toyroom.repository;

import org.example.DatabaseConnector;
import org.example.toyroom.models.MyColor;
import org.example.toyroom.models.Size;
import org.example.toyroom.models.Toy;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ToyRepositoryTest {

    private ToyRepository toyRepository;

    @BeforeAll
    static void setupDatabase() throws SQLException {
        DatabaseConnector.setTestConfig("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1", "sa", "");

        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE toys (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "type VARCHAR(255), " +
                    "size VARCHAR(255), " +
                    "color_code VARCHAR(255), " +
                    "material VARCHAR(255), " +
                    "price DOUBLE, " +
                    "image_path VARCHAR(255))");
        }
    }

    @BeforeEach
    void init() throws SQLException {
        toyRepository = new ToyRepository();
        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM toys");
        }
    }

    private Toy createToy(String type, Size size, String color, String material, double price, String imagePath) {
        return new Toy(type, size, new MyColor(color), material, price, imagePath);
    }

    @Test
    void testAddAndFindAll() {
        Toy toy = createToy("Car", Size.MEDIUM, "#FF0000", "Plastic", 9.99, "/img/car.png");
        toyRepository.add(toy);

        List<Toy> allToys = toyRepository.findAll();
        assertEquals(1, allToys.size());

        Toy savedToy = allToys.get(0);
        assertEquals("Car", savedToy.getType());
        assertEquals(Size.MEDIUM, savedToy.getSize());
        assertEquals("#FF0000", savedToy.getColor().getHexCode());
        assertEquals("Plastic", savedToy.getMaterial());
        assertEquals(9.99, savedToy.getPrice());
        assertEquals("/img/car.png", savedToy.getImagePath());
    }

//    @Test
//    void testFindAllSortedByColor() {
//        toyRepository.add(createToy("Ball", Size.SMALL, "#00FF00", "Rubber", 3.5, "/img/ball.png"));
//        toyRepository.add(createToy("Doll", Size.LARGE, "#0000FF", "Fabric", 7.8, "/img/doll.png"));
//        toyRepository.add(createToy("Car", Size.MEDIUM, "#FF0000", "Plastic", 10.0, "/img/car.png"));
//
//        List<Toy> toys = toyRepository.findAllSortedByColor();
//        assertEquals(3, toys.size());
//        assertEquals("#0000FF", toys.get(0).getColor().getHexCode());
//        assertEquals("#00FF00", toys.get(1).getColor().getHexCode());
//        assertEquals("#FF0000", toys.get(2).getColor().getHexCode());
//    }
//
//    @Test
//    void testFindAllSortedBySize() {
//        toyRepository.add(createToy("Ball", Size.SMALL, "#00FF00", "Rubber", 3.5, "/img/ball.png"));
//        toyRepository.add(createToy("Doll", Size.LARGE, "#0000FF", "Fabric", 7.8, "/img/doll.png"));
//        toyRepository.add(createToy("Car", Size.MEDIUM, "#FF0000", "Plastic", 10.0, "/img/car.png"));
//
//        List<Toy> toys = toyRepository.findAllSortedBySize();
//        assertEquals(3, toys.size());
//        assertEquals(Size.SMALL, toys.get(0).getSize());
//        assertEquals(Size.MEDIUM, toys.get(1).getSize());
//        assertEquals(Size.LARGE, toys.get(2).getSize());
//    }
//
//    @Test
//    void testFindByColor() {
//        toyRepository.add(createToy("Car", Size.MEDIUM, "#AAAAAA", "Plastic", 5.0, "/img/car.png"));
//        toyRepository.add(createToy("Ball", Size.SMALL, "#BBBBBB", "Rubber", 3.5, "/img/ball.png"));
//        toyRepository.add(createToy("Doll", Size.LARGE, "#AAAAAA", "Fabric", 7.8, "/img/doll.png"));
//
//        List<Toy> result = toyRepository.findByColor(new MyColor("#AAAAAA"));
//        assertEquals(2, result.size());
//        assertTrue(result.stream().allMatch(t -> t.getColor().getHexCode().equals("#AAAAAA")));
//    }
//
//    @Test
//    void testFindByType() {
//        toyRepository.add(createToy("Car", Size.MEDIUM, "#AAAAAA", "Plastic", 5.0, "/img/car.png"));
//        toyRepository.add(createToy("Ball", Size.SMALL, "#BBBBBB", "Rubber", 3.5, "/img/ball.png"));
//        toyRepository.add(createToy("Car", Size.LARGE, "#CCCCCC", "Metal", 6.0, "/img/car2.png"));
//
//        List<Toy> result = toyRepository.findByType("Car");
//        assertEquals(2, result.size());
//        assertTrue(result.stream().allMatch(t -> t.getType().equals("Car")));
//    }
//
//    @Test
//    void testFindBySize() {
//        toyRepository.add(createToy("Car", Size.MEDIUM, "#AAAAAA", "Plastic", 5.0, "/img/car.png"));
//        toyRepository.add(createToy("Ball", Size.SMALL, "#BBBBBB", "Rubber", 3.5, "/img/ball.png"));
//        toyRepository.add(createToy("Doll", Size.MEDIUM, "#CCCCCC", "Fabric", 7.8, "/img/doll.png"));
//
//        List<Toy> result = toyRepository.findBySize(Size.MEDIUM);
//        assertEquals(2, result.size());
//        assertTrue(result.stream().allMatch(t -> t.getSize() == Size.MEDIUM));
//    }
//
//    @Test
//    void testSaveToys() throws SQLException {
//        List<Toy> toys = List.of(
//                createToy("Car", Size.MEDIUM, "#FF0000", "Plastic", 10.0, "/img/car.png"),
//                createToy("Doll", Size.SMALL, "#00FF00", "Fabric", 6.0, "/img/doll.png")
//        );
//
//        toyRepository.saveToys(toys);
//
//        try (Connection conn = DatabaseConnector.getConnection();
//             Statement stmt = conn.createStatement();
//             ResultSet rs = stmt.executeQuery("SELECT * FROM toys")) {
//
//            List<Toy> saved = new ArrayList<>();
//            while (rs.next()) {
//                saved.add(toyRepository.mapRowToToy(rs));
//            }
//
//            assertEquals(2, saved.size());
//            assertEquals("Car", saved.get(0).getType());
//            assertEquals("Doll", saved.get(1).getType());
//        }
//    }

    @Test
    void testDeleteById() {
        Toy toy = createToy("Train", Size.LARGE, "#123456", "Wood", 8.5, "/img/train.png");
        toyRepository.add(toy);

        int idToDelete = toy.getId();
        boolean deleted = toyRepository.deleteById(idToDelete);
        assertTrue(deleted);

        List<Toy> toysAfter = toyRepository.findAll();
        assertEquals(0, toysAfter.size());
    }

    @Test
    void testDeleteByIdNotFound() {
        boolean result = toyRepository.deleteById(9999);
        assertFalse(result);
    }
}
