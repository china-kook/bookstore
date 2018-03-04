package com.ikook.bookstore.dao;

import java.util.Set;

import com.ikook.bookstore.domain.Trade;

public interface TradeDao {
	
	/**
	 * 向数据表中插入 Trade 对象
	 * @param trade
	 */
	public abstract void insert(Trade trade);
	
	/**
	 * 根据 userId 获取和其关联的 Trade 集合
	 * @param userId
	 * @return
	 */
	public abstract Set<Trade> getTradesWithUserId(Integer userId);

}
