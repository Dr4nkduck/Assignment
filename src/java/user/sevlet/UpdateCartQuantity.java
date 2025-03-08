package user.sevlet;

import DAO.CartDAO;
import entity.User;
import java.io.IOException;
import java.sql.SQLException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "UpdateCartQuantity", urlPatterns = {"/updateCartQuantity"})
public class UpdateCartQuantity extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int bookId = Integer.parseInt(request.getParameter("bookId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));

            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("userobj");
            if (user == null) {
                response.sendRedirect("login.jsp");
                return;
            }

            CartDAO cartDAO = new CartDAO();
            cartDAO.updateCartItem(user.getId(), bookId, quantity);

            double itemTotal = cartDAO.getItemTotal(user.getId(), bookId);
            double cartTotal = cartDAO.getCartTotal(user.getId());

            response.setContentType("application/json");
            response.getWriter().write("{\"success\": true, \"itemTotal\": " + itemTotal + ", \"cartTotal\": " + cartTotal + "}");
        } catch (SQLException e) {
            e.printStackTrace();
            response.setContentType("application/json");
            response.getWriter().write("{\"success\": false, \"error\": \"" + e.getMessage().replace("\"", "\\\"") + "\"}");
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("application/json");
            response.getWriter().write("{\"success\": false, \"error\": \"Invalid request.\"}");
        }
    }

}
