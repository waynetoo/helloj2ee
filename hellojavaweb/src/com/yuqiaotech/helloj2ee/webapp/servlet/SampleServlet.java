package com.yuqiaotech.helloj2ee.webapp.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 看看document下HttpServlet.java的代码，理解这里的doGet和doPost的来龙去脉。
 */
public class SampleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SampleServlet() {
        super();
        System.out.println("servlet实例化SampleServlet.SampleServlet()");
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.write("<html>");
		out.write("<head>");
		out.write("<title>");
		out.write("servlet演示");
		out.write("</title>");
		out.write("</head>");
		out.write("<body>");
		out.write("传递的参数信息:<br/>");
		
		Enumeration enume = request.getParameterNames();
		while (enume.hasMoreElements()) {
			String paramName = enume.nextElement().toString();
			out.write(paramName+"="+request.getParameter(paramName)+"<br/>");
		}
		out.write("</body>");
		out.write("</html>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet( request,  response);
	}

}
