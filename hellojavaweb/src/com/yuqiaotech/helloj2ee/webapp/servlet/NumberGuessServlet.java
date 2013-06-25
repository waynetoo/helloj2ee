package com.yuqiaotech.helloj2ee.webapp.servlet;

import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 */
public class NumberGuessServlet extends HttpServlet {
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String act = request.getParameter("act");
		String result = null;
		if("guess".equals(act)){
			result = guess(request, response);
		}else{
			result = newGame(request, response);
		}
		String forwardTo = "";
		if("success".equals(result)){
			forwardTo = "/javaweb/numguess2/success.jsp";
		}else{
			forwardTo = "/javaweb/numguess2/guess.jsp";
		}
		request.getRequestDispatcher(forwardTo).forward(request, response);
	}
	
	private String newGame(HttpServletRequest request, HttpServletResponse response){
		Random r = new Random();
		Integer i = r.nextInt(100)+1;
		request.getSession().setAttribute("number",i);
		request.getSession().setAttribute("guessTimes",0);
		Integer newGameCnt = (Integer)request.getSession().getServletContext().getAttribute("newGameCnt");
		if(newGameCnt == null)newGameCnt=0;
		request.getSession().getServletContext().setAttribute("newGameCnt",++newGameCnt);
		return "newGame";
	}
	private String guess(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession();
		int guess = Integer.parseInt(request.getParameter("guess"));
		Integer number = (Integer)session.getAttribute("number");
		int guessTimes = (Integer)session.getAttribute("guessTimes");
		session.setAttribute("guessTimes",++guessTimes);
		if(guess == number){
			request.setAttribute("msg","对了.");
			return "success";
		}else if(guess > number){
			request.setAttribute("msg","偏大了.");
		}else{
			request.setAttribute("msg","偏小了.");
		}
		return "failure";
	}
}
