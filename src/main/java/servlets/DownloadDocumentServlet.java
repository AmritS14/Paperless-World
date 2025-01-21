package servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

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
        
        try {
            // Get document ID from request
            int id = Integer.parseInt(request.getParameter("id"));

            // Get document from database using id
            DocumentDAO documentDAO = new DocumentDAO();
            Document document = documentDAO.getDocumentById(id);
            
            if (document == null) {
                System.out.println("Document not found with id: " + id);
                response.sendRedirect("documents?error=" + URLEncoder.encode("Document not found", StandardCharsets.UTF_8));
                return;
            }
            
            String fileName = document.getFileName();
            
            // Get the webapp directory path using ServletContext
            String webappPath = getServletContext().getRealPath("/");
            String docsPath = webappPath + "docs" + File.separator;
            File file = new File(docsPath + fileName);
            
            System.out.println("Debug - File path: " + file.getAbsolutePath());
            
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
                String errorMessage = "File not found: " + fileName;
                System.out.println("Error: " + errorMessage);
                response.sendRedirect("documents?error=" + URLEncoder.encode(errorMessage, StandardCharsets.UTF_8));
            }
            
        } catch (NumberFormatException e) {
            response.sendRedirect("documents?error=" + URLEncoder.encode("Invalid document ID", StandardCharsets.UTF_8));
        } catch (Exception e) {
            System.out.println("Error downloading file: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect("documents?error=" + URLEncoder.encode("Error downloading file: " + e.getMessage(), StandardCharsets.UTF_8));
        }
    }
} 