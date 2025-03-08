package DAO;

import entity.CartItem;
import java.sql.*;
import java.util.*;

public class CartDAO extends MyDAO {

    public boolean addToCart(int userId, int bookId, int quantity) throws SQLException {
        xSql = "INSERT INTO Cart (userId, bookId, quantity) VALUES (?, ?, ?)";
        try {
            ps = con.prepareStatement(xSql);
            ps.setInt(1, userId);
            ps.setInt(2, bookId);
            ps.setInt(3, quantity);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw e;
        }
    }

    public boolean removeFromCart(int userId, int bookId) throws SQLException {
        xSql = "DELETE FROM Cart WHERE userId = ? AND bookId = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setInt(1, userId);
            ps.setInt(2, bookId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw e;
        }
    }

    public List<CartItem> getCartItems(int userId) throws SQLException {
        List<CartItem> cartItems = new ArrayList<>();
        xSql = "SELECT b.bookId, b.bookname, b.author, b.price, c.quantity "
                + "FROM Cart c JOIN Book b ON c.bookId = b.bookId WHERE c.userId = ?";

        try {
            ps = con.prepareStatement(xSql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            while (rs.next()) {
                CartItem item = new CartItem();
                item.setBookId(rs.getInt("bookId"));
                item.setBookname(rs.getString("bookname"));
                item.setAuthor(rs.getString("author"));
                item.setPrice(rs.getBigDecimal("price").doubleValue());
                item.setQuantity(rs.getInt("quantity"));
                item.setUserId(userId);
                cartItems.add(item);
            }
        } catch (SQLException e) {
            throw e;
        }
        return cartItems;
    }

    public void updateCartItem(int userId, int bookId, int quantity) throws SQLException {
        String sql = "UPDATE Cart SET quantity = ? WHERE userId = ? AND bookId = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, quantity);
            ps.setInt(2, userId);
            ps.setInt(3, bookId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }

    public double getItemTotal(int userId, int bookId) throws SQLException {
        String sql = "SELECT quantity, price FROM Cart INNER JOIN Book ON Cart.bookId = Book.bookId WHERE Cart.userId = ? AND Cart.bookId = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, bookId);
            rs = ps.executeQuery();
            if (rs.next()) {
                int quantity = rs.getInt("quantity");
                double price = rs.getDouble("price");
                return quantity * price;
            }
        } catch (SQLException e) {
            throw e;
        }
        return 0;
    }

    public double getCartTotal(int userId) throws SQLException {
        String sql = "SELECT SUM(Cart.quantity * Book.price) AS total FROM Cart INNER JOIN Book ON Cart.bookId = Book.bookId WHERE Cart.userId = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble("total");
            }
        } catch (SQLException e) {
            throw e;
        }
        return 0;
    }

}
