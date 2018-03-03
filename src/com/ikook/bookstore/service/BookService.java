package com.ikook.bookstore.service;

import com.ikook.bookstore.dao.BookDao;
import com.ikook.bookstore.dao.impl.BookDaoImpl;
import com.ikook.bookstore.domain.Book;
import com.ikook.bookstore.domain.ShoppingCart;
import com.ikook.bookstore.web.CriteriaBook;
import com.ikook.bookstore.web.Page;

public class BookService {

	private BookDao bookDao = new BookDaoImpl();

	public Page<Book> getPage(CriteriaBook criteriaBook) {
		return bookDao.getPage(criteriaBook);
	};

	public Book getBook(int id) {
		return bookDao.getBook(id);
	}

	public boolean addToCart(int id, ShoppingCart shoppingCart) {

		Book book = bookDao.getBook(id);

		if (book != null) {
			shoppingCart.addBook(book);
			return true;
		} else {
			return false;
		}
	}

	public void removeItemFromShoppingCart(ShoppingCart cart, int id) {
		cart.removeItem(id);
	}

	public void clearShoppingCart(ShoppingCart shoppingCart) {
		shoppingCart.clear();

	}

	public void updateItemQuantity(ShoppingCart shoppingCart, int id, int quantity) {
		shoppingCart.updateItemQuantity(id, quantity);
	};

}
