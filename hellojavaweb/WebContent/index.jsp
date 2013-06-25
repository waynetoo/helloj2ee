<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>hellojee 首页</title>
<style>
<!--
* {
margin:0;
padding:0;
}
.block{
	float: left;
}
dl {margin:10px ;width:500px; border: 1px solid #CCCCCC;}
dl dt { 
	background-image: url(images/bg.jpg);
	height: 28px;background-repeat: repeat-x;
	font-size: 12px;line-height: 28px;
	font-weight: bold;color: #666;
	padding-left:10px;
}
dl dd { 
	line-height:24px;background-color:#F8F8F8;
	background-image:url(images/dot.gif);
	background-repeat: no-repeat;
	background-position: 10px 10px;
	padding-left: 20px;
}
-->
</style>
</head>
<body>
你好，这是hellojee项目首页，现在时间是<%=new Date() %>。<br/>
本项目主要是展示最基本的java语言、javaweb开发和jdbc使用。<br/>
本项目只是一些例子，指出常规开发至少需要了解的一些内容，具体知识的系统了解，还是要通过视频、书籍、作业来推进。
<div class="block">
<dl>
<dt>java基础</dt>
		<dd>
		先看看/src/com/yuqiaotech/hellojee/java/HelloWorld.java。
		</dd>
		<dd>
		/src/com/yuqiaotech/hellojee/java/reflect 一些反射的例子
		</dd>
		<dd>
		/src/com/yuqiaotech/hellojee/xml xml操作的例子
		</dd>
		<dd>
		/src/com/yuqiaotech/hellojee/jdbc 数据库操作的例子
		</dd>
		<dd>
		/src/com/yuqiaotech/hellojee/java/generic 泛型的例子
		</dd>
		<dd>
		/src/com/yuqiaotech/hellojee/java/thread 线程的例子
		</dd>
</dl>
</div>

<div class="block">
<dl>
<dt>JSP和HttpServlet</dt>
		<dd>
		<a target="_blank" href="<%=request.getContextPath()%>/html.html">这就是html</a>
		</dd>
		<dd>
		本页面就是一个简单的JSP，了解下HTML(html,css,javascript),el,jsp里如何写java代码等。
		</dd>
		<dd>
		<a target="_blank" href="<%=request.getContextPath()%>/javaweb/hello.jsp?name=Tom">JSP和request.getParameter的使用。</a>
		</dd>
		<dd>
		<a target="_blank" href="<%=request.getContextPath()%>/SampleServlet?a=123&b=xxx">servlet和request的getParameterNames方法</a>
		</dd>
		<dd>
		<a target="_blank" href="<%=request.getContextPath()%>/javaweb/plus1/plus_form.html">加法器：表单和servlet以及response</a>
		</dd>
		<dd>
		<a target="_blank" href="<%=request.getContextPath()%>/forward">servlet里做forward</a>
		</dd>
</dl>
</div>

<div class="block">
<dl>
<dt>filter和listener</dt>
		<dd>
		一个简单的过滤器，打印所有请求的相关信息。<br/>/src/com/yuqiaotech/hellojee/webapp/servlet/SampleFilter.java
		</dd>
		<dd>
		session的监听器 <br/>/src/com/yuqiaotech/hellojee/webapp/servlet/SessionListener.java
		</dd>
		<dd>
		servletContext的监听器 <br/>/src/com/yuqiaotech/hellojee/webapp/servlet/ContextSetupListener.java
		</dd>

</dl>
</div>

<div class="block">
<dl>
<dt>较综合的例子</dt>

		<dd>
		<a target="_blank" href="${pageContext.request.contextPath}/javaweb/numguess1/new_game.jsp">使用jsp做的猜数字</a><br/>
		</dd>

		<dd>
		<a target="_blank" href="${pageContext.request.contextPath}/numberGuessServlet">使用servlet+jsp做的猜数字</a><br/>
		</dd>

		<dd>
		<a target="_blank" href="${pageContext.request.contextPath}/bookAdmin">使用servlet+jsp+jdbc做的书籍管理</a><br/>
		</dd>

		<dd>
		<a target="_blank" href="${pageContext.request.contextPath}/bookAdmin2">使用servlet+jsp+jdbc做的书籍管理2</a><br/>
		</dd>

		<dd>
		<a target="_blank" href="${pageContext.request.contextPath}/simpleClickstream">使用filter做访问统计</a><br/>
		 /src/com/yuqiaotech/hellojee/webapp/servlet/SimpleClickstreamFilter.java
		</dd>
</dl>
</div>


<div class="block">
<dl>
<dt>其他</dt>

		<dd>
		<a target="_blank" href="${pageContext.request.contextPath}/javascript/index.html">javascript的例子</a><br/>
		</dd>
</dl>
</div>
</body>
</html>