<%@ page contentType="text/html; charset=UTF-8"%>
<html>
	<head>
		<title>猜数字</title>
	</head>
	<body>
恭喜你猜对了。你共猜了${guessTimes }次。
本游戏共开局${newGameCnt }次。
<a href="<%=request.getContextPath() %>/numberGuessServlet?act=newGame">开始新游戏</a>
	</body>
</html>
