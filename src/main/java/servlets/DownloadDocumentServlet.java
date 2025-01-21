package servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.document.model.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/downloadDocument")
public class DownloadDocumentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Get document ID from request
        int id = Integer.parseInt(request.getParameter("id"));

        // Get document from database using id
        DocumentDAO documentDAO = new DocumentDAO();
        Document document = documentDAO.getDocumentById(id);
        
        if (document == null) {
            System.out.println("Document not found with id: " + id);
            response.sendRedirect("error.jsp");
            return;
        }
        
        String fileName = document.getFileName();
        
        try {
            // Get the webapp directory path using ServletContext
            String webappPath = null;
            try {
                webappPath = getServletContext().getRealPath("/");
                System.err.println("Debug - Webapp Path: " + webappPath);
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Error getting webapp path: " + e.getMessage());
            }
            
            String docsPath = webappPath + "docs" + File.separator;
            File file = new File(docsPath + fileName);
            
            System.err.println("==== DEBUG OUTPUT START ====");
            System.err.println("Debug - File details:");
            System.err.println("Webapp Path: " + (webappPath != null ? webappPath : "null"));
            System.err.println("Docs Path: " + docsPath);
            System.err.println("Full file path: " + file.getAbsolutePath());
            System.err.println("File exists: " + file.exists());
            System.err.println("==== DEBUG OUTPUT END ====");
            
            if (file.exists()) {
                // Set response headers
                response.setContentType("application/octet-stream");
                response.setHeader("Content-Disposition", 
                    "attachment; filename=\"" + fileName + "\"");
                
                // Copy file to response output stream
                try (FileInputStream in = new FileInputStream(file);
                     OutputStream out = response.getOutputStream()) {
                    
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = in.read(buffer)) != -1) {
                        out.write(buffer, 0, bytesRead);
                    }
                    out.flush();
                }
            } else {
                System.out.println("File not found: " + file.getAbsolutePath());
                response.sendRedirect("error.jsp");
            }
            
        } catch (Exception e) {
            System.out.println("Error downloading file: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
} 