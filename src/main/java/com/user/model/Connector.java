package com.user.model;

import java.sql.*;

public class Connector {
    private static final String url = "jdbc:mysql://localhost:3306/paperlessworld";
    private static final String user = "test";
    private static final String password = "password";

    public static void main(String[] args) {
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        UserDAO dao = new UserDAO();
//        User u1 = new User(1, "Peter Parker", "12345", "test@test");
//
//        dao.insertUser(u1);

        System.out.println(dao.getAllUsers());
    }

//    public void insertUser(User user) {
//        Connector dao = new Connector();
//
//        try (Connection conn = dao.getConnection()) {
//            PreparedStatement s = conn.prepareStatement(INSERT_USER);
//        }
//    }
}
