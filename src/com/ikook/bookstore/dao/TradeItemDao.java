package com.ikook.bookstore.dao;

import java.util.Collection;
import java.util.Set;

import com.ikook.bookstore.domain.TradeItem;

public interface TradeItemDao {
	
	/**
	 * 批量保存 TradeItem 对象
	 * @param items
	 */
	public abstract void batchSave(Collection<TradeItem> items);
	
	/**
	 * 根据 tradeId 获取和其关联的 TradeItem 集合
	 * @param tradeId
	 * @return
	 */
	public abstract Set<TradeItem> geTradeItemsWithTradeId(Integer tradeId);

}