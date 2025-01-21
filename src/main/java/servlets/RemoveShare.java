package servlets;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.*;
import com.document.model.DocumentDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/removeSharedDocument")
public class RemoveShare extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get document ID from request
        int documentId = Integer.parseInt(request.getParameter("id"));
        int userId = Integer.parseInt(request.getSession().getAttribute("userId").toString());

        String query = "DELETE FROM shared WHERE documentId = ? AND userId = ?";


        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/paperlessworld",
                "test", "password");
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, documentId);
            ps.setInt(2, userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}