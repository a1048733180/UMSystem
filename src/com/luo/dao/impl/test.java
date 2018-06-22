package com.luo.dao.impl;

import com.luo.entity.Profession;
import com.luo.entity.Student;

public class test {
	public static void main(String[] args) {

		StudentDaoImpl stuDao = new StudentDaoImpl();
		// System.out.println(stuDao.studentList.toString());
		// System.out.println(stuDao.selectStudentById(311600424).toString());
		Student stu = new Student(311600421);
		stu.setAge(19);
		stu.setName("刘维特");
		Profession pro = new Profession();
		pro.setName("信息管理与信息系统");
		pro.setId(2);
		stu.setProfession(pro);
		stu.setSex("男");
		stu.setProfessionId(2);
		stu.setIntroduce("无");

		stuDao.alertStudent(stu);
		System.out.println(stuDao.selectStudentById(311600421).toString());
	}
}
