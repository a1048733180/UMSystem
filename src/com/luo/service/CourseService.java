package com.luo.service;

import com.luo.entity.Course;
import com.luo.entity.Profession;

/**
 * 课程信息管理接口
 * 
 * @author L99
 *
 */
public interface CourseService {
	
	// 添加课程
	void insertCourse(Course course);
	
	// 获取全部课程集合
	String getCourseList(Profession profession);
	
	// 删除课程
	void deleteCourse(Course course);
}
