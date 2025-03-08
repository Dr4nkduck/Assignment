package admin.servelet;

import DAO.ProductDAO;
import entity.Product;
import entity.User;
import java.io.File;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.sql.SQLException;

@WebServlet("/edit_book")
@MultipartConfig
public class EditBookServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            response.setContentType("text/html;charset=UTF-8");
            HttpSession session = request.getSession();
            int productId = Integer.parseInt(request.getParameter("productId"));
            ProductDAO productDAO = new ProductDAO();
            Product product = productDAO.getProductById(productId); 

            if (product != null) {
                session.setAttribute("editProduct", product);
                response.sendRedirect("admin/edit_book.jsp");
            } else {
                session.setAttribute("errMsg", "Product not found");
                response.sendRedirect("admin/all_books.jsp");
            }
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("errMsg", "Invalid product ID format");
            response.sendRedirect("admin/all_books.jsp");
        } catch (Exception e) {
            request.getSession().setAttribute("errMsg", "An error occurred while fetching product details");
            response.sendRedirect("admin/all_books.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();

        // Check if user is logged in
        User user = (User) session.getAttribute("userobj");
        if (user == null) {
            session.setAttribute("failedMsg", "You must be logged in to edit a product!");
            response.sendRedirect("login.jsp");
            return;
        }
        String userEmail = user.getEmail();

        // Getting parameters from form
        String idStr = request.getParameter("id");
        int id;
        if (idStr != null && !idStr.isEmpty()) {
            try {
                id = Integer.parseInt(idStr);
            } catch (NumberFormatException e) {
                session.setAttribute("errMsg", "Invalid Product ID!");
                response.sendRedirect("edit_book.jsp");
                return;
            }
        } else {
            session.setAttribute("errMsg", "Product ID is missing!");
            response.sendRedirect("admin/edit_book.jsp");
            return;
        }

        String productName = request.getParameter("pname");
        String brand = request.getParameter("brand");
        String priceStr = request.getParameter("price");
        String productCategory = request.getParameter("ptype");
        String status = request.getParameter("pstatus");
        Part part = request.getPart("pimg");

        // Convert price to double
        double price = 0.0;
        if (priceStr != null && !priceStr.isEmpty()) {
            try {
                price = Double.parseDouble(priceStr);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        // Handling file upload
        String fileName = null;
        if (part != null && part.getSize() > 0) {
            fileName = part.getSubmittedFileName();
            String uploadPath = getServletContext().getRealPath("") + File.separator + "book";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String filePath = uploadPath + File.separator + fileName;
            part.write(filePath);
        } else {
            fileName = request.getParameter("oldPhoto");
        }

        // Create Book object
        Product product = new Product(id, productName, brand, price, productCategory, status, fileName, userEmail);

        // Validate input
        String validationError = validate(product);
        if (validationError != null) {
            session.setAttribute("errMsg", validationError);
            response.sendRedirect("admin/edit_book.jsp");
            return;
        }

        // Update book in database
        ProductDAO productDAO = new ProductDAO();
        try {
            productDAO.updateProduct(product);
            session.setAttribute("succMsg", "Product updated successfully!");
        } catch (SQLException e) {
            session.setAttribute("errMsg", "Update failed! Database error.\n" + e.getMessage());
        }

        response.sendRedirect("admin/all_books.jsp");
    }

    private String validate(Product product) {
        if (product.getProductName() == null || product.getProductName().trim().isEmpty()) {
            return "Book name cannot be empty!";
        }
        if (product.getBrand() == null || product.getBrand().trim().isEmpty()) {
            return "Brand cannot be empty!";
        }
        if (product.getPrice() <= 0) {
            return "Price must be a NUMBER greater than 0!";
        }
        return null;
    }

    @Override
    public String getServletInfo() {
        return "Servlet for editing products in admin panel.";
    }
}
