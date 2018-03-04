package com.ikook.bookstore.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ikook.bookstore.dao.BookDao;
import com.ikook.bookstore.domain.Book;
import com.ikook.bookstore.domain.ShoppingCartItem;
import com.ikook.bookstore.web.CriteriaBook;
import com.ikook.bookstore.web.Page;

public class BookDaoImpl extends BaseDao<Book> implements BookDao {

	@Override
	public Book getBook(int id) {
		String sql = "SELECT id, author, title, price, publishingdate, "
				+ "salesamount, storenumber, remark FROM mybooks WHERE id = ?";

		return query(sql, id);
	}

	@Override
	public Page<Book> getPage(CriteriaBook criteriaBook) {
		Page<Book> page = new Page<>(criteriaBook.getPageNo());

		page.setTotalItemNumber(getTotalBookNumber(criteriaBook));

		// 检验 CriteriaBook 的 getPageNo 的合法性
		criteriaBook.setPageNo(page.getPageNo());

		page.setList(getPageList(criteriaBook, 3));
		return page;
	}

	@Override
	public long getTotalBookNumber(CriteriaBook criteriaBook) {
		String sql = "SELECT count(id) FROM mybooks WHERE price >= ? AND price <= ?";
		return getSingleVal(sql, criteriaBook.getMinPrice(), criteriaBook.getMaxPrice());
	}

	@Override
	public List<Book> getPageList(CriteriaBook criteriaBook, int pageSize) {
		// Mysql 使用 Limit 指定检索的行（限制结果），LIMIT 从零开始检索，也就是数据库的第一行数据是在零开始。
		String sql = "SELECT id, author, title, price, publishingdate, "
				+ "salesamount, storenumber, remark FROM mybooks WHERE price >= ? AND price <=? LIMIT ?, ?";

		return queryForList(sql, criteriaBook.getMinPrice(), criteriaBook.getMaxPrice(),
				(criteriaBook.getPageNo() - 1) * pageSize, pageSize);
	}

	@Override
	public int getStoreNumber(Integer id) {
		String sql = "select storeNumber from mybooks where id = ?";
		return getSingleVal(sql, id);
	}

	@Override
	public void batchUpdateStoreNumberAndSalesAmount(Collection<ShoppingCartItem> items) {
		String sql = "UPDATE mybooks SET salesAmount = salesAmount + ?, " +
				"storeNumber = storeNumber - ? " +
				"WHERE id = ?";
		
		Object [][] params = null;
		params = new Object[items.size()][3];
		List<ShoppingCartItem> scis = new ArrayList<>(items);		
		for(int i = 0; i < items.size(); i++){
			params[i][0] = scis.get(i).getQuantity();
			params[i][1] = scis.get(i).getQuantity();
			params[i][2] = scis.get(i).getBook().getId();
		}
		batch(sql, params);
	}

}
