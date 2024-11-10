package com.user.model;

import java.sql.*;

public class UserDAO {
    private static final String url = "jdbc:mysql://localhost:3306/paperlessworld";
    private static final String user = "test";
    private static final String password = "password";

    private static final String INSERT_USER = "INSERT INTO users (userId, name, email, password) VALUES (?,?,?,?)";
    private static final String SELECT_USER_BY_ID = "SELECT * FROM users WHERE userId = ?";
    private static final String SELECT_ALL_USERS = "SELECT * FROM users";
    private static final String DELETE_USER = "DELETE FROM users WHERE userId = ?";
    private static final String DELETE_ALL_USERS = "DELETE FROM users";
    private static final String UPDATE_USER = "UPDATE users SET name = ?, email = ?, password = ? WHERE userId = ?";

//    public UserDAO() {
//        super();
//    }

//    public Connection getConnection() {
//        Connection conn = null;
//
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            conn = (Connection) DriverManager.getConnection(url, user, password);
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return conn;
//    }

    public void insertUser(User u) {
        String query = "INSERT INTO users (name, email, password) VALUES (?,?,?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(query)) {

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
}
