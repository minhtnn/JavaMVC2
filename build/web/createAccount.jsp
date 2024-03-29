<%-- 
    Document   : createAccount
    Created on : Oct 13, 2023, 9:14:50 AM
    Author     : N.Minh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>
            .display_flex{
                display: flex;
                padding-bottom: 10px;
            }
            .display_flex label{
                width: 100px;
            }

            .display_flex input{
                width: 200px;
                margin-right: 10px;
            }

            .display_button{
                display: flex;
            }
            .display_button input{
                width: 100px;
                margin-right: 20px;
            }
            
            div{
                padding-bottom: 10px;
            }
        </style>
    </head>
    <body>
        <h2>Sign up</h2>
        <form action="createAccountController" method="POST">
            <c:set var="errors" value="${requestScope.CREATE_ERRORS}"/>
            <div class="display_flex">
                <label for="username">Username </label>
                <input type="text" name="txtUsername" value="${param.txtUsername}" /> (6 - 30 characters)<br/>
                <c:if test="${not empty errors.usernameLengthErr}">
                    <br/>
                    <font color="red">
                        ${errors.usernameLengthErr}
                    </font>
                </c:if>
                <c:if test="${not empty errors.usernameIsExisted}">
                    <br/>
                    <font color="red">
                        ${errors.usernameIsExisted}
                    </font>
                </c:if>
                    <%-- 2 errors will be shown by 2 if --%>
            </div>
            <div class="display_flex">
                <label for="password">Password</label>
                <input type="password" name="txtPassword" value="" />(6 - 20 characters)<br/>
                <c:if test="${not empty errors.passwordLengthErr}">
                    <br/>
                    <font color="red">
                        ${errors.passwordLengthErr}
                    </font>
                </c:if>
                
            </div>
            <div class="display_flex">
                <label for="confirm">Confirm </label>
                <input type="password" name="txtConfirm" value="" /><br/>
                <c:if test="${not empty errors.confirmNotMatch}">
                    <br/>
                    <font color="red">
                        ${errors.confirmNotMatch}
                    </font>
                </c:if>
            </div>
            <div class="display_flex">
                <label for="fullname">Full name:</label>
                <input type="text" name="txtFullname" value="${param.txtFullname}" />(2 - 50 characters)<br/>
                <c:if test="${not empty errors.fullnameLengthErr}">
                    <br/>
                    <font color="red">
                        ${errors.fullnameLengthErr}
                    </font>
                </c:if>
            </div>
            
            <div>
                <input type="submit" value="Create new account" name="btAction" />
                <input type="reset" value="Reset" />
            </div>
        </form>
    </body>
</html>
