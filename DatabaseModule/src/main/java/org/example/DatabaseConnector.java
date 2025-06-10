package org.example;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {

    private static Dotenv dotenv = Dotenv.load();

    private static String url = dotenv.get("URL");;
    private static String user = dotenv.get("USER");;
    private static String password = dotenv.get("PASSWORD");;

    public static void setTestConfig(String testUrl, String testUser, String testPassword) {
        url = testUrl;
        user = testUser;
        password = testPassword;
    }

    public static Connection getConnection() throws SQLException {
//        Dotenv dotenv = Dotenv.load();
//
//        url = dotenv.get("URL");
//        user = dotenv.get("USER");
//        password = dotenv.get("PASSWORD");

        return DriverManager.getConnection(url, user, password);
    }
}