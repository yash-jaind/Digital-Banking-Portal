<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Digital Banking Portal - Register</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="auth-container">
    <div class="auth-box">
        <div class="bank-logo">
            <h1>🏦 Digital Banking Portal</h1>
            <p>Create Your Account</p>
        </div>

        <% if (request.getAttribute("error") != null) { %>
            <div class="alert error">${error}</div>
        <% } %>

        <form method="post" action="${pageContext.request.contextPath}/register">
            <div class="form-group">
                <label>Full Name</label>
                <input type="text" name="fullName" placeholder="Enter full name" required>
            </div>
            <div class="form-group">
                <label>Username</label>
                <input type="text" name="username" placeholder="Choose a username" required>
            </div>
            <div class="form-group">
                <label>Email</label>
                <input type="email" name="email" placeholder="Enter email" required>
            </div>
            <div class="form-group">
                <label>Phone</label>
                <input type="text" name="phone" placeholder="Enter phone number" required>
            </div>
            <div class="form-group">
                <label>Password</label>
                <input type="password" name="password" placeholder="Choose a password" required>
            </div>
            <button type="submit" class="btn btn-primary">Register</button>
        </form>

        <p class="auth-link">Already have an account? <a href="${pageContext.request.contextPath}/login">Login here</a></p>
    </div>
</div>
</body>
</html>
