<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Document Details</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-5">
        <div class="card">
            <div class="card-header">
                <h3><c:out value="${document.title}"/></h3>
            </div>
            <div class="card-body">
                <div class="row">
                    <div class="col-md-8">
                        <h4>Document details: </h4>
                        <p><strong>Author:</strong> <c:out value="${document.author}"/></p>
                        <p><strong>File Name:</strong> <c:out value="${document.fileName}"/></p>
                        <p><strong>Size:</strong> <c:out value="${document.size}"/></p>
                        <p><strong>Type:</strong> <c:out value="${document.type}"/></p>
                        <p><strong>Created:</strong> <c:out value="${document.dateCreated}"/></p>
                        <p><strong>Modified:</strong> <c:out value="${document.dateModified}"/></p>
                    </div>
                </div>
                <div class="mt-3">
                    <a href="downloadDocument?id=<c:out value='${document.id}'/>" class="btn btn-primary">Download</a>
                    <a href="index.jsp" class="btn btn-secondary">Back to List</a>
                </div>
            </div>
        </div>
    </div>
</body>
</html> 