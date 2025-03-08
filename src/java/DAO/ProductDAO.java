package DAO;

import entity.Product;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO extends MyDAO {

    public String insertProduct(Product product) {
        xSql = "INSERT INTO product (productname, brand, price, productCategory, status, photo, user_email) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, product.getProductName());
            ps.setString(2, product.getBrand());
            ps.setDouble(3, product.getPrice());
            ps.setString(4, product.getProductCategory());
            ps.setString(5, product.getStatus());
            ps.setString(6, product.getPhoto());
            ps.setString(7, product.getUserEmail());

            int rowsAffected = ps.executeUpdate();
            return ""; // Returns empty if insertion is successful
        } catch (SQLException e) {
            return e.getMessage();

        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                return e.getMessage();

            }
        }
    }

    public List<Product> getAllproducts() {
        List<Product> products = new ArrayList<>();
        xSql = "SELECT * FROM product";

        try {
            ps = con.prepareStatement(xSql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("productId"),
                        rs.getString("productName"),
                        rs.getString("brand"),
                        rs.getDouble("price"),
                        rs.getString("productCategory"),
                        rs.getString("status"),
                        rs.getString("photo"),
                        rs.getString("user_email")
                );
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return products;
    }

    public Product getproductById(int id) throws Exception {
        Product product = null;
        xSql = "SELECT * FROM product WHERE productId = ?";

        try {
            ps = con.prepareStatement(xSql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                product = new Product(
                        rs.getInt("productId"),
                        rs.getString("productName"),
                        rs.getString("brand"),
                        rs.getDouble("price"),
                        rs.getString("productCategory"),
                        rs.getString("status"),
                        rs.getString("photo"),
                        rs.getString("user_email")
                );
            }
        } catch (SQLException e) {
            throw e;
        }
        return product;

    }

    public boolean updateProduct(Product product) throws SQLException {
        xSql = "UPDATE product SET productname=?, brand=?, price=?, productCategory=?, status=?, photo=? WHERE productId=?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, product.getProductName());
            ps.setString(2, product.getBrand());
            ps.setDouble(3, product.getPrice());
            ps.setString(4, product.getProductCategory());
            ps.setString(5, product.getStatus());
            ps.setString(6, product.getPhoto());
            ps.setInt(7, product.getProductId());

            return ps.executeUpdate() > 0; // Returns true if update successful
        } catch (SQLException e) {
            throw e;
        }
    }

    public boolean deleteProduct(int productId) throws SQLException {
        xSql = "DELETE FROM product WHERE productId = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setInt(1, productId);
            return ps.executeUpdate() > 0; 
        } catch (SQLException e) {
            throw e;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                throw e;
            }
        }
    }

}
