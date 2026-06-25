package com.banking.servlet;

import com.banking.dao.AccountDAO;
import com.banking.dao.TransactionDAO;
import com.banking.model.Account;
import com.banking.model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet("/transfer")
public class FundTransferServlet extends HttpServlet {

    private AccountDAO accountDAO = new AccountDAO();
    private TransactionDAO transactionDAO = new TransactionDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("loggedUser") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("loggedUser");
        Account account = accountDAO.getAccountByUserId(user.getUserId());

        req.setAttribute("account", account);
        req.getRequestDispatcher("/WEB-INF/views/transfer.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("loggedUser") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("loggedUser");

        Account senderAccount = accountDAO.getAccountByUserId(user.getUserId());

        String toAccountNumber = req.getParameter("toAccount");
        double amount = Double.parseDouble(req.getParameter("amount"));
        String description = req.getParameter("description");

        Account receiverAccount = accountDAO.getAccountByNumber(toAccountNumber);
        if (senderAccount.getAccountNumber().equals(receiverAccount.getAccountNumber())) {
    req.setAttribute("error", "You cannot transfer money to your own account.");
    req.setAttribute("account", senderAccount);
    req.getRequestDispatcher("/WEB-INF/views/transfer.jsp").forward(req, resp);
    return;
}

        if (receiverAccount == null) {
            req.setAttribute("error", "Recipient account not found.");
            req.setAttribute("account", senderAccount);
            req.getRequestDispatcher("/WEB-INF/views/transfer.jsp").forward(req, resp);
            return;
        }

        if (senderAccount.getBalance() < amount) {
            req.setAttribute("error", "Insufficient balance.");
            req.setAttribute("account", senderAccount);
            req.getRequestDispatcher("/WEB-INF/views/transfer.jsp").forward(req, resp);
            return;
        }

        // Perform transfer using a single database transaction
        boolean success = transactionDAO.transferFunds(
                senderAccount,
                receiverAccount,
                amount,
                description
        );

        if (!success) {
            req.setAttribute("error", "Transfer failed. Please try again.");
            req.setAttribute("account", senderAccount);
            req.getRequestDispatcher("/WEB-INF/views/transfer.jsp").forward(req, resp);
            return;
        }

        resp.sendRedirect(req.getContextPath() + "/dashboard?transferSuccess=true");
    }
}