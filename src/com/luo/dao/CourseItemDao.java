package com.luo.dao;

import com.luo.entity.Course;
import com.luo.entity.CourseItem;
import com.luo.entity.Profession;
import com.luo.entity.Teacher;

/**
 * 课程教师专业对应信息管理接口
 * 
 * @author L99
 *
 */
public interface CourseItemDao {
	
	// 修改课程和专业对应的教师
	void alertCourseItem(CourseItem course);
	
	// 增加新课程
	void insertCourse(Course course,Teacher teacher,Profession[] profession);
}
