package com.luo.dao;

import com.luo.entity.Course;

/**
 * 课程管理接口
 * 
 * @author L99
 *
 */
public interface CourseDao {
	
	// 增加课程
	void insertCourse(Course course);

	// 删除课程
	void deleteCourse(Course course);

	// 修改课程
	void alertCourse(Course course);
	
	// 记录有多少门必修课和专业必修课（不包括选修课）
	int countCourse();
}
