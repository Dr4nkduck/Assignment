/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package admin.servelet;

import DAO.ProductDAO;
import entity.Product;
import entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;

@WebServlet("/add_book")
@MultipartConfig
public class BooksAddServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();

        // Getting parameters from the form
        String productName = request.getParameter("pname");
        String brand = request.getParameter("brand");
        String priceStr = request.getParameter("price");
        String productCategory = request.getParameter("ptype"); // Book category dropdown
        String status = request.getParameter("pstatus"); // Book status dropdown
        Part part = request.getPart("pimg"); // Uploaded file

        User user = (User) session.getAttribute("userobj");
        if (user == null) {
            session.setAttribute("failedMsg", "You must be logged in to add a product!");
            response.sendRedirect("login.jsp");
            return;
        }

        String userEmail = user.getEmail();

        // Convert price to double
        double price = 0.0;
        if (priceStr != null && !priceStr.isEmpty()) {
            try {
                price = Double.parseDouble(priceStr);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        if (part != null) {
            try {
                String fileName = part.getSubmittedFileName(); // Get uploaded file name
                String uploadPath = getServletContext().getRealPath("") + File.separator + "product";
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }

                String filePath = uploadPath + File.separator + fileName;
                part.write(filePath);
                System.out.println("File saved at: " + filePath);
            } catch (Exception e) {
                session.setAttribute("errMsg", "Add Product Failed! There is no Image Provided!");
                response.sendRedirect("admin/add_books.jsp");
                return;
            }
        } else {
            session.setAttribute("errMsg", "Add Product Failed! The image is null");
            response.sendRedirect("admin/add_books.jsp");
            return;
        }
        // Getting the file name
        String fileName = part.getSubmittedFileName();

        Product product = new Product(productName, brand, price, productCategory, status, fileName, userEmail);

        String path = getServletContext().getRealPath("");

        String validationError = validate(product);
        if (validationError != null) {
            session.setAttribute("errMsg", validationError);
            response.sendRedirect("admin/add_books.jsp");
            return;
        }

        ProductDAO productDAO = new ProductDAO();
        String msg = productDAO.insertProduct(product);

        if (msg.isBlank()) {
            session.setAttribute("succMsg", "Added product Successfully!");
            response.sendRedirect("admin/add_books.jsp");
        } else {
            String errMsg = "Add product Failed! Insert product to Database Failed" + msg;
            session.setAttribute("errMsg", errMsg);
            response.sendRedirect("admin/add_books.jsp");
        }

    }

    private String validate(Product product) {
        if (product.getProductName() == null || product.getProductName().trim().isEmpty()) {
            return "Book name cannot be empty!";
        }
        if (product.getBrand() == null || product.getBrand().trim().isEmpty()) {
            return "Author cannot be empty!";
        }
        if (product.getPrice() <= 0) {
            return "Price must be a number greater than 0!";
        }
        return null; // No errors
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Used for adding product in admin panel.";
    }// </editor-fold>

}
