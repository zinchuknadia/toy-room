package org.example.toyroom.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.example.DatabaseConnector;
import org.example.toyroom.models.MyColor;
import org.example.toyroom.models.Size;
import org.example.toyroom.models.toys.Toy;

public class ToyRepository {

    private static final Logger logger = LoggerFactory.getLogger(ToyRepository.class);

    public void add(Toy toy) {
        logger.info("Adding toy: {}", toy);
        String sql = "INSERT INTO toys (type, size, color_code, material, price, image_path) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, toy.getType());
            stmt.setString(2, toy.getSize().name());
            stmt.setString(3, toy.getColor().getHexCode());
            stmt.setString(4, toy.getMaterial());
            stmt.setDouble(5, toy.getPrice());
            stmt.setString(6, toy.getImagePath());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    toy.setId(generatedKeys.getInt(1));
                }
            }
            logger.debug("Executed insert statement");

        } catch (SQLException e) {
            logger.error("Failed to insert toy: {}", toy, e); // Це відправиться також на email
        }
    }

    public void saveToys(List<Toy> toys) {
        logger.info("Adding toys: {}", toys.toString());
        String query = "INSERT INTO toys (type, size, color_code, material, price, image_path) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            for (Toy toy : toys) {
                stmt.setString(1, toy.getType());
                stmt.setString(2, toy.getSize().toString());
                stmt.setString(3, toy.getColor().getHexCode()); // або інший getHex()/getName()
                stmt.setString(4, toy.getMaterial());
                stmt.setDouble(5, toy.getPrice());
                stmt.setString(6, toy.getImagePath());
                stmt.addBatch();
            }

            stmt.executeBatch();
            logger.debug("Executed insert statement");
        } catch (SQLException e) {
            logger.error("Failed to save toys: " + toys.toString(), e);
        }
    }

    public List<Toy> findAll() {
        logger.info("Finding all toys ");
        List<Toy> toys = new ArrayList<>();
        String sql = "SELECT * FROM toys";

        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Toy toy = mapRowToToy(rs);
                toys.add(toy);
            }
            logger.debug("Executed select statement");
        } catch (SQLException e) {
            logger.error("Failed to retrieve all toys", e);
        }

        return toys;
    }

    public List<Toy> findAllSortedByColor() {
        logger.info("Finding toys sorted by color ");
        List<Toy> toys = new ArrayList<>();
        String sql = "SELECT * FROM toys ORDER BY color_code";

        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Toy toy = mapRowToToy(rs);
                toys.add(toy);
            }
            logger.debug("Executed select statement");
        } catch (SQLException e) {
            logger.error("Failed find toys sorted by color: ", e);
        }

        return toys;
    }

    public List<Toy> findAllSortedBySize() {
        logger.info("Finding toys sorted by size ");
        List<Toy> toys = new ArrayList<>();
        String sql = "SELECT * FROM toys ORDER BY \n" +
                "  CASE size\n" +
                "    WHEN 'SMALL' THEN 1\n" +
                "    WHEN 'MEDIUM' THEN 2\n" +
                "    WHEN 'LARGE' THEN 3\n" +
                "  END;\n";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Toy toy = mapRowToToy(rs);
                toys.add(toy);
            }
            logger.debug("Executed select statement");
        } catch (SQLException e) {
            logger.error("Failed to find toys sorted by size: ", e);
        }

        return toys;
    }

    public List<Toy> findByColor(MyColor color) {
        logger.info("Finding toys by color {} ", color);
        List<Toy> toys = new ArrayList<>();
        String sql = "SELECT * FROM toys WHERE color_code = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, color.getHexCode()); // assumes Color class has a method getHexCode()
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Toy toy = mapRowToToy(rs);
                    toys.add(toy);
                }
            }
            logger.debug("Executed select statement");

        } catch (SQLException e) {
            logger.error("Failed to find toys by color", e);
        }

        return toys;
    }

    public List<Toy> findByType(String type) {
        logger.info("Finding toys by type {} ", type);
        List<Toy> toys = new ArrayList<>();
        String sql = "SELECT * FROM toys WHERE type = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, type);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Toy toy = mapRowToToy(rs);
                    toys.add(toy);
                }
            }
            logger.debug("Executed select statement");

        } catch (SQLException e) {
            logger.error("Failed to find toys by type", e);
        }

        return toys;
    }


    public List<Toy> findBySize(Size size) {
        logger.info("Finding toys by size {} ", size);
        List<Toy> toys = new ArrayList<>();
        String sql = "SELECT * FROM toys WHERE size = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, size.name());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Toy toy = mapRowToToy(rs);
                    toys.add(toy);
                }
            }
            logger.debug("Executed select statement");

        } catch (SQLException e) {
            logger.error("Failed to find toys by size", e);
        }

        return toys;
    }

    public boolean deleteById(int id) {
        logger.info("Deleting toy with id {} ", id);
        String sql = "DELETE FROM toys WHERE id = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();

            logger.debug("Executed delete statement");
            return rowsAffected > 0;
        } catch (SQLException e) {
            logger.error("Failed to delete toy with id=" + id, e);
        }

        return false;
    }

    public Toy mapRowToToy(ResultSet rs) throws SQLException {
        Toy toy = new Toy();
        toy.setId(rs.getInt("id"));
        toy.setType(rs.getString("type"));
        toy.setColor(new MyColor(rs.getString("color_code")));
        toy.setSize(Size.valueOf(rs.getString("size")));
        toy.setMaterial(rs.getString("material"));
        toy.setPrice(rs.getDouble("price"));
        toy.setImagePath(rs.getString("image_path"));
        return toy;
    }
}
