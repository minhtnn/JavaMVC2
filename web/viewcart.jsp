<%-- 
    Document   : viewcart
    Created on : Oct 13, 2023, 7:23:46 AM
    Author     : N.Minh
--%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="java.util.Map"%>
<%@page import="cart.CartObject"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Book Store</h1>
        <div>
            <c:if test="${not empty sessionScope}">
                <c:set var="CART" value="${sessionScope.Cart}"/>
                <c:if test="${not empty CART}">
                    <c:set var="ITEMS" value="${CART.items}"/>
                    <c:if test="${not empty ITEMS}">
                        <form action="DispatchServlet">
                            <table border="1">
                                <thead>
                                    <tr>
                                        <th>No.</th>
                                        <th>Name</th>
                                        <th>Quantity</th>
                                        <th>Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${ITEMS}" var="entry" varStatus="counter">
                                        <tr>
                                            <td>${counter.count}</td>
                                            <td>${entry.key.name}</td>
                                            <td>${entry.value}</td>
                                            <td>
                                                <input type="checkbox" name="chkItem" 
                                                       value="${entry.key.id}" />
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    <tr>
                                        <td colspan="2">
                                            <a href="bookStore.jsp">Add more books</a>
                                        </td>
                                        <td>
                                            <input type="submit" value="Remove selected items" name="btAction" />
                                        </td>
                                        <td>
                                            <input type="submit" value="Check out" name="btAction" />
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </form>
                    </c:if>
                    <c:if test="${empty ITEMS}">
                        <h2>Your cart is empty</h2>
                        <a href="bookStore.html">Continue to buy</a>
                    </c:if>
                </c:if>
            </c:if>

        </div>
        <%--
                <%
                    //1. Cust goes to cart place
                    if (session != null) {
                        //2. Cust takes his/her cart
                        CartObject cart = (CartObject) session.getAttribute("Cart");
                        if (cart != null) {
                            //3. Cust gets items
                            Map<String, Integer> items = cart.getItems();
                            if (items != null) {
                %>

        <form action="DispatchServlet">
            <table border="1">
                <thead>
                    <tr>
                        <th>No.</th>
                        <th>Name</th>
                        <th>Quantity</th>
                        <th>Action</th>

                    </tr>
                </thead>
                <tbody>
                    <%
                        int count = 0;
                        for (String key : items.keySet()) {
                    %>
                    <tr>
                        <td><%= count++%></td>
                        <td><%= key%></td>
                        <td><%= items.get(key)%></td>
                        <td>
                            <input type="checkbox" name="chkItem" 
                                   value="<%= key%>" />
                        </td>
                    </tr>
                    <%
                        }
                    %>
                    <tr>
                        <td colspan="3">
                            <a href="bookStore.jsp">Add more books</a>
                        </td>
                        <td>
                            <input type="submit" value="Remove selected items" name="btAction" />
                        </td>
                    </tr>
                </tbody>
            </table>
        </form>
        <%                    return;
            }
        }//end cart has existed
        }//cart place must be existed
        %>
        --%>

    </body>
</html>
