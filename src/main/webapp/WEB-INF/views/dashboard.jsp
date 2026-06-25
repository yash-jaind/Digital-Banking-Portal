<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.banking.model.Transaction, java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard - Digital Banking Portal</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%-- Navbar --%>
<nav class="navbar">
    <div class="nav-brand">🏦 Digital Banking Portal</div>
    <div class="nav-links">
        <span>Welcome, ${sessionScope.loggedUser.fullName}</span>
        <a href="${pageContext.request.contextPath}/transfer">Fund Transfer</a>
        <a href="${pageContext.request.contextPath}/beneficiary">Beneficiaries</a>
        <a href="${pageContext.request.contextPath}/logout">Logout</a>
    </div>
</nav>

<div class="container">

    <% if (request.getParameter("transferSuccess") != null) { %>
        <div class="alert success">Fund transfer successful!</div>
    <% } %>

    <%-- Account Summary Card --%>
    <div class="card account-card">
        <h2>Account Summary</h2>
        <div class="account-info">
            <div class="info-item">
                <span class="label">Account Number</span>
                <span class="value">${account.accountNumber}</span>
            </div>
            <div class="info-item">
                <span class="label">Account Type</span>
                <span class="value">${account.accountType}</span>
            </div>
            <div class="info-item">
                <span class="label">Available Balance</span>
                <span class="value balance">₹ ${String.format("%.2f", account.balance)}</span>
            </div>
            <div class="info-item">
                <span class="label">Status</span>
                <span class="value status-active">${account.status}</span>
            </div>
        </div>
    </div>

    <%-- Recent Transactions --%>
    <div class="card">
        <h2>Recent Transactions</h2>
        <table class="txn-table">
            <thead>
                <tr>
                    <th>Date</th>
                    <th>Type</th>
                    <th>From</th>
                    <th>To</th>
                    <th>Amount</th>
                    <th>Description</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<Transaction> transactions = (List<Transaction>) request.getAttribute("transactions");
                    if (transactions != null && !transactions.isEmpty()) {
                        for (Transaction txn : transactions) {
                %>
                <tr>
                    <td><%=txn.getTransactionDate()%></td>
                    <td><span class="badge <%=txn.getTransactionType().toLowerCase()%>"><%=txn.getTransactionType()%></span></td>
                    <td><%=txn.getFromAccount() != null ? txn.getFromAccount() : "-"%></td>
                    <td><%=txn.getToAccount() != null ? txn.getToAccount() : "-"%></td>
                    <td>₹ <%=String.format("%.2f", txn.getAmount())%></td>
                    <td><%=txn.getDescription()%></td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr><td colspan="6" style="text-align:center;">No transactions found.</td></tr>
                <% } %>
            </tbody>
        </table>
    </div>

</div>
</body>
</html>
