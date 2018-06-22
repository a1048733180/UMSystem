package com.luo.service.impl;

import com.luo.dao.UserDao;
import com.luo.dao.impl.UserDaoImpl;
import com.luo.entity.User;
import com.luo.service.UserService;

/**
 * 实现用户操作接口实现类
 * 
 * @author L99
 *
 */
public class UserServiceImpl implements UserService {

	UserDao userDao;

	public UserServiceImpl() {
		userDao = new UserDaoImpl();
	}

	@Override
	public User getAdmin(User user) {
		// 检验登陆账户密码是否正确
		return userDao.getAdmin(user);
	}

}
