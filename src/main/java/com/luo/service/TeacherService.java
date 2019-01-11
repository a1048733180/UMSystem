package com.luo.service;

import com.luo.entity.Page;
import com.luo.entity.Teacher;

/**
 * 教师信息处理接口
 * 
 * @author L99
 *
 */
public interface TeacherService {

	/**
	 * 增加教师信息
	 * @param teacher
	 * @return true：添加成功  false: 添加失败
	 * @throws Exception
	 */
	boolean insertTeacher(Teacher teacher) throws Exception;

	/**
	 * 修改教师信息
	 * @param teacher
	 * @return true：修改成功  false: 修改失败
	 */
	boolean alertTeacher(Teacher teacher);
	
	// 定义删除教师方法
	boolean deleteTeacher(Teacher teacher);
	
	// 分页获取教师列表
	String getTeacherListByPage(Page page);
}
