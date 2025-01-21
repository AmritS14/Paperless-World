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
    
        <%-- Handle error from request attribute --%>
        <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <%= request.getAttribute("error") %>
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        <% } %>

        <%-- Handle error from URL parameter --%>
        <% if (request.getParameter("error") != null) { %>
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <%= request.getParameter("error") %>
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        <% } %>

        <%-- Handle success message --%>
        <% if (request.getParameter("message") != null) { %>
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                <%= request.getParameter("message") %>
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        <% } %>
        <div class="container mt-4"></div>
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2>My Documents</h2>
        </div>
        <table class="table table-hover">
            <thead class="thead-light">
                <tr>
                    <th scope="col" style="width: 30%">Document Name</th>
                    <th scope="col" style="width: 15%">Date Modified</th>
                    <th scope="col" style="width: 10%">Size</th>
                    <th scope="col" style="width: 10%">Type</th>
                    <th scope="col" style="width: 35%">Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="doc" items="${documentList}">
                    <tr>
                        <td><c:out value="${doc.title}"/></td>
                        <td><c:out value="${doc.dateModified}"/></td>
                        <td><c:out value="${doc.size}"/></td>
                        <td><c:out value="${doc.type}"/></td>
                        <td>
                            <div class="btn-group" role="group">
                                <a href="viewDocument?id=<c:out value='${doc.id}'/>" class="btn btn-sm btn-outline-primary">View</a>
                                <a href="downloadDocument?id=<c:out value='${doc.id}'/>" class="btn btn-sm btn-outline-secondary">Download</a>
                                <a href="#" onclick="openShareModal(<c:out value='${doc.id}'/>)" class="btn btn-sm btn-outline-success">Share</a>
                                <a href="removeDocument?id=<c:out value='${doc.id}'/>" class="btn btn-sm btn-outline-danger">Remove</a>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <div class="text-right mb-5">
            <a href="addDocument.jsp" class="btn btn-primary">Add New Document</a>
        </div>

    <h2 class="mb-4">Shared Documents</h2>
    <table class="table table-hover">
        <thead class="thead-light">
            <tr>
                <th scope="col" style="width: 30%">Document Name</th>
                <th scope="col" style="width: 15%">Date Modified</th>
                <th scope="col" style="width: 10%">Size</th>
                <th scope="col" style="width: 10%">Author</th>
                <th scope="col" style="width: 35%">Actions</th>
            </tr>
        </thead>
        <tbody>
        <c:forEach var="doc" items="${sharedDocumentList}">
            <tr>
                <td><c:out value="${doc.title}"/></td>
                <td><c:out value="${doc.dateModified}"/></td>
                <td><c:out value="${doc.size}"/></td>
                <td><c:out value="${doc.author}"/></td>
                <td>
                    <div class="btn-group" role="group">
                        <a href="viewDocument?id=<c:out value='${doc.id}'/>" class="btn btn-sm btn-outline-primary">View</a>
                        <a href="downloadDocument?id=<c:out value='${doc.id}'/>" class="btn btn-sm btn-outline-secondary">Download</a>
                        <a href="removeSharedDocument?id=<c:out value='${doc.id}'/>" class="btn btn-sm btn-outline-danger">Remove</a>
                    </div>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<!-- Share Document Modal -->
<div class="modal fade" id="shareModal" tabindex="-1" role="dialog" aria-labelledby="shareModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="shareModalLabel">Share Document</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <form id="shareForm" action="shareDocument" method="post">
                <div class="modal-body">
                    <input type="hidden" id="documentId" name="documentId">
                    <div class="form-group">
                        <label for="shareWithUser">Share with User:</label>
                        <input type="text" class="form-control" id="shareWithUser" name="shareWithUser" 
                               placeholder="Enter username" required>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                    <button type="submit" class="btn btn-success">Share</button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- Add this JavaScript before closing body tag -->
<script>
    function openShareModal(documentId) {
        document.getElementById('documentId').value = documentId;
        $('#shareModal').modal('show');
    }
</script>
</body>

<%
    }
    else {
%>
        <script>
            // alert("Please login first!");
            window.location.href = "login.jsp";
        </script>
<%
    }
%>
</html>
