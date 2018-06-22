package com.luo.service.impl;

import com.luo.entity.Page;
import com.luo.entity.Student;
import com.luo.service.StudentService;

public class text {
	public static void main(String[] args) {
		StudentService studentService = new StudentServiceImpl();
		Student stu = new Student();
		stu.setProfessionId(3);
		Page page = new Page(1, 10);

		String result = studentService.getStudentListByPage(stu, page);
		System.out.println(result);
	}
}
