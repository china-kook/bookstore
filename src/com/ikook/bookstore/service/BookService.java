package com.ikook.bookstore.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

import com.ikook.bookstore.dao.AccountDao;
import com.ikook.bookstore.dao.BookDao;
import com.ikook.bookstore.dao.TradeDao;
import com.ikook.bookstore.dao.TradeItemDao;
import com.ikook.bookstore.dao.UserDao;
import com.ikook.bookstore.dao.impl.AccountDaoImpl;
import com.ikook.bookstore.dao.impl.BookDaoImpl;
import com.ikook.bookstore.dao.impl.TradeDaoImpl;
import com.ikook.bookstore.dao.impl.TradeItemDaoImpl;
import com.ikook.bookstore.dao.impl.UserDaoImpl;
import com.ikook.bookstore.domain.Book;
import com.ikook.bookstore.domain.ShoppingCart;
import com.ikook.bookstore.domain.ShoppingCartItem;
import com.ikook.bookstore.domain.Trade;
import com.ikook.bookstore.domain.TradeItem;
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
	}

	private AccountDao accountDao = new AccountDaoImpl();
	private TradeDao tradeDao = new TradeDaoImpl();
	private UserDao userDao = new UserDaoImpl();
	private TradeItemDao tradeItemDao = new TradeItemDaoImpl();

	public void cash(ShoppingCart shoppingCart, String username, String accountId) {
		// 1. 更新 mybooks 数据表相关记录的 salesamount 和 storenumber
		bookDao.batchUpdateStoreNumberAndSalesAmount(shoppingCart.getItems());

		// 2. 更新 account 数据表的 balance
		accountDao.updateBalance(Integer.parseInt(accountId), shoppingCart.getTotalMoney());

		// 3. 向 trade 数据表插入一条记录
		Trade trade = new Trade();
		trade.setTradeTime(new Date(new java.util.Date().getTime()));
		trade.setUserId(userDao.getUser(username).getUserId());
		tradeDao.insert(trade);

		// 4. 向 tradeitem 数据表插入 n 条记录
		Collection<TradeItem> items = new ArrayList<>();
		for (ShoppingCartItem sci : shoppingCart.getItems()) {
			TradeItem tradeItem = new TradeItem();

			tradeItem.setBookId(sci.getBook().getId());
			tradeItem.setQuantity(sci.getQuantity());
			tradeItem.setTradeId(trade.getTradeId());

			items.add(tradeItem);
		}
		tradeItemDao.batchSave(items);

		// 5. 清空购物车
		shoppingCart.clear();

	};

}
