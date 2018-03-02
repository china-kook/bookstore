package com.ikook.bookstore.test;

import java.sql.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.ikook.bookstore.dao.impl.BookDaoImpl;
import com.ikook.bookstore.domain.Book;

class BaseDaoTest {

	private BookDaoImpl bookDaoImpl = new BookDaoImpl();

	@Test
	void testInsert() {
		String sql = "insert into trade (userid, tradetime) values(?,?)";
		Long id = bookDaoImpl.insert(sql, 1, new Date(new java.util.Date().getTime()));

		System.out.println(id);
	}

	@Test
	void testUpdate() {
		String sql = "update mybooks set salesamount = ? where id = ?";
		bookDaoImpl.update(sql, 200, 4);
	}

	@Test
	void testQuery() {
		String sql = "SELECT id, author, title, price, publishingdate, "
				+ "salesamount, storenumber, remark FROM mybooks WHERE id = ?";
		Book book = bookDaoImpl.query(sql, 4);
		System.out.println(book);
		
//		String sql = "select itemid, quantity, bookid from tradeitem where itemid = ?";
//		TradeItem tradeItem = tradeItemDaoImpl.query(sql, 23);
//		System.out.println(tradeItem);
	}

	@Test
	void testQueryForList() {
		String sql = "SELECT id, author, title, price, publishingDate, "
				+ "salesAmount, storeNumber, remark FROM mybooks WHERE id < ?";
		List<Book> books = bookDaoImpl.queryForList(sql, 4);
		System.out.println(books);
	}

	@Test
	void testGetSingleVal() {
		String sql = "select count(id) from mybooks";
		long count = bookDaoImpl.getSingleVal(sql);
		System.out.println(count);
	}

	@Test
	void testBatch() {
		String sql = "UPDATE mybooks SET salesAmount = ?, storeNumber = ? " +
				"WHERE id = ?";
		
		bookDaoImpl.batch(sql, new Object[]{1, 1, 1}, new Object[]{2, 2, 2}, new Object[]{3, 3, 3}); 
	}

}
