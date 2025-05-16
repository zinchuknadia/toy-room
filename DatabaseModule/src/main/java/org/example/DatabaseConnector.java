package org.example;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    public static Connection getConnection() throws SQLException {
        Dotenv dotenv = Dotenv.load();

        String url = dotenv.get("URL");
        String user = dotenv.get("USER");
        String password = dotenv.get("PASSWORD");

        return DriverManager.getConnection(url, user, password);
    }
}