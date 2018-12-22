package com.luo.service;

import com.luo.entity.User;

/**
 * 实现用户操作接口
 * 
 * @author L99
 *
 */
public interface UserService {

	// 登陆检验
	boolean getAdmin(User user);
}
