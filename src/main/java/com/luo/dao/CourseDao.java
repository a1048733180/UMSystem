package com.luo.dao;

import com.luo.base.list.SeqList;
import com.luo.base.list.SortedCirDoublyList;
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
	
	// 返回课程列表
	SortedCirDoublyList<Course> getCourseList();
	
	// 返回课程顺序表,方便直接获取元素
	SeqList<Course> getCourseSeqList();
	
	// 通过课程id返回课程
	Course getCourseById(int id);
}
