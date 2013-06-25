<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑书籍</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/bookAdmin2" method="post">
	<input type="hidden" name="act" value="save"/>
	<input type="hidden" name="id" value="${book.id }"/>
	书名<input type="text" name="bookName" value="${book.bookName }"><br>
	作者<input type="text" name="author" value="${book.author }"><br>
	价格<input type="text" name="price" value="${book.price }"><br>
	<fmt:formatDate pattern="yyyy-MM-dd" value="${book.publicationDate }" var="dateFmt"/>
	出版日期<input type="text" name="publicationDate" value="${dateFmt }"><br>
	<input type="submit" value="保存"/><a href="${pageContext.request.contextPath}/bookAdmin">列表</a>
</form>
</body>
</html>