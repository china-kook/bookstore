package com.ikook.bookstore.test;

import org.junit.jupiter.api.Test;

import com.ikook.bookstore.dao.AccountDao;
import com.ikook.bookstore.dao.impl.AccountDaoImpl;
import com.ikook.bookstore.domain.Account;

class AccountDaoTest {
	
 	private AccountDao accountDao = new AccountDaoImpl();

	@Test
	void testGet() {
		
		Account account = accountDao.get(1);
		System.out.println(account);
	}

	@Test
	void testUpdateBalance() {
		
		accountDao.updateBalance(1, 50);
		
	}

}
