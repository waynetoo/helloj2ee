<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
${msg } 你已经猜了${guessTimes }次。本游戏共开了${totalVisits }局。<a href="new_game.jsp">新游戏</a><br/>
<form action="guess.jsp" method="get">
请填写1~100的数值
<input id="guessInput" type="text" name="guess">${param.guess }
<input type="submit" value="提交" onclick="return validate()">
</form>
<script type="text/javascript">
<!--
function validate(){
	var ele = document.getElementById("guessInput");
	if(!ele){alert("找不到输入框");return false;}
	var v = ele.value;
	var pass = true;
	if(v == "")pass = false;
	if(/[^\d]/.test(v))pass = false;
	if(!pass){alert("输入有错误。");return false;}
	return true;
}
//-->
</script>