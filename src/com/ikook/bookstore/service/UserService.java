package com.ikook.bookstore.service;

import java.util.Iterator;
import java.util.Set;

import com.ikook.bookstore.dao.BookDao;
import com.ikook.bookstore.dao.TradeDao;
import com.ikook.bookstore.dao.TradeItemDao;
import com.ikook.bookstore.dao.UserDao;
import com.ikook.bookstore.dao.impl.BookDaoImpl;
import com.ikook.bookstore.dao.impl.TradeDaoImpl;
import com.ikook.bookstore.dao.impl.TradeItemDaoImpl;
import com.ikook.bookstore.dao.impl.UserDaoImpl;
import com.ikook.bookstore.domain.Trade;
import com.ikook.bookstore.domain.TradeItem;
import com.ikook.bookstore.domain.User;

public class UserService {
	
private UserDao userDao = new UserDaoImpl();
	
	public User getUserByUserName(String username){
		return userDao.getUser(username);
	}
	
	private TradeDao tradeDao = new TradeDaoImpl();
	private TradeItemDao tradeItemDao = new TradeItemDaoImpl();
	private BookDao bookDao = new BookDaoImpl();
	
	public User getUserWithTrades(String username){
	
		// 调用 UserDao 的方法获取 User 对象
		User user = userDao.getUser(username);
		if(user == null){
			return null;
		}
		
		// 调用 TradeDao 的方法获取 Trade 的集合，把其装配为 User 的属性
		int userId = user.getUserId();
		
		Set<Trade> trades = tradeDao.getTradesWithUserId(userId);
		
		if(trades != null){
			Iterator<Trade> tradeIt = trades.iterator();
			
			while(tradeIt.hasNext()){
				Trade trade = tradeIt.next();
				
				int tradeId = trade.getTradeId();
				Set<TradeItem> items = tradeItemDao.getTradeItemsWithTradeId(tradeId);
				
				if(items != null){
					for(TradeItem item: items){
						item.setBook(bookDao.getBook(item.getBookId())); 
					}
					
					if(items != null && items.size() != 0){
						trade.setItems(items);						
					}
				}
				
				if(items == null || items.size() == 0){
					tradeIt.remove();	
				}
				
			}
		}
		
		if(trades != null && trades.size() != 0){
			user.setTrades(trades);			
		}
		
		return user;
	}

}
