package com.yuqiaotech.helloj2ee.webapp.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PlusServlet extends HttpServlet {

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String add1Str = request.getParameter("add1");
		String add2Str = request.getParameter("add2");
		int add1 = Integer.parseInt(add1Str);
		int add2 = Integer.parseInt(add2Str);
		int sum = add1+add2;
		response.getWriter().write(add1+"+"+add2+"="+sum);
	}
}
