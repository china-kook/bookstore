package com.ikook.bookstore.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.ikook.bookstore.dao.TradeItemDao;
import com.ikook.bookstore.dao.impl.TradeItemDaoImpl;
import com.ikook.bookstore.domain.TradeItem;

class TradeItemDaoTest {


	private TradeItemDao tradeItemDao = new TradeItemDaoImpl();
	
	@Test
	public void testBatchSave() {
		Collection<TradeItem> items = new ArrayList<>();
		
		items.add(new TradeItem(null, 1, 10, 23));
		items.add(new TradeItem(null, 2, 20, 23));
		items.add(new TradeItem(null, 3, 30, 23));
		items.add(new TradeItem(null, 4, 40, 23));
		items.add(new TradeItem(null, 5, 50, 23));
		
		tradeItemDao.batchSave(items);
	}

	@Test
	public void testGetTradeItemsWithTradeId() {
		Set<TradeItem> items = tradeItemDao.getTradeItemsWithTradeId(23);
		System.out.println(items);
	}
}
