package Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Databasehandler {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/chatapp"; // Update with your DB URL
    private static final String DB_USERNAME = "root"; // Your database username
    private static final String DB_PASSWORD = ""; // Your database password

    // Establish a connection to the database
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }

    // Validate login credentials
    public static boolean validateLogin(String username, String password) {
        boolean isValid = false;
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
             
            stmt.setString(1, username);
            stmt.setString(2, password); // You may want to hash the password in a real application

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    isValid = true; // User found with matching username and password
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isValid;
    }
}
