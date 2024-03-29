<%-- 
    Document   : bookStore
    Created on : Oct 12, 2023, 5:28:00 PM
    Author     : tahoa
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Book Store</title>
        <style>
            div{
                padding-bottom: 10px;
            }
        </style>
    </head>
    <body>
        <div>
            <h1>Book Store</h1>
            <form action="searchBookController">
                <div>
                    <label for="search">Search book: </label>
                    <input type="text" name="txtSearchBookValue" 
                           value="${param.txtSearchBookValue}">
                    <input type="submit" name="btAction" value="Find">
                </div>
            </form>
        </div>
        <div>
                <c:set var="searchBook" value="${param.txtSearchBookValue}"/>
                <c:if test="${not empty searchBook}">
                    <c:set var="result" value="${requestScope.BOOKS_RESULT_SET}"/>
                    <c:if test="${empty result}">
                        <h2>Book does not exist!</h2>
                    </c:if>
                    <c:if test="${not empty result}">
                        <table border="1">
                            <thead>
                                <tr>
                                    <th>No.</th>
                                    <th>Name</th>
                                    <th>Price</th>
                                    <th>Quantity</th>
                                    <th>Action</th>

                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${result}" var="dto" varStatus="counter">
                                <form action="addItemToCartController">
                                    <c:if test="${dto.status}">
                                        <tr>
                                            <td>${counter.count}</td>
                                            <td>${dto.name} </td>
                                            <td>${dto.unitPrice}</td>
                                            <td>
                                                <input type="text" name="txtAddQuantity" value="1" /><br/>
                                                Books in stock: ${dto.quantity}<br/>
                                            </td>
                                            <td>
                                                <input type="hidden" name="txtBookID" value="${dto.id}"/>
                                                <input type="hidden" name="txtSearchBookValue" value="${param.txtSearchBookValue}" />
                                                <input type="submit" value="Add Book to your cart" name="btAction">
                                            </td>
                                        </tr>
                                    </c:if>
                                </form>
                            </c:forEach>
                            </tbody>
                        </table>
                        <c:set var="addBooksErrors" value="${requestScope.CREATE_ADD_BOOKS_ERROR}"/>

                        <c:if test="${not empty addBooksErrors.addBookMoreThanRemainErr}">
                            <font color="red">
                            ${addBooksErrors.addBookMoreThanRemainErr}
                            </font>
                        </c:if>
                        <c:if test="${not empty addBooksErrors.inputQuantityErr}">
                            <font color="red">
                            ${addBooksErrors.inputQuantityErr}
                            </font>
                        </c:if>
                </c:if>
            </c:if>
        </div>
        <div>
            <form action="viewCartPageController">
                <input type="submit" name="btAction" value="View your cart" />
            </form>
        </div>
    </body>
</html>