package org.example.toyroom.repository;

//import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.example.DatabaseConnector;
import org.example.toyroom.models.Color;
import org.example.toyroom.models.Size;
import org.example.toyroom.models.Toy;

public class ToyRepository {

    public List<Toy> findAll() {
        List<Toy> toys = new ArrayList<>();
        String sql = "SELECT * FROM toys";

        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Toy toy = new Toy();
                toy.setId(rs.getInt("id"));
                toy.setType(rs.getString("type"));
                toy.setColor(new Color(rs.getString("color_code")));
                toy.setSize(Size.valueOf(rs.getString("size")));
                toy.setMaterial(rs.getString("material"));
                toys.add(toy);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return toys;
    }

    public void save(Toy toy) {
        String sql = "INSERT INTO toys (type, size, color_code, material) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, toy.getType());
            stmt.setString(2, toy.getSize().name());
            stmt.setString(3, toy.getColor().getHexCode());
            stmt.setString(4, toy.getMaterial());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM toys WHERE id = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Toy> findByColor(Color color) {
        List<Toy> toys = new ArrayList<>();
        String sql = "SELECT * FROM toys WHERE color_code = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, color.getHexCode()); // assumes Color class has a method getHexCode()
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Toy toy = new Toy();
                    toy.setId(rs.getInt("id"));
                    toy.setType(rs.getString("type"));
                    toy.setColor(new Color(rs.getString("color_code")));
                    toy.setSize(Size.valueOf(rs.getString("size")));
                    toy.setMaterial(rs.getString("material"));
                    toys.add(toy);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return toys;
    }

    public List<Toy> findBySize(Size size) {
        List<Toy> toys = new ArrayList<>();
        String sql = "SELECT * FROM toys WHERE size = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, size.name());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Toy toy = new Toy();
                    toy.setId(rs.getInt("id"));
                    toy.setType(rs.getString("type"));
                    toy.setColor(new Color(rs.getString("color_code")));
                    toy.setSize(Size.valueOf(rs.getString("size")));
                    toy.setMaterial(rs.getString("material"));
                    toys.add(toy);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return toys;
    }
}
