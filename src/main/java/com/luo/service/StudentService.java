package com.luo.service;

import com.luo.entity.Page;
import com.luo.entity.Student;

/**
 * 学生信息处理接口
 * @author L99
 *
 */
public interface StudentService {

	/**
	 * 添加学生方法
	 * @param student
	 * @return
	 */
	int insertStudent(Student student);

	/**
	 * 删除学生(根据Id)方法
	 * @param id
	 */
	void deleteStudent(long id);


	/**
	 * 定义修改学生方法
	 * @param student
	 */
	void alertStudent(Student student);

	/**
	 * 分页获取学生
	 * @param student
	 * @param page
	 * @return
	 */
	 String getStudentListByPage(Student student, Page page);

}
