<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
	<head>
		<title>hello jsp</title>
	</head>
	<body>
<table>
	<thead>
	<th>序号</th><th>URI</th><th>访问次数</th>
	</thead>
	<c:forEach items="${entriesList }" var="entry" varStatus="status">
	<tr>
		<td>${ status.count}</td>
		<td>${ entry.key}</td>
		<td>${ entry.value}</td>
	</tr>
	</c:forEach>
</table>
	</body>
</html>
