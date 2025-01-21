package servlets;

import com.document.model.Document;
import com.document.model.DocumentDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/viewDocument")
public class ViewDocumentServlet extends HttpServlet {
    private DocumentDAO documentDAO;

    public void init() {
        documentDAO = new DocumentDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int documentId = Integer.parseInt(request.getParameter("id"));
            Document document = documentDAO.getDocumentById(documentId);
            
            if (document != null) {
                request.setAttribute("document", document);
                request.getRequestDispatcher("document.jsp").forward(request, response);
            } else {
                response.sendRedirect("index.jsp");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("index.jsp");
        }
    }
} 