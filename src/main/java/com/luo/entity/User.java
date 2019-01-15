package com.luo.entity;

/**
 * 系统用户类
 * 
 * @author L99
 *
 */
public class User {

	/*
	 * 管理员为1
	 */
	public static final int USER_ADMIN = 1;

	/*
	 * 学生为2
	 */
	public static final int USER_STUDENT = 2;

	/*
	 * 教师为3
	 */
	public static final int USER_TEACHER = 3;

	private int id; // ID

	/**
	 * 用户名
	 */
	private String userName;

	private String password = "111111"; // 密码：默认'111111'

	private String name; // 用户姓名

	private int type = USER_ADMIN; // 账户类型：默认1为管理员；1为管理员，2为学生，3为教师

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}

	
	
	
}
