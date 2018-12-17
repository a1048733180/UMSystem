package com.luo.dao;

import com.luo.base.list.SeqList;
import com.luo.base.list.SortedCirDoublyList;
import com.luo.entity.Student;

/**
 * 学生信息操作接口
 * 
 * @author L99
 *
 */
public interface StudentDao {

	// 增加学生
	void insertStudent(Student student);

	// 删除学生(根据学生id)
	void deleteStudent(int id);

	// 通过学生id查询学生
	Student selectStudentById(int id);

	// 通过学生姓名查询学生

	// 修改学生信息
	void alertStudent(Student student);

	// 获取学生列表
	SortedCirDoublyList<Student> getStudentList();

	// 获取学生列表数组
	SeqList<Student> getStudentSeqList();

	// 把学生链表转化成学生顺序表
	void changeToSeqList();
}
