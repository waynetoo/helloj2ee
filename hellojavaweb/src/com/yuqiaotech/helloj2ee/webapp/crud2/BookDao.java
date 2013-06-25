package com.yuqiaotech.helloj2ee.webapp.crud2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
/**
 * book的dao类。
 * 各个方法一看即名，不再加注释了。
 * 本类在test下有单元测试类，可以看看。
 */
public class BookDao {
	public List<Book> search(String sql){
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			stat = conn.createStatement();
			rs = stat.executeQuery(sql);
			List list = new ArrayList();
			while(rs.next()){
				Book book = rsToBook(rs);
				list.add(book);
			}
			return list;
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
	/**
	 * 如果book有ID属性，则id的值为book的id值，负责数据库自动生成。
	 * @param book
	 */
	public void insert(Book book){
		Connection conn = null;
		PreparedStatement stat = null;
		try {
			conn = getConnection();
			String sql =  "insert into book (bookName,author,price,publicationDate) values (?,?,?,?)";
			if(book.getId() != null){
				sql =  "insert into book (id,bookName,author,price,publicationDate) values (?,?,?,?,?)";
			}
			stat = conn.prepareStatement(sql);
			int i = 1;
			if(book.getId() != null){
				stat.setLong(i++, book.getId());
			}
			stat.setString(i++, book.getBookName());
			stat.setString(i++, book.getAuthor());
			stat.setObject(i++, book.getPrice());
			java.sql.Date sqlDate = null;
			if(book.getPublicationDate() != null)
				sqlDate = new java.sql.Date(book.getPublicationDate().getTime());
			stat.setDate(i++, sqlDate);
			
			stat.execute();
			
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
	public void update(Book book) {
		Connection conn = null;
		PreparedStatement stat = null;
		try {
			conn = getConnection();
			String sql = "update book set bookName=?,author=?,price=?,publicationDate=? where id=?";
			stat = conn.prepareStatement(sql);
			int i = 1;
			
			stat.setString(i++, book.getBookName());
			stat.setString(i++, book.getAuthor());
			stat.setObject(i++, book.getPrice());
			java.sql.Date sqlDate = null;
			if(book.getPublicationDate() != null)
				sqlDate = new java.sql.Date(book.getPublicationDate().getTime());
			stat.setDate(i++, sqlDate);
			
			stat.setLong(i++, book.getId());
			stat.execute();
			
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
	public int delete(Long bookId){
		Connection conn = null;
		Statement stat = null;
		try {
			conn = getConnection();
			stat = conn.createStatement();
			int cnt = stat.executeUpdate("delete from book where id="+bookId);
			return cnt;
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
	public Book get(Long bookId){

		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			stat = conn.createStatement();
			rs = stat.executeQuery("select * from book where id="+bookId);
			rs.next();
			Book book = rsToBook(rs);
			return book;
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

	private Connection getConnection(){
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
	
	private Book rsToBook(ResultSet rs){
		Book book = new Book();
		try {
			book.setId(rs.getLong("id"));
			book.setBookName(rs.getString("bookname"));
			book.setPrice(rs.getFloat("price"));
			book.setAuthor(rs.getString("author"));
			book.setPublicationDate(rs.getDate("publicationDate"));
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(),e);
		}
		return book;
	}
}
