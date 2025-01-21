<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Add New Document - Paperless World</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        .form-group label {
            font-weight: 500;
            color: #495057;
        }
        .card {
            box-shadow: 0 0.125rem 0.25rem rgba(0, 0, 0, 0.075);
        }
        .card-header {
            background-color: #f8f9fa;
            border-bottom: 1px solid #e9ecef;
        }
        .btn-primary {
            background-color: #007bff;
            border-color: #007bff;
        }
        .btn-primary:hover {
            background-color: #0069d9;
            border-color: #0062cc;
        }
        .form-control-file {
            padding: 4px 0;
        }
        .text-muted {
            font-size: 0.875rem;
        }
    </style>
</head>
<body>
<%
    if (session.getAttribute("user") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<%
    request.setAttribute("page", "addDocument");
%>
<%@ include file="navbar.jsp" %>

<div class="container mt-5">
    <div class="card">
        <div class="card-header">
            <h3>Add New Document</h3>
        </div>
        <div class="card-body">
            <% if (request.getAttribute("error") != null) { %>
                <div class="alert alert-danger" role="alert">
                    <%= request.getAttribute("error") %>
                </div>
            <% } %>
            
            <form action="uploadDocument" method="post" enctype="multipart/form-data">
                <div class="row">
                    <div class="col-md-8">
                        <div class="form-group">
                            <label for="title">Document Title</label>
                            <input type="text" class="form-control" id="title" name="title" required
                                   placeholder="Enter document title">
                        </div>
                        
                        <div class="form-group">
                            <label for="type">Document Type</label>
                            <select class="form-control" id="type" name="type" required>
                                <option value="">Select document type...</option>
                                <option value="INVOICE">Invoice</option>
                                <option value="CONTRACT">Contract</option>
                                <option value="REPORT">Report</option>
                                <option value="OTHER">Other</option>
                            </select>
                        </div>
                        
                        <div class="form-group">
                            <label for="documentFile">Upload File</label>
                            <div class="custom-file">
                                <input type="file" class="custom-file-input" id="documentFile" name="documentFile" required>
                                <label class="custom-file-label" for="documentFile">Choose file</label>
                            </div>
                            <small class="form-text text-muted mt-2">
                                <i class="fas fa-info-circle"></i>
                                Supported formats: PDF, DOC, DOCX, TXT (Max size: 10MB)
                            </small>
                        </div>
                    </div>
                </div>
                
                <div class="mt-4">
                    <button type="submit" class="btn btn-primary">
                        <i class="fas fa-upload"></i> Upload Document
                    </button>
                    <a href="index.jsp" class="btn btn-secondary">
                        <i class="fas fa-arrow-left"></i> Back to List
                    </a>
                </div>
            </form>
        </div>
    </div>
</div>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">

<!-- Add jQuery and Bootstrap JS for custom file input -->
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
<script>
    $(document).ready(function() {
        $('.custom-file-input').on('change', function() {
            let fileName = $(this).val().split('\\').pop();
            $(this).next('.custom-file-label').html(fileName);
        });
    });
</script>

</body>
</html> 