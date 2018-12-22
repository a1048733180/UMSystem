package com.luo.dao;

import com.luo.base.list.SeqList;
import com.luo.base.list.SortedCirDoublyList;
import com.luo.entity.Student;

import java.util.List;

/**
 * 学生信息操作接口
 * 
 * @author L99
 *
 */
public interface StudentDao {

	/**
	 * 增加学生
	 * @param student
	 */
	void insertStudent(Student student);

	/**
	 *  删除学生 根据学生id
	 * @param id
	 */
	void deleteStudent(long id);

	/**
	 * 通过学生id查询学生
	 * @param id
	 * @return
	 */
	Student selectStudentById(long id);


	/**
	 *  修改学生信息
	 * @param student
	 */
	void alertStudent(Student student);

	// 获取学生列表
	// SortedCirDoublyList<Student> getStudentList();

	/**
	 * 获取学生列表
	 * @return
	 */
	List<Student> getStudent();



}
