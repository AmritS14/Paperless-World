package servlets;

import com.document.model.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/shareDocument")
public class ShareDocumentServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            int documentId = Integer.parseInt(request.getParameter("documentId"));
            String shareWithUsername = request.getParameter("shareWithUser");

            if (shareWithUsername == null || shareWithUsername.trim().isEmpty()) {
                response.sendRedirect("documents?error=Username cannot be empty");
                return;
            }

            DocumentDAO documentDAO = new DocumentDAO();
            int status = documentDAO.shareDocument(documentId, shareWithUsername);

            switch (status) {
                case 0:
                    response.sendRedirect("documents?message=Document shared successfully");
                    break;

                case 1:
                    response.sendRedirect("documents?error=User not found!");
                    break;

                case 2:
                    response.sendRedirect("documents?error=Document is already shared with this user");
                    break;

                case 3:
                default:
                    response.sendRedirect("documents?error=Unknown error!");
                    break;
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("documents?error=Invalid document ID");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("documents?error=An error occurred while sharing the document");
        }
    }
} 