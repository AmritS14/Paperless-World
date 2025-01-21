package servlets;

import com.document.model.Document;
import com.document.model.DocumentDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/documents")
public class DocumentListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String username = (String) request.getSession().getAttribute("user");
        int userId = (int) request.getSession().getAttribute("userId");
        
        DocumentDAO documentDAO = new DocumentDAO();
        List<Document> documents = documentDAO.getAllDocuments(userId);
        List<Document> sharedDocuments = documentDAO.getSharedDocuments(userId);
        
        // Debug print
        System.out.println("User: " + username);
        System.out.println("Number of owned documents: " + documents.size());
        System.out.println("Number of shared documents: " + sharedDocuments.size());
        
        request.setAttribute("documentList", documents);
        request.setAttribute("sharedDocumentList", sharedDocuments);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
} 