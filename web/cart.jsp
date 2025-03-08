<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page import="java.util.List"%>
<%@page import="entity.CartItem"%>
<%@page import="entity.User"%>
<%@page import="DAO.CartDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>

<%
    // Get the user from the session
    User user = (User) session.getAttribute("userobj");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    // Fetch cart items
    CartDAO cartDAO = new CartDAO();
    List<CartItem> cartItems = cartDAO.getCartItems(user.getId());
    request.setAttribute("cartItems", cartItems);

    double cartTotal = 0;
    request.setAttribute("cartTotal", cartTotal);
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ebook: Cart</title>
        <script>
            function updateQuantity(bookId, change) {
                let qtyInput = document.getElementById("qty-" + bookId);
                let totalPriceElement = document.getElementById("total-" + bookId);
//                let cartTotalElement = document.getElementById("cart-total");
                let currentQty = parseInt(qtyInput.value);

                let newQty = currentQty + change;
                if (newQty < 1)
                    return; // Prevent quantity going below 1

                qtyInput.value = newQty;

                // Send AJAX request to update quantity in backend
                let xhr = new XMLHttpRequest();
                xhr.open("POST", "updateCartQuantity", true);
                xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                xhr.onreadystatechange = function () {
                    if (xhr.readyState === 4) {
                        console.log("Response:", xhr.responseText);  // Debugging
                        if (xhr.status === 200) {
                            let response = JSON.parse(xhr.responseText);
                            if (response.success) {
                                totalPriceElement.innerHTML = response.itemTotal + "$";
//                                cartTotalElement.innerHTML = response.cartTotal + "₫";
                            } else {
                                alert("Error: " + response.error);
                            }
                        }
                    }
                };

                xhr.send("bookId=" + bookId + "&quantity=" + newQty);
                
                
                updateTotal();
            }
        </script>
        <%@include file="all_component/allCss.jsp" %>
    </head>
    <body style="background-color: #f0f1f2">
        <%@include file="all_component/navbar.jsp" %>
        <div class="container mt-5">
            <div class="cart-container">
                <div class="cart-header">Products</div>

                <c:if test="${empty cartItems}">
                    <p class="text-center text-muted mt-3">Your cart is empty.</p>
                </c:if>

                <c:if test="${not empty cartItems}">
                    <c:forEach var="item" items="${cartItems}">
                        <div class="cart-item">
                            <input type="checkbox" class="cart-checkbox">
                            <img src="images/${item.bookId}.jpg" alt="${item.bookname}">
                            <div class="cart-details">
                                <div>${item.bookname}</div>
                                <div class="text-muted">Author: ${item.author}</div>
                                <div class="price">${item.price}$</div>
                            </div>
                            <div class="cart-actions">
                                <div class="quantity-box">
                                    <button onclick="updateQuantity(${item.bookId}, -1)">-</button>
                                    <input type="text" id="qty-${item.bookId}" value="${item.quantity}" class="quantity" readonly>
                                    <button onclick="updateQuantity(${item.bookId}, 1)">+</button>
                                </div>
                                <div class="price" id="total-${item.bookId}">${item.price * item.quantity}$</div>
                                <a href="removeFromCart?bookId=${item.bookId}" class="text-danger">Remove</a>
                            </div>
                        </div>
                    </c:forEach>

                    <div class="checkout-section">
                        <div>
                            <input type="checkbox"> Select All (${cartItems.size()})
                            <a href="#" class="text-danger">Remove</a>
                        </div>
                        <div>
                            Total Cost: <span class="price" id="cartTotal">${cartTotal}$</span>
                            <button class="checkout-button">ORDER NOW</button>
                        </div>
                    </div>
                </c:if>
            </div>
        </div>
        <script>
            document.querySelectorAll('.cart-checkbox').forEach(checkbox => {
                console.log("lmao");
                checkbox.addEventListener('change', updateTotal);
            });

            function updateTotal() {
                let total = 0;
                document.querySelectorAll('.cart-item').forEach(item => {
                    let checkbox = item.querySelector('.cart-checkbox');
                    if (checkbox.checked) {
                        let price = parseFloat(item.querySelector('.price').textContent);
                        let quantity = parseInt(item.querySelector('.quantity').value);
                        total += price * quantity;
                    }
                });
                document.getElementById('cartTotal').textContent = total.toLocaleString() + "$";
            }
        </script>
    </body>
    <br>
    <%@include file="all_component/footer.jsp" %>

</html>
