package com.ikook.bookstore.dao.impl;

import java.util.Collection;
import java.util.Set;

import com.ikook.bookstore.dao.TradeItemDao;
import com.ikook.bookstore.domain.TradeItem;

public class TradeItemDaoImpl extends BaseDao<TradeItem> implements TradeItemDao{

	@Override
	public void batchSave(Collection<TradeItem> items) {
		
		
		
	}

	@Override
	public Set<TradeItem> geTradeItemsWithTradeId(Integer tradeId) {
		// TODO Auto-generated method stub
		return null;
	}

}
