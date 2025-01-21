package servlets;

import java.io.IOException;
import jakarta.servlet.*;
import com.document.model.DocumentDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/removeDocument")
public class RemoveDocumentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Get document ID from request
        int documentId = Integer.parseInt(request.getParameter("id"));
        
        try {
            // Get document service/DAO instance
            DocumentDAO documentDAO = new DocumentDAO();
            
            // Remove document
            documentDAO.deleteDocument(documentId);
            
            // Redirect back to documents page
            response.sendRedirect("documents");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 