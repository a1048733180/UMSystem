package com.luo.dao;

import com.luo.base.list.SeqList;
import com.luo.base.list.SortedCirDoublyList;
import com.luo.entity.Teacher;

/**
 * 教师信息操作接口
 * 
 * @author L99
 *
 */
public interface TeacherDao {

	// 添加教师
	void insertTeacher(Teacher teacher);

	// 修改教师
	void alertTeacher(Teacher teacher);

	// 删除教师
	void deleteTeacher(Teacher teacher);

	// 获取教师列表
	SortedCirDoublyList<Teacher> getTeacherList();
	
	// 获取教师列表数组
	SeqList<Teacher> getTeacherSeqList();
}
