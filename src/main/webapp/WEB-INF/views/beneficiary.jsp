<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.banking.model.Beneficiary, java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>Beneficiaries - Digital Banking Portal</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<nav class="navbar">
    <div class="nav-brand">🏦 Digital Banking Portal</div>
    <div class="nav-links">
        <a href="${pageContext.request.contextPath}/dashboard">Dashboard</a>
        <a href="${pageContext.request.contextPath}/transfer">Fund Transfer</a>
        <a href="${pageContext.request.contextPath}/logout">Logout</a>
    </div>
</nav>

<div class="container">

    <% if (request.getParameter("added") != null) { %>
        <div class="alert success">Beneficiary added successfully!</div>
    <% } %>

    <%-- Add Beneficiary Form --%>
    <div class="card form-card">
        <h2>Add Beneficiary</h2>
        <form method="post" action="${pageContext.request.contextPath}/beneficiary">
            <div class="form-row">
                <div class="form-group">
                    <label>Beneficiary Name</label>
                    <input type="text" name="beneficiaryName" placeholder="Full name" required>
                </div>
                <div class="form-group">
                    <label>Account Number</label>
                    <input type="text" name="accountNumber" placeholder="Account number" required>
                </div>
                <div class="form-group">
                    <label>Bank Name</label>
                    <input type="text" name="bankName" placeholder="Bank name" required>
                </div>
            </div>
            <button type="submit" class="btn btn-primary">Add Beneficiary</button>
        </form>
    </div>

    <%-- Beneficiary List --%>
    <div class="card">
        <h2>My Beneficiaries</h2>
        <table class="txn-table">
            <thead>
                <tr>
                    <th>#</th>
                    <th>Name</th>
                    <th>Account Number</th>
                    <th>Bank</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<Beneficiary> beneficiaries = (List<Beneficiary>) request.getAttribute("beneficiaries");
                    if (beneficiaries != null && !beneficiaries.isEmpty()) {
                        int i = 1;
                        for (Beneficiary b : beneficiaries) {
                %>
                <tr>
                    <td><%=i++%></td>
                    <td><%=b.getBeneficiaryName()%></td>
                    <td><%=b.getAccountNumber()%></td>
                    <td><%=b.getBankName()%></td>
                    <td>
                        <a href="${pageContext.request.contextPath}/beneficiary?action=delete&id=<%=b.getBeneficiaryId()%>"
                           class="btn btn-danger btn-sm"
                           onclick="return confirm('Delete this beneficiary?')">Delete</a>
                    </td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr><td colspan="5" style="text-align:center;">No beneficiaries added yet.</td></tr>
                <% } %>
            </tbody>
        </table>
    </div>

</div>
</body>
</html>
