<%@ page language="java" pageEncoding="UTF-8"%><%
int guess = Integer.parseInt(request.getParameter("guess"));
Integer number = (Integer)session.getAttribute("number");
int guessTimes = (Integer)session.getAttribute("guessTimes");
session.setAttribute("guessTimes",++guessTimes);
if(guess == number){
	request.setAttribute("msg","对了.");
}else if(guess > number){
	request.setAttribute("msg","偏大了.");
}else{
	request.setAttribute("msg","偏小了.");
}
%><jsp:forward page="number_input.jsp"></jsp:forward>