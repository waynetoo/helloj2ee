package com.yuqiaotech.helloj2ee.test;

import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import com.yuqiaotech.helloj2ee.webapp.crud2.Book;
import com.yuqiaotech.helloj2ee.webapp.crud2.BookDao;
/**
 * BookDao的单元测试。
 */
public class BookDaoTest extends TestCase{
	BookDao bookDao;
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		bookDao = new BookDao();
	}
	/**
	 * 测试删除、新增、修改、查询等方法。
	 */
	public void testCRUD(){
		bookDao.delete(1L);
		Book b = new Book();
		b.setId(1L);
		b.setAuthor("Tom");
		b.setBookName("Hello Jee");
		b.setPrice(10.5f);
		b.setPublicationDate(new Date());
		bookDao.insert(b);
		Book b2 = bookDao.get(1L);
		assertEquals(b.getBookName(), b2.getBookName());
		String newBookName = "Hello SSH";
		b2.setBookName(newBookName);
		bookDao.update(b2);
		List<Book> books = bookDao.search("select * from book where bookname='"+newBookName+"'");
		assertTrue(books.size()>0);
		for (Book book : books) {
			if(book.getId() == 1){
				assertEquals(b2.getBookName(), book.getBookName());
			}
		}
		int cnt = bookDao.delete(1L);
		assertEquals(1, cnt);
	}
}
