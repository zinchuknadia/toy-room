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
            logger.error("Failed to delete toy with id={}", id, e);
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
