package com.document.model;

import com.user.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DocumentDAO {
    private static final String url = "jdbc:mysql://localhost:3306/paperlessworld";
    private static final String user = "test";
    private static final String password = "password";

    public void insertDocument(Document document) {
        String query = "INSERT INTO documents (title, dateCreated, dateModified, size, author, fileName) VALUES (?,?,?,?,?,?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(query))  {

            ps.setString(1, document.getTitle());
            ps.setString(2, document.getDateCreated());
            ps.setString(3, document.getDateModified());
            ps.setString(4, document.getSize());
            ps.setString(5, document.getAuthor());
            ps.setString(6, document.getFilename());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Document> getALlDocuments() {
        String query = "SELECT * FROM documents";
        List<Document> documents = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(query)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                documents.add(new Document(
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("size"),
                        rs.getString("fileName"),
                        rs.getString("dateCreated"),
                        rs.getString("dateModified")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return documents;
    }

    public Document getDocumentById(int id) {
        String query = "SELECT * FROM documents WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return new Document(
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("size"),
                        rs.getString("fileName"),
                        rs.getString("dateCreated"),
                        rs.getString("dateModified")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
