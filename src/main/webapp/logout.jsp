<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    session.invalidate();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Logged Out - Paperless World</title>

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">

    <style>
        body {
            background-color: #f8f9fa;
            height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .logout-container {
            max-width: 400px;
            width: 90%;
            text-align: center;
            padding: 2rem;
        }
        .card {
            border: none;
            border-radius: 10px;
            box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.15);
        }
        .card-body {
            padding: 2rem;
        }
        .icon-logout {
            font-size: 3rem;
            color: #28a745;
            margin-bottom: 1rem;
        }
        .btn-login {
            padding: 0.5rem 2rem;
            font-weight: 500;
        }
        .card-title {
            color: #2c3e50;
            font-weight: 600;
            margin-bottom: 1.5rem;
        }
        .text-muted {
            margin-bottom: 1.5rem;
        }
    </style>
</head>
<body>
<div class="logout-container">
    <div class="card">
        <div class="card-body">
            <i class="fas fa-check-circle icon-logout"></i>
            <h2 class="card-title">Logged Out Successfully</h2>
            <a href="login.jsp" class="btn btn-primary btn-login">
                <i class="fas fa-sign-in-alt mr-2"></i>Login Again
            </a>
        </div>
    </div>
</div>

<!-- Bootstrap JS and dependencies -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
