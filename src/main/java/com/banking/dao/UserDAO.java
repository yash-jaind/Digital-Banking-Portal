package com.banking.dao;

import com.banking.model.User;
import com.banking.util.DBConnection;

import java.sql.*;

public class UserDAO {

    public User authenticate(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setFullName(rs.getString("full_name"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                return user;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // NEW METHOD
    public boolean usernameExists(String username) {

        String sql = "SELECT user_id FROM users WHERE username = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean registerUser(User user) {

        // Check duplicate username
        if (usernameExists(user.getUsername())) {
            return false;
        }

        String sql = "INSERT INTO users (username, password, full_name, email, phone) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFullName());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getPhone());

            int rows = ps.executeUpdate();

            if (rows > 0) {

                ResultSet keys = ps.getGeneratedKeys();

                if (keys.next()) {
                    int userId = keys.getInt(1);
                    createDefaultAccount(conn, userId);
                }

                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    private void createDefaultAccount(Connection conn, int userId) throws SQLException {

        String accNumber = "ACC" + System.currentTimeMillis();

        String sql = "INSERT INTO accounts (user_id, account_number, account_type, balance) VALUES (?, ?, 'SAVINGS', 0.00)";

        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setInt(1, userId);
        ps.setString(2, accNumber);

        ps.executeUpdate();
    }
}