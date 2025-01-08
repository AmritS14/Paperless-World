package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/loginProcess")
public class LoginProcess extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        boolean isAuthenticated = false;
        String name = "";

        try {
            // Database connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/paperlessworld", "test", "password");

            // Query to validate login credentials
            String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                isAuthenticated = true;
                name = resultSet.getString("name");
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Database connection or query error: " + e.getMessage());
            return;
        }

        if (isAuthenticated) {
            // Successful login
            HttpSession session = request.getSession();
            session.setAttribute("user", name);
            response.sendRedirect("index.jsp"); // Redirect to dashboard or another page
        } else {
            // Unsuccessful login
            request.setAttribute("errorMessage", "Invalid email or password!");
            request.getRequestDispatcher("login.jsp").forward(request, response); // Forward back to login page
        }
    }
}
