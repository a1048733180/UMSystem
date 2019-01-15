package com.luo.dao;

import com.luo.base.list.SeqList;
import com.luo.base.list.SortedCirDoublyList;
import com.luo.entity.Course;

import java.util.List;

/**
 * 课程管理接口
 * 
 * @author L99
 *
 */
public interface CourseDao {

	/**
	 * 增加课程
	 * @param course
	 * @return 受影响的行数
	 */
	int insertCourse(Course course);

	/**
	 * 通过课程id删除课程
	 * @param courseId
	 * @return 受影响的行数
	 */
	int deleteCourse(int courseId);

	// 修改课程
	void alertCourse(Course course);
	
	// 记录有多少门必修课和专业必修课（不包括选修课）
	int countCourse();

	/**
	 * 返回课程列表
	 */
	List<Course> getCourse();
	
	// 返回课程顺序表,方便直接获取元素
	List<Course> getCourseSeqList();
	
	// 通过课程id返回课程
	Course getCourseById(int id);
}
