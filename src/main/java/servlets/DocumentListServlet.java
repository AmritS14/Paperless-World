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
        DocumentDAO documentDAO = new DocumentDAO();
        List<Document> documents = documentDAO.getALlDocuments();
        
        // Debug print
        System.out.println("Number of documents: " + documents.size());
        for (Document doc : documents) {
            System.out.println("Document: " + doc.getTitle());
        }
        
        request.setAttribute("documentList", documents);  // Changed attribute name
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
} 