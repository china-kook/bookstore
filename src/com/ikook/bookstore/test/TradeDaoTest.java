package com.ikook.bookstore.test;

import java.sql.Date;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.ikook.bookstore.dao.TradeDao;
import com.ikook.bookstore.dao.impl.TradeDaoImpl;
import com.ikook.bookstore.domain.Trade;

class TradeDaoTest {

	private TradeDao tradeDao = new TradeDaoImpl();

	@Test
	public void testInsertTrade() {
		Trade trade = new Trade();
		trade.setUserId(2);
		trade.setTradeTime(new Date(new java.util.Date().getTime()));

		tradeDao.insert(trade);
	}

	@Test
	public void testGetTradesWithUserId() {
		Set<Trade> trades = tradeDao.getTradesWithUserId(1);
		System.out.println(trades);
	}

}
