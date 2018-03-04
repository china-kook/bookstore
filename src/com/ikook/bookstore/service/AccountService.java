package com.ikook.bookstore.service;

import com.ikook.bookstore.dao.AccountDao;
import com.ikook.bookstore.dao.impl.AccountDaoImpl;
import com.ikook.bookstore.domain.Account;

public class AccountService {

	private AccountDao accountDao = new AccountDaoImpl();

	public Account getAccount(int accountId){
		return accountDao.get(accountId);
	}

}
