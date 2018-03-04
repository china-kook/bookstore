package com.ikook.bookstore.dao.impl;

import com.ikook.bookstore.dao.AccountDao;
import com.ikook.bookstore.domain.Account;

public class AccountDaoImpl extends BaseDao<Account> implements AccountDao {

	@Override
	public Account get(Integer accountId) {
		String sql = "select accountId, balance from account where accountId = ?";
		return query(sql, accountId);
	}

	@Override
	public void updateBalance(Integer accountId, float amount) {
		String sql = "update account set balance = balance - ? where accountId = ?";
		update(sql, amount, accountId); 
	}

}
