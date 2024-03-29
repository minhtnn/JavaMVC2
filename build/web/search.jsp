<%-- 
    Document   : search
    Created on : Sep 29, 2023, 8:57:58 AM
    Author     : N.Minh
--%>

<%@page import="registration.RegistrationDTO"%>
<%@page import="java.util.List"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> <%-- No library but still has <c:if> --%>
<%-- delete jstl --%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search</title>
    </head>
    <body>
        <div>
            <form action="logOutController" method="POST">
                <input type="submit" value="Log out" name="btAction" />
            </form>
        </div>
        <div>
            <%-- want to catch, must gans into an attribute --%>
            <%-- only present view only when --%>

            <%--            
<%
                            Cookie[] cookies = request.getCookies();
                            if (cookies != null) {
                                Cookie newestCookie = cookies[cookies.length - 1];
                                String username = newestCookie.getName();
                        %>
                        <p style="color: red">Welcome, <%= username%></p>
                        <%
                            }
                        %>

            <!--        <font color="red">
            <%
                Cookie[] cookies1 = request.getCookies();
                if (cookies1 != null) {
                    String username1 = "";
                    for (Cookie cookie1 : cookies1) {
                        String temp = cookie1.getName();
                        if (!temp.equals("JSESSIONID")) {
                            username1 = temp;
                        }
                    }
            %>
    Welcome, <%= username1%>
            <%
                }
            %>
            
            </font>-->
            --%>
            <c:set var="UserInfor" value="${sessionScope.USER_INFO}"/>
            <c:if test="${empty UserInfor}">
                <%--
                    response.sendRedirect("login.html");
                --%>
                <c:redirect url="login.html" />
            </c:if>
            <c:if test="${not empty UserInfor}">
                <font>
                Welcome, ${sessionScope.USER_INFO.fullName}
                </font>

                <h1>Search Page</h1>
                <%--
                <form action="DispatchServlet">
                    <div>
                        <label for="search">Name: </label>
                        <input type="text" name="txtSearchValue" 
                               value="<%= request.getParameter("txtSearchValue")%>">
                        <input type="submit" name="btAction" value="Search">
                    </div>
                </form>
                --%>
                <form action="searchLastNameController">
                    <div>
                        <label for="search">Name: </label>
                        <input type="text" name="txtSearchValue" 
                               value="${param.txtSearchValue}">
                        <input type="submit" name="btAction" value="Search">
                    </div>
                </form>
                <br/>
                <form action="updateAccountController">
                    <c:set var="searchValue" value="${param.txtSearchValue}"/>
                    <c:if test="${not empty searchValue}"> <%-- --%>
                        <c:set var="result" value="${requestScope.SEARCH_RESULT}"/>
                        <c:if test="${empty result}">
                            <h2>No record is matched</h2>
                        </c:if> 
                        <c:if test="${not empty result}">
                            <table border="1">
                                <thead>
                                    <tr>
                                        <th>No.</th>
                                        <th>Username</th>
                                        <th>Password</th>
                                        <th>Full name</th>
                                        <th>Role</th>
                                        <th>Delete</th>
                                        <th>Update</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${result}" var="dto" varStatus="counter">
                                    <form action="updateAccountController">
                                        <%-- 500: property not found  --%>
                                        <%-- 1. No define get() --%>
                                        <%-- 2: property does not copy -> error  --%>
                                        <tr>
                                            <td>${counter.count}</td>
                                            <%-- <td>${counter}</td> --%>
                                            <td>
                                                ${dto.username}
                                                <input type="hidden" name="txtUsername" 
                                                       value="${dto.username}" />
                                                <%-- <td>${dt.username}</td> --%>
                                                <%-- No  --%>
                                            </td>
                                            <td>
                                                <input type="text" name="txtPassword" value="${dto.password}"/>
                                            </td>
                                            <%-- <td>${dto.pasword}</td>  miss s--%>
                                            <%-- no attribute --%>
                                            <td>${dto.fullName}</td>
                                            <td>
                                                <input type="checkbox" name="chkAdmin" value="ON"
                                                       <c:if test="${dto.role}">
                                                           checked="checked"
                                                       </c:if>
                                                       />
                                                <%-- <td>${result.role}</td> --%>
                                                <%-- 500: when call result -> get index (because cannot convert username) --%>
                                            </td>
                                            <td>
                                                <c:url var="urlRewriting" value="deleteAccountController">
                                                    <c:param name="btAction" value="Delete"></c:param>
                                                    <c:param name="pk" value="${dto.username}"></c:param>
                                                    <c:param name="lastSearchValue" value="${searchValue}"></c:param>
                                                </c:url>
                                                <a href="${urlRewriting}">Delete</a>
                                            </td>
                                            <td>
                                                <input type="hidden" name="lastSearchValue"
                                                       value="${searchValue}"/>
                                                <input type="submit" value="Update" name="btAction"/>
                                            </td>
                                        </tr>
                                    </form>
                                </c:forEach>
                                </tbody>
                            </table>
                        </c:if> <%-- --%>
                    </c:if>
                </form>

                <br/>
                <%--
                <%
                    String searchValue = request.getParameter("txtSearchValue");
                    //If there are no value in search.html (search value did not trigger in previous form)
                    if (searchValue != null) {
                        //Before controller send , Search value is in attribute
                        List<RegistrationDTO> result = (List<RegistrationDTO>) request.getAttribute("SEARCH_RESULT");
                        if (result != null) {
                %>
                <table border="1">
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>Username</th>
                            <th>Password</th>
                            <th>Full name</th>
                            <th>Role</th>
                            <th>Delete</th>
                            <th>Update</th>
                        </tr>
                    </thead>
                    <tbody>


                    <%
                        int count = 0;
                        for (RegistrationDTO dto : result) {
                            String urlRewriting = "DispatchServlet"
                                    + "?btAction=Delete"
                                    + "&pk=" + dto.getUsername()
                                    + "&lastSearchValue=" + searchValue;

                    %>
                <form action="DispatchServlet" method="POST">
                    <tr>
                        <td>
                            <%= ++count%>

                        </td>
                        <td>
                            <%= dto.getUsername()%>
                            <input type="hidden" name="txtUsername" 
                                   value="<%= dto.getUsername()%>" />

                        </td>
                        <td>
                            <input type="text" name="txtPassword" value="<%= dto.getPassword()%>" />

                        </td>
                        <td>
                            <%= dto.getFullName()%>
                        </td>
                        <td>
                            <input type="checkbox" name="chkAdmin" value="ON"
                                   <%
                                       if (dto.isRole()) {
                                   %>
                                   checked="checked"
                                   <%
                                       }
                                   %>
                                   />
                        </td>
                        <td>
                            <a href="<%= urlRewriting%>">Delete</a>
                        </td>
                        <td>
                            <input type="hidden" name="lastSearchValue"
                                   value="<%= request.getParameter("txtSearchValue")%>"/>
                            <input type="submit" value="Update" name="btAction"/>
                        </td>

                    </tr>
                </form>
                

                <%
                    }//end traverse DTO
                %>
                </tbody>
            </table>
            <%
            } else {
            %>
            <h2>No record is matched</h2>
            <%
                    }
                }
                //render
%>
                --%>
            </c:if>
        </div>
    </body>
</html>
