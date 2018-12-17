package com.luo.service;

import com.luo.entity.Page;
import com.luo.entity.Student;

/**
 * 学生信息处理接口
 * @author L99
 *
 */
public interface StudentService {
	
	// 定义添加学生方法
	void insertStudent(Student student);
	
	// 定义删除学生(根据Id)方法
	void deleteStudent(int id);
	
	// 定义查询学生方法
	Student selectStudentById(int id);
	
	// 定义修改学生方法
	void alertStudent(Student student);
	
	// 分页获取学生
	 String getStudentListByPage(Student student, Page page);
}
