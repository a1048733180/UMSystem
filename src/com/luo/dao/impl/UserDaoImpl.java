package com.luo.dao.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import com.luo.base.list.SinglyList;
import com.luo.dao.UserDao;
import com.luo.entity.User;

/**
 * 登陆用户操作接口实现类
 * 
 * @author L99
 *
 */
public class UserDaoImpl implements UserDao {

	SinglyList<User> userList = new SinglyList<>();

	// 建立一个文件类(默认为管理员登陆表)
	File file = new File("F:\\JAVA项目\\UMSystem\\文件表\\管理员登录表.txt");

	public File alertFileLocation(int type) {
		if (type == 2) {
			file = new File("F:\\JAVA项目\\UMSystem\\文件表\\用户登录表.txt");
		} else if (type == 3) {
			file = new File("F:\\JAVA项目\\UMSystem\\文件表\\教师登录表.txt");
		}
		return file;
	}

	// 获取本地文件
	// 有三个用户文件，分别是管理员登录表、学生登陆表、教师登录表
	public void readContent(int type) {
		// 先根据type查看是要查询哪个表
		File realFile = alertFileLocation(type);
		String thisLine;
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(realFile), "UTF-8"));
			while ((thisLine = in.readLine()) != null) {
				String[] str = thisLine.split("/");
				User user = new User();
				user.setId(Integer.valueOf(str[0]));
				user.setAccount(str[1]);
				user.setPassword(str[2]);
				user.setName(str[3]);
				user.setType(type);
				userList.insert(user);
			}
			in.close();

		} catch (IOException e) {
			System.out.println("读取文件失败" + e);
		}
	}

	public SinglyList<User> getUserList(){		
		return this.userList;
	}
	
	@Override
	public User getAdmin(User user) {
		// TODO Auto-generated method stub
		readContent(user.getType());
		
		return this.getUserList().equalsElements(user);
	}
}
