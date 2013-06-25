package com.yuqiaotech.helloj2ee.webapp.crud;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BookAdmin extends HttpServlet{
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		try {
			String bookName = req.getParameter("q_bookName");
			String author = req.getParameter("q_author");
			String sql = "select * from book where 1=1";
			if(bookName != null && !"".equals(bookName))sql += " and bookName like '%"+bookName+"%'";
			if(author != null && !"".equals(author))sql += " and author like '%"+author+"%'";
			
			conn = getConnection();
			stat = conn.createStatement();
			rs = stat.executeQuery(sql);
			List list = new ArrayList();
			while(rs.next()){
				Map bookMap = rsToBookMap(rs);
				list.add(bookMap);
			}
			req.setAttribute("books", list);
			req.getRequestDispatcher("/javaweb/crud/book_list.jsp").forward(req, resp);
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(),e);
		}finally{
			if(rs != null)
				try {
					rs.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			if(stat != null)
				try {
					stat.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if(conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}
	void createForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		req.getRequestDispatcher("/javaweb/crud/book_edit_form.jsp").forward(req, resp);
	}
	
	void editForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		try {
			long bookId = Long.parseLong(req.getParameter("id"));
			conn = getConnection();
			stat = conn.createStatement();
			rs = stat.executeQuery("select * from book where id="+bookId);
			rs.next();
			Map bookMap = rsToBookMap(rs);
			req.setAttribute("book", bookMap);
			req.getRequestDispatcher("/javaweb/crud/book_edit_form.jsp").forward(req, resp);
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(),e);
		}finally{
			if(rs != null)
				try {
					rs.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			if(stat != null)
				try {
					stat.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if(conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}

	void save(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		//我们简单的认为传过来id了就是修改，否则就是新增
		String bookIdStr = req.getParameter("id");
		if(bookIdStr != null && !"".equals(bookIdStr)){
			update(req,resp);
		}else{
			insert(req,resp);
		}
	}
	
	void insert(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		Connection conn = null;
		PreparedStatement stat = null;
		try {
			String bookName = req.getParameter("bookName");
			String author = req.getParameter("author");
			String priceStr = req.getParameter("price");
			Float price = null;
			if(priceStr != null && !"".equals(priceStr))price = Float.parseFloat(priceStr);
			
			String publicationDateStr = req.getParameter("publicationDate");
			Date publicationDate = null;
			if(publicationDateStr != null && !"".equals(publicationDateStr)){
				publicationDate = sdf.parse(publicationDateStr);
			}
			conn = getConnection();
			String sql =  "insert into book (bookName,author,price,publicationDate) values (?,?,?,?)";
			stat = conn.prepareStatement(sql);
			int i = 1;
			
			stat.setString(i++, bookName);
			stat.setString(i++, author);
			stat.setObject(i++, price);
			java.sql.Date sqlDate = null;
			if(publicationDate != null) sqlDate= new java.sql.Date(publicationDate.getTime());
			stat.setDate(i++, sqlDate);
			stat.execute();
			
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(),e);
		} catch (ParseException e) {
			throw new RuntimeException(e.getMessage(),e);
		}finally{
			if(stat != null)
				try {
					stat.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if(conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}
	void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		Connection conn = null;
		PreparedStatement stat = null;
		try {
			String bookIdStr = req.getParameter("id");
			Long bookId = Long.parseLong(bookIdStr);
			
			String bookName = req.getParameter("bookName");
			String author = req.getParameter("author");
			String priceStr = req.getParameter("price");
			Float price = null;
			if(priceStr != null && !"".equals(priceStr))price = Float.parseFloat(priceStr);
			
			String publicationDateStr = req.getParameter("publicationDate");
			Date publicationDate = null;
			if(publicationDateStr != null && !"".equals(publicationDateStr)){
				publicationDate = sdf.parse(publicationDateStr);
			}
			conn = getConnection();
			String sql = "update book set bookName=?,author=?,price=?,publicationDate=? where id=?";
			stat = conn.prepareStatement(sql);
			int i = 1;
			
			stat.setString(i++, bookName);
			stat.setString(i++, author);
			stat.setObject(i++, price);
			
			java.sql.Date sqlDate = null;
			if(publicationDate != null) sqlDate= new java.sql.Date(publicationDate.getTime());
			stat.setDate(i++, sqlDate);
			
			stat.setLong(i++, bookId);
			stat.execute();
			
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(),e);
		} catch (ParseException e) {
			throw new RuntimeException(e.getMessage(),e);
		}finally{
			if(stat != null)
				try {
					stat.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if(conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}

	void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		Connection conn = null;
		Statement stat = null;
		try {
			long bookId = Long.parseLong(req.getParameter("id"));
			conn = getConnection();
			stat = conn.createStatement();
			int cnt = stat.executeUpdate("delete from book where id="+bookId);
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(),e);
		}finally{
			if(stat != null)
				try {
					stat.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if(conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}
	
	Connection getConnection(){
		try{
			String DriverName = "org.gjt.mm.mysql.Driver";
			String dbURL = "jdbc:mysql://localhost/helloj2ee?useUnicode=true&characterEncoding=GBK";
			String dbuser = "root";
			String dbpassword = "";
			Class.forName(DriverName).newInstance();
			Connection conn = DriverManager.getConnection(dbURL, dbuser, dbpassword);
			return conn;
		}catch(Exception e){
			throw new RuntimeException(e.getMessage(),e);
		}
	}
	
	Map rsToBookMap(ResultSet rs){
		Map m = new HashMap();
		try {
			m.put("id", rs.getLong("id"));
			m.put("bookName", rs.getString("bookname"));
			m.put("author", rs.getString("author"));
			m.put("price", rs.getObject("price"));
			m.put("publicationDate", rs.getDate("publicationDate"));
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(),e);
		}
		return m;
	}
	
}
