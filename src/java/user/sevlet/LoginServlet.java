package user.sevlet;

import entity.User;
import DAO.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            UserDAO dao = new UserDAO();

            String email = req.getParameter("email");
            String password = req.getParameter("password");
            String rememberMe = req.getParameter("rememberMe");

            // Authenticate user from database
            User user = dao.login(email, password);

            if (user != null) {
                HttpSession session = req.getSession();
                session.setAttribute("userobj", user);

                // Store in cookies if "Remember Me" is checked
                if ("on".equals(rememberMe)) {
                    saveUserCookies(req, resp, email);
                }

                // Redirect admin to admin panel
                if ("admin@gmail.com".equals(email)) {
                    resp.sendRedirect("admin/home.jsp");
                } else {
                    resp.sendRedirect("index.jsp");
                }
            } else {
                req.setAttribute("email", email); // Keep email on failure
                req.setAttribute("failedMsg", "Invalid Email or Password");
                req.getRequestDispatcher("login.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("failedMsg", "Login Error: " + e.getMessage());
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }

    private void saveUserCookies(HttpServletRequest req, HttpServletResponse resp, String email) {
        Cookie emailCookie = new Cookie("user_email", email);
        emailCookie.setMaxAge(7 * 24 * 60 * 60); // 7 days
        emailCookie.setHttpOnly(true);

        if (!req.getServerName().equals("localhost")) {
            emailCookie.setSecure(true);
        }

        resp.addCookie(emailCookie);
    }
}
