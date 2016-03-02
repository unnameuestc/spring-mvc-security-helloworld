package com.wzhang.dao;

import com.wzhang.domain.UserBean;

public interface UserDao {
	UserBean getUser(String userName);
}
