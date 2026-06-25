<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Fund Transfer - Digital Banking Portal</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<nav class="navbar">
    <div class="nav-brand">🏦 Digital Banking Portal</div>
    <div class="nav-links">
        <a href="${pageContext.request.contextPath}/dashboard">Dashboard</a>
        <a href="${pageContext.request.contextPath}/beneficiary">Beneficiaries</a>
        <a href="${pageContext.request.contextPath}/logout">Logout</a>
    </div>
</nav>

<div class="container">
    <div class="card form-card">
        <h2>Fund Transfer</h2>
        <p class="sub">Current Balance: <strong>₹ ${String.format("%.2f", account.balance)}</strong> | From: <strong>${account.accountNumber}</strong></p>

        <% if (request.getAttribute("error") != null) { %>
            <div class="alert error">${error}</div>
        <% } %>

        <form method="post" action="${pageContext.request.contextPath}/transfer">
            <div class="form-group">
                <label>Recipient Account Number</label>
                <input type="text" name="toAccount" placeholder="Enter recipient account number" required>
            </div>
            <div class="form-group">
                <label>Amount (₹)</label>
                <input type="number" name="amount" placeholder="Enter amount" min="1" step="0.01" required>
            </div>
            <div class="form-group">
                <label>Description (optional)</label>
                <input type="text" name="description" placeholder="e.g. Rent payment">
            </div>
            <button type="submit" class="btn btn-primary">Transfer Funds</button>
            <a href="${pageContext.request.contextPath}/dashboard" class="btn btn-secondary">Cancel</a>
        </form>
    </div>
</div>
</body>
</html>
