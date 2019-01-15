package com.luo.dao;

import com.luo.entity.User;

/**
 * 登陆用户操作接口
 * 
 * @author L99
 *
 */
public interface UserDao {

	/**
	 * 判断管理员用户密码
	 * @param user
	 * @return 受影响的行数
	 */
	int getAdmin(User user);
}
