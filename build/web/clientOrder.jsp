<%-- 
    Document   : ClientOrder
    Created on : Oct 31, 2023, 9:08:11 PM
    Author     : N.Minh
--%>
<%-- Nếu thanh toán, số lượng ở DB sẽ giảm xuống --%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Order list</h1>
        <c:if test="${not empty sessionScope}">
            <c:set var="CheckOut" value="${sessionScope.CHECK_OUT}"/>
            <c:if test="${not empty CheckOut}">
                <c:set var="Orders" value="${CheckOut.orders}"/>
                <c:set var="OrderDetails" value="${CheckOut.orderDetails}"/>
                <c:if test="${(not empty Orders) && (not empty OrderDetails)}">
                    <c:forEach items="${Orders}" var="entry" varStatus="counter">
                        OrderId: ${entry.key}<br/>
                        Date: ${entry.value.orderDate}
                        <table border="1">
                            <tr>
                                <th>OrderDetailID</th>
                                <th>ProductID</th>
                                <th>Quantity</th>
                                <th>Unit Price</th>
                                <th>Total</th>
                            </tr>
                            <c:forEach items="${OrderDetails}" var="entry1" varStatus="counter">
                                <c:if test="${entry1.value.orderID == entry.key}">
                                    <tr>
                                        <td>${entry1.key}</td>
                                        <td>${entry1.value.productID}</td>
                                        <td>${entry1.value.quantity}</td>
                                        <td>${entry1.value.price}</td>
                                        <td>${entry1.value.total}</td>
                                    </tr>
                                </c:if>
                            </c:forEach>
                            <tr>
                                <td colspan="4">Total</td>
                                <td>${entry.value.total}</td>
                            </tr>
                        </table>
                    </c:forEach>
                    <br/>
                    <a href="bookStore.html">Back to book store!</a>
                </c:if>
            </c:if>
            <c:if test="${empty CheckOut}">
                <a href="bookStore.html">Back to book store!</a>
            </c:if>
        </c:if>
        <c:if test="${empty sessionScope}">
            <a href="bookStore.html">Back to book store!</a>
        </c:if>

        <%--
        <c:if test="${not empty sessionScope}">
            <c:set var="CHECK_OUT" value="${sessionScope.CHECK_OUT}"/>
            <c:if test="${not empty CHECK_OUT}">
                <c:set var="ORDERS" value="${CHECK_OUT.orders}"/>
                <c:set var="ORDERDETAILS" value="${CHECK_OUT.orderDetails}"/>
                <c:if test="${not empty ORDERS}">
                    <c:if test="${not empty ORDERDETAILS}">
                        <c:forEach items="${ORDERS}" var="entry" varStatus="counter">
                            OrderID: ${entry.key} <br/>
                            Date: ${entry.value.orderDate}<br/>
                            <table>
                                <tr>
                                    <th>ProductID</th>
                                    <th>Quantity</th>
                                    <th>Price</th>
                                    <th>OrderDetailTotal</th>
                                </tr>
                                <c:forEach items="${ORDERDETAILS}" var="entry1" varStatus="counter">
                                    <tr>
                                        <td>${entry1.key}</td>
                                        <td>${entry1.value.quantity}</td>
                                        <td>${entry1.value.price}</td>
                                        <td>${entry1.value.total}</td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </c:forEach>
                    </c:if>
                </c:if>
            </c:if>
        </c:if>
        --%>

    </body>
</html>
