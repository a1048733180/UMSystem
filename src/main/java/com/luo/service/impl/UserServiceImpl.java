package com.luo.service.impl;

import com.luo.dao.UserDao;
import com.luo.dao.impl.UserDaoImpl;
import com.luo.entity.User;
import com.luo.service.UserService;
import com.luo.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

/**
 * 实现用户操作接口实现类
 * 
 * @author L99
 *
 */
public class UserServiceImpl implements UserService {

	private SqlSession sqlSession;

	private SqlSession getSqlSession(){
		this.sqlSession = MyBatisUtil.getSqlSession();
		return this.sqlSession;
	}

	@Override
	public boolean getAdmin(User user) {
		boolean result = false;
		// 检验登陆账户密码是否正确
		try {
			UserDao userDao = getSqlSession().getMapper(UserDao.class);
			int result1 = userDao.getAdmin(user);

			if (result1 > 0) {
				result = true;
			}
		}catch (Exception e){
			sqlSession.close();
			throw e;
		}

		sqlSession.close();
		return result;
	}

}
