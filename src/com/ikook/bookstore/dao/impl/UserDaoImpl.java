package com.ikook.bookstore.dao.impl;

import com.ikook.bookstore.dao.UserDao;
import com.ikook.bookstore.domain.User;

public class UserDaoImpl extends BaseDao<User> implements UserDao {

	@Override
	public User getUser(String username) {
		String sql = "SELECT userId, username, accountId FROM userinfo WHERE username = ?";
		return query(sql, username);
	}

}
