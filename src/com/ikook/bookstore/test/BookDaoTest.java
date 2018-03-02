package com.ikook.bookstore.test;

import org.junit.jupiter.api.Test;

import com.ikook.bookstore.dao.BookDao;
import com.ikook.bookstore.dao.impl.BookDaoImpl;
import com.ikook.bookstore.domain.Book;
import com.ikook.bookstore.web.CriteriaBook;
import com.ikook.bookstore.web.Page;

class BookDaoTest {
	
	private BookDao bookDao = new BookDaoImpl();

	@Test
	void testGetBook() {
		Book book = bookDao.getBook(5);
		System.out.println(book);
	}

	@Test
	void testGetPage() {
//		CriteriaBook criteriaBook = new CriteriaBook(0, Integer.MAX_VALUE, 3);
		CriteriaBook criteriaBook = new CriteriaBook(50, 60, 6);
		Page<Book> page = bookDao.getPage(criteriaBook);
		
		System.out.println("pageNo：" + page.getPageNo()); // 第几页
		System.out.println("totalPageNumber: " + page.getTotalPageNumber()); // 共多少页
		System.out.println("list: " + page.getList()); // 当前页的 List
		System.out.println("prevPage: " + page.getPrevPage()); // 上一页
		System.out.println("nextPage: " + page.getNextPage());  // 下一页
	}

	@Test
	void testGetStoreNumber() {
		int storeNumber = bookDao.getStoreNumber(5);
		System.out.println(storeNumber);
	}

}
