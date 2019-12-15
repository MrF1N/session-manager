<%--
  Страница изменения IP (изменение имени)
  Created by IntelliJ IDEA.
  User: Grey
  Date: 19.10.2019
  Time: 13:30
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Session</title>
    <style type="text/css">
        .error {
            color: #ff0000;
            font-weight: bold;
        }
    </style>
</head>
<body>

<div align="center">
    <h2>Edit User Ip</h2>
    <c:if test="${!empty userIp.ipAddr}">
        <c:set var="act" scope="page" value="edit"/>
    </c:if>
    <form:form action="${act}" method="post" modelAttribute="userIp">
        <table border="0" cellpadding="5">
            <tr>
                <td>Name:</td>
                <td><form:input path="name"/></td>
                <td><form:errors path="name" cssClass="error"/></td>
            </tr>
            <tr>
                <td>IP Address:</td>
                <td>${userIp.ipAddr}
                    <form:hidden path="ipAddr"/></td>
            </tr>
            <tr>
                <td>Banned by IP:</td>
                <td>${userIp.bannedByIp}
                    <form:hidden path="bannedByIp"/></td>
            </tr>
            <tr>
                <td colspan="2">
                    <c:if test="${!empty userIp.ipAddr}">
                        <input type="submit"
                               value="<spring:message text="Edit User Ip"/>"/>
                    </c:if>
                </td>
            </tr>
        </table>
    </form:form>
</div>

</body>
</html>
