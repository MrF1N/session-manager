<%--
  Страница просмотра всех сессий и IP
  Created by IntelliJ IDEA.
  User: Grey
  Date: 20.10.2019
  Time: 19:00
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Sessions</title>
    <style type="text/css">
        .tg {
            border-collapse: collapse;
            border-spacing: 0;
            border-color: #ccc;
        }

        .tg td {
            font-family: Arial, sans-serif;
            font-size: 14px;
            padding: 10px 5px;
            border-style: solid;
            border-width: 1px;
            overflow: hidden;
            word-break: normal;
            border-color: #ccc;
            color: #333;
            background-color: #fff;
        }

        .tg th {
            font-family: Arial, sans-serif;
            font-size: 14px;
            font-weight: normal;
            padding: 10px 5px;
            border-style: solid;
            border-width: 1px;
            overflow: hidden;
            word-break: normal;
            border-color: #ccc;
            color: #333;
            background-color: #f0f0f0;
        }

        .tg .tg-4eph {
            background-color: #f9f9f9
        }

        a {
            padding: 10px 20px;
            text-decoration: none;
        }
    </style>
</head>
<body>
<div align="center">
    <h3>Sessions List</h3>
    <c:if test="${!empty listUserSessions}">
        <table class="tg">
            <tr>
                <th width="80">User Session ID</th>
                <th width="120">IP Addr</th>
                <th width="120">Banned by Session</th>
                <th width="60">Ban by Session</th>
            </tr>
            <c:forEach items="${listUserSessions}" var="userSession">
                <tr>
                    <td>${userSession.id}</td>
                    <td>${userSession.ipAddr}</td>
                    <td>${userSession.bannedBySession}</td>
                        <%--                    <td><a href="<c:url value='/admin/session/${userSession.id}' />">Edit</a></td>--%>
                    <td><a href="<c:url value='/admin/session/${userSession.id}/ban_session' />">Ban</a></td>
                        <%--                    <td><a href="<c:url value='/admin/session/${userSession.id}/delete' />">Del</a></td>--%>
                </tr>
            </c:forEach>
        </table>
    </c:if>
    <br>
    <h3>Ips List</h3>
    <c:if test="${!empty listUserIps}">
        <table class="tg">
            <tr>
                <th width="80">User Ip</th>
                <th width="80">Name</th>
                <th width="120">Banned by Ip</th>
                <th width="60">Edit</th>
                <th width="60">Ban by Ip</th>
            </tr>
            <c:forEach items="${listUserIps}" var="userIp">
                <tr>
                    <td>${userIp.ipAddr}</td>
                    <td>${userIp.name}</td>
                    <td>${userIp.bannedByIp}</td>
                    <td><a href="<c:url value='/admin/ip/${userIp.ipAddr}' />">Edit</a></td>
                    <td><a href="<c:url value='/admin/ip/${userIp.ipAddr}/ban_ip' />">Ban</a></td>
                </tr>
            </c:forEach>
        </table>
        <br>
    </c:if>
</div>
</body>
</html>