package servlets;

import com.document.model.DocumentDAO;
import com.document.model.Document;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@WebServlet("/uploadDocument")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024, // 1 MB
    maxFileSize = 1024 * 1024 * 10,  // 10 MB
    maxRequestSize = 1024 * 1024 * 15 // 15 MB
)
public class UploadDocumentServlet extends HttpServlet {
    private static final String UPLOAD_DIRECTORY = "docs";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {

            // Create upload directory if it doesn't exist
            String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;
            Files.createDirectories(Paths.get(uploadPath));

            // Process the uploaded file
            Part filePart = request.getPart("documentFile");
            String fileName = getSubmittedFileName(filePart);
            String fileExtension = getFileExtension(fileName);

            // Validate file extension
            if (!isValidFileExtension(fileExtension)) {
                request.setAttribute("error", "Invalid file type. Supported formats: PDF, DOC, DOCX, TXT");
                request.getRequestDispatcher("addDocument.jsp").forward(request, response);
                return;
            }

            // Generate unique file name
            String uniqueFileName = UUID.randomUUID().toString() + "." + fileExtension;
            String filePath = uploadPath + File.separator + uniqueFileName;

            // Save the file
            filePart.write(filePath);

            // Format current date/time
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String currentDateTime = LocalDateTime.now().format(formatter);

            // Format file size
            long fileSizeInBytes = filePart.getSize();
            String formattedSize = formatFileSize(fileSizeInBytes);

            // Create document object
            Document document = new Document(
                request.getParameter("title"),
                request.getSession().getAttribute("user").toString(),
                formattedSize,
                uniqueFileName,
                currentDateTime,
                currentDateTime,
                fileExtension.toUpperCase()
            );

            // Save to database
            DocumentDAO documentDAO = new DocumentDAO();
            documentDAO.insertDocument(document);

            // Redirect to documents page
            response.sendRedirect("documents");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while uploading the document: " + e.getMessage());
            request.getRequestDispatcher("addDocument.jsp").forward(request, response);
        }
    }

    private String formatFileSize(long bytes) {
        if (bytes < 1024) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(1024));
        String pre = "KMGTPE".charAt(exp-1) + "";
        return String.format("%.1f %sB", bytes / Math.pow(1024, exp), pre);
    }

    private String getSubmittedFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] tokens = contentDisp.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length() - 1);
            }
        }
        return "";
    }

    private String getFileExtension(String fileName) {
        if (fileName == null) return "";
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex == -1) return "";
        return fileName.substring(lastDotIndex + 1).toLowerCase();
    }

    private boolean isValidFileExtension(String extension) {
        return extension != null && (
            extension.equals("pdf") ||
            extension.equals("doc") ||
            extension.equals("docx") ||
            extension.equals("txt")
        );
    }
} 