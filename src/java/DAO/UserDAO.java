package DAO;

import entity.User;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Base64;

public class UserDAO extends MyDAO {

    // Hash password using SHA-256
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    // User Login
    public User login(String email, String password) throws SQLException {
        User user = null;
        String hashedPassword = hashPassword(password);

        xSql = "SELECT * FROM [User] WHERE email = ? AND password = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, email);
            ps.setString(2, hashedPassword);
            rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setPhone(rs.getString("phone"));
            }
        } catch (SQLException e) {
            throw e;
        }
        return user;
    }

    // User Register
    public boolean register(String name, String email, String password, String phone) throws SQLException {
        String hashedPassword = hashPassword(password); // Hash password before storing

        xSql = "INSERT INTO [User] (name, email, password, phone) VALUES (?, ?, ?, ?)";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, hashedPassword);
            ps.setString(4, phone);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw e;
        }
    }
}
