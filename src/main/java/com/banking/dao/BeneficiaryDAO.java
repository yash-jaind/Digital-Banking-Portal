package com.banking.dao;

import com.banking.model.Beneficiary;
import com.banking.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BeneficiaryDAO {

    public boolean addBeneficiary(Beneficiary b) {
        String sql = "INSERT INTO beneficiaries (user_id, beneficiary_name, account_number, bank_name) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, b.getUserId());
            ps.setString(2, b.getBeneficiaryName());
            ps.setString(3, b.getAccountNumber());
            ps.setString(4, b.getBankName());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Beneficiary> getBeneficiariesByUser(int userId) {
        List<Beneficiary> list = new ArrayList<>();
        String sql = "SELECT * FROM beneficiaries WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Beneficiary b = new Beneficiary();
                b.setBeneficiaryId(rs.getInt("beneficiary_id"));
                b.setUserId(rs.getInt("user_id"));
                b.setBeneficiaryName(rs.getString("beneficiary_name"));
                b.setAccountNumber(rs.getString("account_number"));
                b.setBankName(rs.getString("bank_name"));
                list.add(b);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean deleteBeneficiary(int beneficiaryId, int userId) {
        String sql = "DELETE FROM beneficiaries WHERE beneficiary_id = ? AND user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, beneficiaryId);
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
