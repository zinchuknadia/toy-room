package org.example.toyroom.repository;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.example.DatabaseConnector;
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
//                toy.setName(rs.getString("name"));
                toy.setColor(rs.getString("color"));
                toy.setSize(Size.valueOf(rs.getString("size")));
//                toy.setPrice(rs.getDouble("price"));
                // Add other fields
                toys.add(toy);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return toys;
    }

    public void save(Toy toy) {}

    public void deleteById(int id) {}

    public List<Toy> findByColor(Color color) {
        return List.of();
    }
    // You can add save, deleteById, findById etc.
}
