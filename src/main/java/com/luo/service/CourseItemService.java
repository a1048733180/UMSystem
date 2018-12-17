package com.luo.service;

import com.luo.entity.CourseItem;

/**
 * 专业课程教师对应信息接口处理
 * 
 * @author L99
 *
 */
public interface CourseItemService {
	// 新添加课程教师专业对应对象
	void insertCourseItem(CourseItem courseItem);
	
	// 修改课程教师专业对应对象
	void alertCourseItem(CourseItem[] courses,int teacherId);
	
	// 删除课程教师专业对应对象
	void deleteCourseItem(int teacherId);
}
