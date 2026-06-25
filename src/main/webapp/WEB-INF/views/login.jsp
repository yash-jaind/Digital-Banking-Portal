<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Digital Banking Portal - Login</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="auth-container">
    <div class="auth-box">
        <div class="bank-logo">
            <h1>🏦 Digital Banking Portal</h1>
            <p>Secure. Simple. Smart.</p>
        </div>

        <% if (request.getParameter("registered") != null) { %>
            <div class="alert success">Registration successful! Please login.</div>
        <% } %>

        <% if (request.getAttribute("error") != null) { %>
            <div class="alert error">${error}</div>
        <% } %>

        <form method="post" action="${pageContext.request.contextPath}/login">
            <div class="form-group">
                <label>Username</label>
                <input type="text" name="username" placeholder="Enter username" required>
            </div>
            <div class="form-group">
                <label>Password</label>
                <input type="password" name="password" placeholder="Enter password" required>
            </div>
            <button type="submit" class="btn btn-primary">Login</button>
        </form>

        <p class="auth-link">Don't have an account? <a href="${pageContext.request.contextPath}/register">Register here</a></p>
    </div>
</div>
</body>
</html>
