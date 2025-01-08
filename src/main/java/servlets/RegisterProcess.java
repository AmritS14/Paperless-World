package servlets;

import com.user.model.User;
import com.user.model.UserDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/registerProcess")
public class RegisterProcess extends HttpServlet {
    public RegisterProcess() {
        super();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User user = new User(name, email, password);
        UserDAO userDAO = new UserDAO();
        userDAO.insertUser(user);

//        PrintWriter out = response.getWriter();
//        List<User> users = userDAO.getAllUsers();
//        for (User user1 : users) {
//            out.println(user1.getName() + " " + user1.getEmail() + " " + user1.getPassword());
//        }

        response.sendRedirect("login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}