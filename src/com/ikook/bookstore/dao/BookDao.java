package com.ikook.bookstore.dao;

import java.util.List;

import com.ikook.bookstore.domain.Book;
import com.ikook.bookstore.web.CriteriaBook;
import com.ikook.bookstore.web.Page;

/**
 * BookDao 接口，定义操作 Book 实体类的基本方法
 * @author ikook
 */
public interface BookDao {
	
	/**
	 * 根据 ID 获取指定的 Book 对象
	 * @param id
	 * @return
	 */
	public abstract Book getBook(int id);
	
	/**
	 * 根据传入的 CriteriaBook 对象返回对应的 Page 对象
	 * @param criteriaBook
	 * @return
	 */
	public abstract Page<Book> getPage(CriteriaBook criteriaBook);
	
	/**
	 * 根据传入的 CriteriaBook 对象返回其对象的记录数
	 * @param criteriaBook
	 * @return
	 */
	public abstract long getTotalBookNumber(CriteriaBook criteriaBook);
	
	/**
	 * 根据传入的 CriteriaBook 和 pageSize 返回当前页对应的 List
	 * @param criteriaBook
	 * @param pageSize
	 * @return
	 */
	public abstract List<Book> getPageList(CriteriaBook criteriaBook, int pageSize);
	
	/**
	 * 返回指定 id 的 book 的 storeNumber 字段的值
	 * @param id
	 * @return
	 */
	public abstract int getStoreNumber(Integer id);
	
//	public abstract void batchUpdateStoreNumberAndSalesAmount(Collection<Shopp>)

	

}
