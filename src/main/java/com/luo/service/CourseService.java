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

	/**
	 * 添加课程
	 * @param course
	 * @return
	 */
	boolean insertCourse(Course course);
	
	// 获取全部课程集合
	String getCourseList(Profession profession);

	/**
	 * 删除课程
	 * @param course
	 * @return true:成功  false：失败
	 */
	boolean deleteCourse(Course course);
}
