package utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataConnectionConfigure {
    private static String username = "postgres";
    private static String password = "12534";
//    private static String url = "jdbc:postgresql://localhost:5432/postgres";
    private static String url = "jdbc:postgresql://localhost:5432/user_db";
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    url,
                    username,
                    password
            );
        } catch (Exception exception) {
            System.out.println("Error during Establishing Connection to database: " + exception.getMessage());
        }
        return null;
    }
}