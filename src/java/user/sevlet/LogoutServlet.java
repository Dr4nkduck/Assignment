package user.sevlet;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "LogoutServlet", urlPatterns = {"/logout"})
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Invalidate the current session
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // Remove "Remember Me" cookies (if they exist)
        Cookie emailCookie = new Cookie("user_email", "");
        Cookie passwordCookie = new Cookie("user_password", "");
        emailCookie.setMaxAge(0);
        passwordCookie.setMaxAge(0);
        emailCookie.setPath("/");  // Ensure it applies to the entire app
        passwordCookie.setPath("/");

        response.addCookie(emailCookie);
        response.addCookie(passwordCookie);

        // Redirect to login page
        response.sendRedirect("login.jsp");
    }

    @Override
    public String getServletInfo() {
        return "Handles user logout by invalidating the session and clearing cookies";
    }
}
