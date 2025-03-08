package admin.servelet;

import DAO.ProductDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;

@WebServlet(name = "DeleteBookServlet", urlPatterns = {"/delete_book"})
public class DeleteBookServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String ProductIdStr = request.getParameter("productId");

        try {
            int productId = Integer.parseInt(ProductIdStr);

            ProductDAO dao = new ProductDAO();
            boolean deleted = dao.deleteProduct(productId);

            if (deleted) {
                session.setAttribute("succMsg", "Product deleted successfully.");
            } else {
                session.setAttribute("errMsg", "Failed to delete the product.");
            }
        } catch (NumberFormatException e) {
            session.setAttribute("errMsg", "Invalid product ID format.");
        } catch (SQLException e) {
            session.setAttribute("errMsg", "Database error: " + e.getMessage());
        }

        response.sendRedirect("admin/all_books.jsp");
    }

    @Override
    public String getServletInfo() {
        return "Handles product deletion";
    }
}
