package com.ikook.bookstore.test;

import org.junit.jupiter.api.Test;

import com.ikook.bookstore.dao.UserDao;
import com.ikook.bookstore.dao.impl.UserDaoImpl;
import com.ikook.bookstore.domain.User;

class UserDaoTest {
	
	private UserDao userDao = new UserDaoImpl();

	@Test
	void testGetUser() {
		
		User user = userDao.getUser("AAA");
		System.out.println(user);
		
	}

}
