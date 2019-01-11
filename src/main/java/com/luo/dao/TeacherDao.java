package com.luo.dao;

import com.luo.base.list.SeqList;
import com.luo.base.list.SortedCirDoublyList;
import com.luo.entity.Teacher;

import java.util.List;

/**
 * 教师信息操作接口
 * 
 * @author L99
 *
 */
public interface TeacherDao {

	/**
	 * 添加教师信息
	 * @param teacher 新增的教师信息
	 * @return 0：失败 1：成功
	 */
	int insertTeacher(Teacher teacher);

	/**
	 * 修改教师信息
	 * @param teacher 需要修改的教师信息
	 * @return 0：失败 1：成功
	 */
	int alertTeacher(Teacher teacher);

	/**
	 * 删除指定教师
	 * @param teacher
	 * @return 0:失败 1：成功
	 */
	int deleteTeacher(Teacher teacher);

	/**
	 * 获取教师
	 * @return 教师列表
	 */
	List<Teacher> getTeacher();
}
