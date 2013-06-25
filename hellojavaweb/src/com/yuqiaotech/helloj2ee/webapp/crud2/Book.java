package com.yuqiaotech.helloj2ee.webapp.crud2;

import java.util.Date;
/**
 * 书籍。
 */
public class Book {

	private Long id;
	private String bookName;
	private String author;
	private Float price;
	private Date publicationDate;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 书名。
	 * @return
	 */
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	/**
	 * 作者。
	 * @return
	 */
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	/**
	 * 出版日期。
	 * @return
	 */
	public Date getPublicationDate() {
		return publicationDate;
	}
	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}
	/**
	 * 价格。
	 * @return
	 */
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	
}
