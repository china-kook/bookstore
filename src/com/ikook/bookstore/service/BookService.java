package com.ikook.bookstore.service;

import com.ikook.bookstore.dao.BookDao;
import com.ikook.bookstore.dao.impl.BookDaoImpl;
import com.ikook.bookstore.domain.Book;
import com.ikook.bookstore.web.CriteriaBook;
import com.ikook.bookstore.web.Page;

public class BookService {
	
	private BookDao bookDao = new BookDaoImpl();
	
	public Page<Book> getPage(CriteriaBook criteriaBook){
		return bookDao.getPage(criteriaBook);
	};
	
	public Book getBook(int id) {
		return bookDao.getBook(id);
	};

}
