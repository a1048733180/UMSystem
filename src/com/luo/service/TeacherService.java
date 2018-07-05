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
	
	// 定义添加教师方法
	void insertTeacher(Teacher teacher);
	
	// 定义修改教师方法
	void alertTeacher(Teacher teacher);
	
	// 定义删除教师方法
	void deleteTeacher(Teacher teacher);
	
	// 分页获取教师列表
	String getTeacherListByPage(Page page);
}
