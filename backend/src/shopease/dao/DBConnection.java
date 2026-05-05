package shopease.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// ── JDBC Connectivity ──
// ── OOP: Singleton Pattern — only one DB connection instance ──
public class DBConnection {

    // ── CHANGE THESE to match your MySQL setup ──
    private static final String URL      = "jdbc:mysql://localhost:3306/shopease_db";
    private static final String USER     = "root";
    private static final String PASSWORD = "your_password_here";  // ← change this

    private static Connection connection = null;

    // ── Private constructor — prevents instantiation ──
    private DBConnection() {}

    // ── Returns single shared connection ──
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("✅ Database connected successfully!");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("❌ MySQL Driver not found. Add mysql-connector-j.jar to lib/");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("❌ DB Connection failed: " + e.getMessage());
            System.out.println("   → Make sure MySQL is running and shopease_db exists.");
            e.printStackTrace();
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
