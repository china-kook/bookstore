package com.ikook.bookstore.dao.impl;

import java.util.LinkedHashSet;
import java.util.Set;

import com.ikook.bookstore.dao.TradeDao;
import com.ikook.bookstore.domain.Trade;

public class TradeDaoImpl extends BaseDao<Trade> implements TradeDao {

	@Override
	public void insert(Trade trade) {
		String sql = "insert into trade (userid, tradetime) VALUES (?, ?)";

		long tradeId = insert(sql, trade.getUserId(), trade.getTradeTime());
		trade.setTradeId((int)tradeId);
	}

	@Override
	public Set<Trade> getTradesWithUserId(Integer userId) {
		String sql = "SELECT tradeId, userId, tradeTime FROM trade " +
				"WHERE userId = ? ORDER BY tradeTime DESC";
		return new LinkedHashSet<>(queryForList(sql, userId));
	}

}
