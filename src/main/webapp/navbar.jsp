<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
    <div class="container">
        <a class="navbar-brand" <% if (session.getAttribute("user") != null) {%>href="index.jsp"<%} else {%> href="#" onclick="login()"<%}%>>Paperless World</a>
        <script>
            function login(){
                alert("Please login first!");
                window.location.href = "login.jsp";
            }
        </script>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ml-auto">
                <li class="nav-item">
                    <a class="nav-link <%= "login".equals(request.getAttribute("page")) ? "active" : "" %>" href="login.jsp">Login</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link <%= "register".equals(request.getAttribute("page")) ? "active" : "" %>" href="register.jsp">Register</a>
                </li>
                <% if(session.getAttribute("user") != null) { %>
                <li class="nav-item">
                    <span class="nav-link">Welcome, <%= session.getAttribute("user") %></span>
                </li>
                <li class="nav-item">
                    <a href="logout.jsp" class="nav-link">Logout</a>
                </li>
                <% } %>
            </ul>
        </div>
    </div>
</nav>

<style>
    .navbar .nav-link.active {
        font-weight: bold;
        color: #ffffff !important;
        background-color: #007bff;
        border-radius: 5px;
    }
</style>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"></script>
