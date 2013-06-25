package com.yuqiaotech.helloj2ee.webapp.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ForwardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public ForwardServlet() {
        System.out.println("servlet被实例化ForwardServlet.ForwardServlet()");
    }
    /**
     * 记得讲解时通过修改这个内容，进一步演示forward到url，以及redirect的内容。
     */
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("title", "标题");
		request.setAttribute("content", "<strong>内容</strong>");
		request.getRequestDispatcher("/javaweb/forward_test.jsp").forward(request, response);
	}
}
