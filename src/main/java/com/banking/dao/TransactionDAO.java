package com.banking.dao;

import com.banking.model.Account;
import com.banking.model.Transaction;
import com.banking.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

    private AccountDAO accountDAO = new AccountDAO();

    // Existing method (leave it as it is)
    public boolean addTransaction(Transaction txn) {
        String sql = "INSERT INTO transactions (from_account, to_account, transaction_type, amount, description) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, txn.getFromAccount());
            ps.setString(2, txn.getToAccount());
            ps.setString(3, txn.getTransactionType());
            ps.setDouble(4, txn.getAmount());
            ps.setString(5, txn.getDescription());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // New overloaded method used inside a transaction
    public boolean addTransaction(Connection conn, Transaction txn) {
        String sql = "INSERT INTO transactions (from_account, to_account, transaction_type, amount, description) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, txn.getFromAccount());
            ps.setString(2, txn.getToAccount());
            ps.setString(3, txn.getTransactionType());
            ps.setDouble(4, txn.getAmount());
            ps.setString(5, txn.getDescription());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // New transaction-safe transfer method
    public boolean transferFunds(Account senderAccount,
                                 Account receiverAccount,
                                 double amount,
                                 String description) {

        Connection conn = null;

        try {

            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            boolean senderUpdated = accountDAO.updateBalance(
                    conn,
                    senderAccount.getAccountNumber(),
                    senderAccount.getBalance() - amount
            );

            boolean receiverUpdated = accountDAO.updateBalance(
                    conn,
                    receiverAccount.getAccountNumber(),
                    receiverAccount.getBalance() + amount
            );

            if (!senderUpdated || !receiverUpdated) {
                conn.rollback();
                return false;
            }

            Transaction txn = new Transaction();
            txn.setFromAccount(senderAccount.getAccountNumber());
            txn.setToAccount(receiverAccount.getAccountNumber());
            txn.setTransactionType("TRANSFER");
            txn.setAmount(amount);
            txn.setDescription(
                    description != null && !description.isEmpty()
                            ? description
                            : "Fund Transfer"
            );

            if (!addTransaction(conn, txn)) {
                conn.rollback();
                return false;
            }

            conn.commit();
            return true;

        } catch (SQLException e) {

            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            e.printStackTrace();

        } finally {

            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        return false;
    }

    public List<Transaction> getTransactionsByAccount(String accountNumber) {

        List<Transaction> list = new ArrayList<>();

        String sql = "SELECT * FROM transactions WHERE from_account = ? OR to_account = ? ORDER BY transaction_date DESC LIMIT 10";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, accountNumber);
            ps.setString(2, accountNumber);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Transaction txn = new Transaction();

                txn.setTransactionId(rs.getInt("transaction_id"));
                txn.setFromAccount(rs.getString("from_account"));
                txn.setToAccount(rs.getString("to_account"));
                txn.setTransactionType(rs.getString("transaction_type"));
                txn.setAmount(rs.getDouble("amount"));
                txn.setDescription(rs.getString("description"));
                txn.setTransactionDate(rs.getTimestamp("transaction_date"));

                list.add(txn);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}