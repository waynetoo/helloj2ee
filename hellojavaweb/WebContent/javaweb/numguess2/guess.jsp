<%@ page contentType="text/html; charset=UTF-8"%>
<html>
	<head>
		<title>猜数字</title>
	</head>
	<body>
${msg } 你已经猜了${guessTimes }次。本游戏共开了${newGameCnt }局。<a href="<%=request.getContextPath() %>/numberGuessServlet?act=newGame">新游戏</a><br/>
<form action="<%=request.getContextPath() %>/numberGuessServlet" method="get">
请填写1~100的数值
<input type="hidden" name="act" value="guess">
<input type="text" name="guess">${param.guess }
<input type="submit" name="btn" value="提交">
</form>
	</body>
</html>
