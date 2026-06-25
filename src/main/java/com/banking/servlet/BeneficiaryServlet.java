package com.banking.servlet;

import com.banking.dao.BeneficiaryDAO;
import com.banking.model.Beneficiary;
import com.banking.model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.List;

@WebServlet("/beneficiary")
public class BeneficiaryServlet extends HttpServlet {

    private BeneficiaryDAO beneficiaryDAO = new BeneficiaryDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("loggedUser") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("loggedUser");
        String action = req.getParameter("action");

        if ("delete".equals(action)) {
            int beneficiaryId = Integer.parseInt(req.getParameter("id"));
            beneficiaryDAO.deleteBeneficiary(beneficiaryId, user.getUserId());
            resp.sendRedirect(req.getContextPath() + "/beneficiary");
            return;
        }

        List<Beneficiary> beneficiaries = beneficiaryDAO.getBeneficiariesByUser(user.getUserId());
        req.setAttribute("beneficiaries", beneficiaries);
        req.getRequestDispatcher("/WEB-INF/views/beneficiary.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("loggedUser") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("loggedUser");

        Beneficiary b = new Beneficiary();
        b.setUserId(user.getUserId());
        b.setBeneficiaryName(req.getParameter("beneficiaryName"));
        b.setAccountNumber(req.getParameter("accountNumber"));
        b.setBankName(req.getParameter("bankName"));

        beneficiaryDAO.addBeneficiary(b);
        resp.sendRedirect(req.getContextPath() + "/beneficiary?added=true");
    }
}
