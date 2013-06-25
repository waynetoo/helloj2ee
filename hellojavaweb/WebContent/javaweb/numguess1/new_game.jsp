<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><%@page import="java.util.Random"%><%
Random r = new Random();
Integer i = r.nextInt(100)+1;
session.setAttribute("number",i);
session.setAttribute("guessTimes",0);
Integer totalVisits = (Integer)application.getAttribute("totalVisits");
if(totalVisits == null)totalVisits=0;
application.setAttribute("totalVisits",++totalVisits);
%>
猜数字游戏开始。
本游戏共开了${totalVisits }局。<br/>
<form action="guess.jsp" method="get">
请填写1~100的数值
<input id="guessInput" type="text" name="guess">${param.guess }
<input type="submit" value="提交">
</form>