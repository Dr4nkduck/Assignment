<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="DAO.BookDAO"%>
<%@page import="entity.Book"%>

<!DOCTYPE html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Admin: All book</title>
    <%@include file="allCss.jsp" %>;
    <script>
        function editBook(bookId) {
            // Create a hidden form and submit it
            var form = document.createElement("form");
            form.method = "POST";
            form.action = "../set_book"; // The servlet to handle session setting

            var input = document.createElement("input");
            input.type = "hidden";
            input.name = "bookId";
            input.value = bookId;
            form.appendChild(input);

            document.body.appendChild(form);
            form.submit();
        }

        function deleteBook(bookId) {
            if (confirm("Are you sure you want to delete this book?")) {
                var form = document.createElement("form");
                form.method = "POST";
                form.action = "../delete_book"; // Ensure this matches your servlet URL

                var input = document.createElement("input");
                input.type = "hidden";
                input.name = "bookId";
                input.value = bookId;
                form.appendChild(input);

                document.body.appendChild(form);
                form.submit();
            }
        }
    </script>
</head>
<body>    
    <%@include file="navbar.jsp" %>;
    <!-- Remove messages from session after showing -->
    <c:remove var="errMsg" scope="session"/>
    <c:remove var="succMsg" scope="session"/>
    <main contentEditable>
        <h3 class="text-center">All Books in Store</h3>

        <table class="table table-striped">
            <thead class="bg-primary text-white">
                <tr>
                    <th scope="col">ID</th>
                    <th scope="col">Image</th>
                    <th scope="col">Book Name</th>
                    <th scope="col">Author</th>
                    <th scope="col">Price</th>
                    <th scope="col">Categories</th>
                    <th scope="col">Status</th>
                    <th scope="col">Action</th>
                </tr>
            </thead>
            <tbody>
                <%
            BookDAO dao = new BookDAO();
            List<Book> books = dao.getAllBooks();
                %>

                <% if (books.isEmpty()) { %>
                <tr>
                    <td colspan="8" class="text-center">
                        <p class="text-muted">No books added! So sad 😢</p>
                    </td>
                </tr>
                <% } else { 
        for (Book book : books) { %>
                <tr>
                    <td><%= book.getBookId() %></td>
                    <td><img src="../book/<%= book.getPhoto() %>" width="50" height="50"></td>
                    <td><%= book.getBookName() %></td>
                    <td><%= book.getAuthor() %></td>
                    <td>$<%= book.getPrice() %></td>
                    <td><%= book.getBookCategory() %></td>
                    <td><%= book.getStatus() %></td>
                    <td>
                        <button onclick="editBook('<%= book.getBookId() %>')" class="btn btn-sm btn-primary"><i class="fas fa-edit"></i>  Edit</button>
                        <button onclick="deleteBook(<%= book.getBookId() %>)" class="btn btn-sm btn-danger"><i class="fas fa-trash-alt"></i>  Delete</button>
                    </td>
                </tr>
                <% } 
    } %>
            </tbody>
        </table>
    </main>
</body>
<%@include file="footer.jsp" %>
</html>

