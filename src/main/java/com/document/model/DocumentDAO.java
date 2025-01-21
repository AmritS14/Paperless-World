package com.document.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DocumentDAO {
    private static final String url = "jdbc:mysql://localhost:3306/paperlessworld";
    private static final String user = "test";
    private static final String password = "password";

    public void insertDocument(Document document) {

        String query = "INSERT INTO documents (title, dateCreated, dateModified, size, fileName, type, authorId) VALUES (?,?,?,?,?,?,?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(query))  {

            int authorId = 0;
            // Get author name from users table
            String authorQuery = "SELECT userId FROM users WHERE name = ?";
            try (PreparedStatement authorPs = conn.prepareStatement(authorQuery)) {
                authorPs.setString(1, document.getAuthor());
                ResultSet authorRs = authorPs.executeQuery();
                if (authorRs.next()) {
                    authorId = authorRs.getInt("userId");
                }
            }

            ps.setString(1, document.getTitle());
            ps.setString(2, document.getDateCreated());
            ps.setString(3, document.getDateModified());
            ps.setString(4, document.getSize());
            ps.setString(5, document.getFileName());
            ps.setString(6, document.getType());
            ps.setInt(7, authorId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Document> getAllDocuments(int userId) {
        String query = "SELECT * FROM documents WHERE authorId = ?";
        List<Document> documents = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();
            String authorName = "";

            // Get author name from users table
            String authorQuery = "SELECT name FROM users WHERE userId = ?";
            try (PreparedStatement authorPs = conn.prepareStatement(authorQuery)) {
                authorPs.setInt(1, userId);
                ResultSet authorRs = authorPs.executeQuery();
                if (authorRs.next()) {
                    authorName = authorRs.getString("name");
                }
            }
            
            while (rs.next()) {
                Document doc = new Document(
                        rs.getString("title"),
                        authorName,
                        rs.getString("size"),
                        rs.getString("fileName"),
                        rs.getString("dateCreated"),
                        rs.getString("dateModified"),
                        rs.getString("type")
                );
                doc.setId(rs.getInt("id"));
                documents.add(doc);
            }
            
            System.out.println("Query executed: " + query);
            System.out.println("Username parameter: " + userId);
            System.out.println("Found " + documents.size() + " documents");
            
        } catch (Exception e) {
            System.out.println("Error in getAllDocuments: ");
            e.printStackTrace();
        }

        return documents;
    }

    public Document getDocumentById(int documentId) {
        String query = "SELECT d.*, u.name as authorName FROM documents d " +
                      "JOIN users u ON d.authorId = u.userId " +
                      "WHERE d.id = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, documentId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                Document doc = new Document(
                    rs.getString("title"),
                    rs.getString("authorName"),
                    rs.getString("size"),
                    rs.getString("fileName"),
                    rs.getString("dateCreated"),
                    rs.getString("dateModified"),
                    rs.getString("type")
                );
                doc.setId(rs.getInt("id"));
                return doc;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void deleteDocument(int id) {
        String query = "DELETE FROM documents WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, id);
            ps.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Document> getSharedDocuments(int userId) {
        String query = "SELECT d.* FROM documents d " +
                      "JOIN shared s ON d.id = s.documentId " +
                      "WHERE s.userId = ?";
        List<Document> documents = new ArrayList<>();
        String authorName = "";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setInt(1, userId);
            // Get author name from users table


            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String authorQuery = "SELECT name FROM users WHERE userId = ?";
                try (PreparedStatement authorPs = conn.prepareStatement(authorQuery)) {
                    authorPs.setInt(1, rs.getInt("authorId"));
                    ResultSet authorRs = authorPs.executeQuery();
                    if (authorRs.next()) {
                        authorName = authorRs.getString("name");
                    }
                }
                Document doc = new Document(
                        rs.getString("title"),
                        authorName,
                        rs.getString("size"),
                        rs.getString("fileName"),
                        rs.getString("dateCreated"),
                        rs.getString("dateModified"),
                        rs.getString("type")
                );
                doc.setId(rs.getInt("id"));
                documents.add(doc);
            }
            
        } catch (Exception e) {
            System.out.println("Error in getSharedDocuments: ");
            e.printStackTrace();
        }

        return documents;
    }

    public boolean isSharedWithUser(int documentId, String userName) {
        String query = "SELECT * FROM shared s JOIN users u ON s.userId=u.userId WHERE s.documentId = ? and u.name = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, documentId);
            ps.setString(2, userName);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
            return false;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int shareDocument(int documentId, String shareWithUsername) {
        if (isSharedWithUser(documentId, shareWithUsername)) {
            return 2;
        }

        String query = "INSERT INTO shared (documentId, userId) " +
                      "SELECT ?, userId FROM users WHERE name = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setInt(1, documentId);
            ps.setString(2, shareWithUsername);
            
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0)
                return 0;
            else
                return 1;
            
        } catch (SQLIntegrityConstraintViolationException e) {
            // Document is already shared with this user
            System.out.println("Document is already shared with this user");
            return 2;
        } catch (Exception e) {
            e.printStackTrace();
            return 3;
        }
    }
}
