package user.servlet;

import entity.User;
import DAO.UserDAO;
import java.io.IOException;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String name = req.getParameter("fname");
            String email = req.getParameter("email");
            String phone = req.getParameter("phone");
            String password = req.getParameter("password");

            HttpSession session = req.getSession();
            UserDAO dao = new UserDAO();

            boolean success = dao.register(name, email, password, phone);

            if (success) {
                session.setAttribute("succMsg", "Registration Successful.");
                resp.sendRedirect("register.jsp");
            } else {
                session.setAttribute("failedMSG", "Registration Failed. Try Again.");
                resp.sendRedirect("register.jsp");
            }
        } catch (SQLException e) {
            String errorMessage = "Registration Failed. Try Again." + e.getMessage();
            if (e.getMessage().contains("UQ__User__B43B145F")) {
                errorMessage = "Phone number already exists. Please use a different one.";
            } else if (e.getMessage().contains("UQ__User__AB6E61648CB4D333")) {
                errorMessage = "Email already registered. Try another email.";
            }

            req.setAttribute("failedMSG", errorMessage);
            req.setAttribute("fname", req.getParameter("fname"));
            req.setAttribute("email", req.getParameter("email"));
            req.setAttribute("phone", req.getParameter("phone"));

            req.getRequestDispatcher("register.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("failedMSG", "Error: " + e.getMessage());
            req.getRequestDispatcher("register.jsp").forward(req, resp);
        }
    }
}
