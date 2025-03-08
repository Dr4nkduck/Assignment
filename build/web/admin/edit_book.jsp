<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.io.*, java.util.*" %>
<%@page import="javax.servlet.*, javax.servlet.http.*" %>
<%@page import="DAO.BookDAO" %>
<%@page import="entity.Book" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin: Edit Book</title>
        <%@include file="allCss.jsp" %>
        <script>
            function previewFile() {
                var preview = document.getElementById('previewImage');
                var file = document.getElementById('exampleFormControlFile1').files[0];
                var reader = new FileReader();

                reader.onloadend = function () {
                    preview.src = reader.result;
                };

                if (file) {
                    reader.readAsDataURL(file);
                }
            }
        </script>

    </head>
    <body>
        <%@include file="navbar.jsp" %> 

        <%
            Book book = (Book) session.getAttribute("editBook");

            if (book == null) {
                response.sendRedirect("all_books.jsp?errMsg=No book selected for editing");
            }     
        %>

        <div class="container">
            <div class="row">
                <div class="col-md-4 offset-md-4">
                    <div class="card">
                        <div class="card-body">
                            <h4 class="text-center">Edit Book</h4>
                            <c:if test="${not empty succMsg}">
                                <p class="text-center text-success">${succMsg}</p>
                                <c:remove var="succMsg" scope="session"></c:remove>
                            </c:if>
                            <c:if test="${not empty errMsg}">
                                <p class="text-center text-danger">${errMsg}</p>
                                <c:remove var="errMsg" scope="session"></c:remove>
                            </c:if>
                            <form action="../edit_book" method="post" enctype="multipart/form-data">
                                <div class="form-group">
                                    <label>Book Name*</label>
                                    <input name="bname" type="text" class="form-control" value="<%= book.getBookName() %>">
                                </div>
                                <div class="form-group">
                                    <label>Author Name*</label>
                                    <input name="author" type="text" class="form-control" value="<%= book.getAuthor() %>">
                                </div>
                                <div class="form-group">
                                    <label>Price*</label>
                                    <input name="price" type="text" class="form-control" value="<%= book.getPrice() %>">
                                </div>

                                <div class="form-group">
                                    <label>Book Categories</label>
                                    <select name="btype" class="form-control">
                                        <option value="New" <%= book.getBookCategory().equals("New") ? "selected" : "" %>>New Book</option>
                                    </select>
                                </div>

                                <div class="form-group">
                                    <label>Book Status</label>
                                    <select name="bstatus" class="form-control">
                                        <option value="Active" <%= book.getStatus().equals("Active") ? "selected" : "" %>>Active</option>
                                        <option value="Inactive" <%= book.getStatus().equals("Inactive") ? "selected" : "" %>>Inactive</option>
                                    </select>
                                </div>

                                <div class="form-group">
                                    <label>Upload Photo</label>
                                    <input name="bimg" type="file" class="form-control-file" id="exampleFormControlFile1" accept="image/*" onchange="previewFile()">
                                    <br>
                                    <img id="previewImage" src="../book/<%= book.getPhoto() %>" style="width: 100%; height: auto; max-width: 100px; max-height: 150px;">

                                </div>
                                <input type="hidden" name="id" value="<%= book.getBookId() %>">
                                <input type="hidden" name="oldPhoto" value="<%= book.getPhoto() %>">

                                <button type="submit" class="btn btn-primary">Update</button>
                            </form>   
                        </div>
                    </div>
                </div>
            </div>  
        </div>

        <div>
            <%@include file="footer.jsp" %>
        </div>
    </body>
</html>
