package com.luo.dao;

import com.luo.entity.User;

/**
 * 登陆用户操作接口
 * 
 * @author L99
 *
 */
public interface UserDao {

	// 判断用户是否在文件中存在
	boolean getAdmin(User user);
}
