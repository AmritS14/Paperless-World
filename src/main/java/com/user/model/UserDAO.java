package com.user.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private static final String url = "jdbc:mysql://localhost:3306/paperlessworld";
    private static final String user = "test";
    private static final String password = "password";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void insertUser(User u) {
        String query = "INSERT INTO users (name, email, password) VALUES (?,?,?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(query))  {

            ps.setString(1, u.getName());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getPassword());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User getUser(int userId) {
        String query = "SELECT * FROM users WHERE userId = ?";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return new User(rs.getInt("userId"),
                                rs.getString("name"),
                                rs.getString("email"),
                                rs.getString("password"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<User> getAllUsers() {
        String query = "SELECT * FROM users";
        List<User> users = new ArrayList<User>();
        
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                users.add(new User(rs.getInt("userId"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password")));
            }
            return users;  // Return the populated list
        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;  // Return empty list if there's an exception
    }
}
