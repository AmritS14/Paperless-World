<%@ page import="com.document.model.Document" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<%
    if (session.getAttribute("user") != null) {
        if(request.getAttribute("documentList") == null) {
            response.sendRedirect("documents");
            return;
        }
%>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link rel="stylesheet" href="styles.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>

    <title>Paperless World</title>
</head>
<body>

<%
    request.setAttribute("page", "index");
%>
<%@ include file="navbar.jsp" %>

<div class="content">
    <div class="container mt-4"></div>
        <h2 class="mb-4">My Documents</h2>
        <table class="table table-hover">
            <thead class="thead-light">
                <tr>
                    <th scope="col">Document Name</th>
                    <th scope="col">Date Modified</th>
                    <th scope="col">Size</th>
                    <th scope="col">Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="doc" items="${documentList}">
                    <tr>
                        <td><c:out value="${doc.title}"/></td>
                        <td><c:out value="${doc.dateModified}"/></td>
                        <td><c:out value="${doc.size}"/></td>
                        <td>
                            <a href="viewDocument?id=<c:out value='${doc.id}'/>" class="btn btn-sm btn-outline-primary mr-2">View</a>
                            <a href="downloadDocument?id=<c:out value='${doc.id}'/>" class="btn btn-sm btn-outline-secondary">Download</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
</div>
</body>

<%
    }
    else {
%>
        <script>
            alert("Please login first!");
            window.location.href = "login.jsp";
        </script>
<%
    }
%>
</html>
