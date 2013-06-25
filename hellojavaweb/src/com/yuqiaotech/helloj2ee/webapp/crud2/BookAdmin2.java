package com.yuqiaotech.helloj2ee.webapp.crud2;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BookAdmin2 extends HttpServlet{
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	BookDao bookDao = new BookDao();
	/**
	 * 根据传过来的act这个参数的值来判断调用哪个方法。
	 */
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String act = req.getParameter("act");
		if("createForm".equals(act)){
			createForm(req,resp);
		}else if("editForm".equals(act)){
			editForm(req,resp);
		}else if("save".equals(act)){
			save(req,resp);
			search(req,resp);
		}else if("delete".equals(act)){
			delete(req,resp);
			search(req,resp);
		}else{
			search(req,resp);
		}
	}

	void search(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
			String bookName = req.getParameter("q_bookName");
			String author = req.getParameter("q_author");
			String sql = "select * from book where 1=1";
			if(bookName != null && !"".equals(bookName))sql += " and bookName like '%"+bookName+"%'";
			if(author != null && !"".equals(author))sql += " and author like '%"+author+"%'";
			List books = bookDao.search(sql);
			req.setAttribute("books", books);
			req.getRequestDispatcher("/javaweb/crud2/book_list.jsp").forward(req, resp);
	}
	void createForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		req.getRequestDispatcher("/javaweb/crud2/book_edit_form.jsp").forward(req, resp);
	}
	
	void editForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		long bookId = Long.parseLong(req.getParameter("id"));
		Book book = bookDao.get(bookId);
		req.setAttribute("book", book);
		req.getRequestDispatcher("/javaweb/crud2/book_edit_form.jsp").forward(req, resp);
	}

	void save(HttpServletRequest req, HttpServletResponse resp){
		//我们简单的认为传过来id了就是修改，否则就是新增
		String bookIdStr = req.getParameter("id");
		Long bookId = null;
		if(bookIdStr != null && !"".equals(bookIdStr)){
			bookId = Long.parseLong(req.getParameter("id"));
		}
		String bookName = req.getParameter("bookName");
		String author = req.getParameter("author");
		String priceStr = req.getParameter("price");
		Float price = null;
		if(priceStr != null && !"".equals(priceStr))price = Float.parseFloat(priceStr);
		
		String publicationDateStr = req.getParameter("publicationDate");
		Date publicationDate = null;
		if(publicationDateStr != null && !"".equals(publicationDateStr)){
			try {
				publicationDate = sdf.parse(publicationDateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		Book book = new Book();
		book.setId(bookId);
		book.setAuthor(author);
		book.setBookName(bookName);
		book.setPublicationDate(publicationDate);
		book.setPrice(price);
		
		//注意以上获取parameter和给book赋值的过程都是可以通过反射自动化的。
		
		try {
			if(book.getId() == null){
				bookDao.insert(book);
			}else{
				bookDao.update(book);
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}
	}
	
	void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		long bookId = Long.parseLong(req.getParameter("id"));
		bookDao.delete(bookId);
	}
}
