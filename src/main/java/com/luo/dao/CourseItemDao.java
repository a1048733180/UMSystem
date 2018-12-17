package com.luo.dao;

import java.util.List;

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
	void alertCourseItem(CourseItem[] course,int teacherId);

	// 增加新课程对应的教师和专业
	void insertCourseItemByCourse(Course course, Teacher teacher, Profession profession[]);

	// 增加新教师对应的课程和专业
	void insertCourseItemByTeacher(Course course, Teacher teacher, Profession profession);

	// 从教师id查找特定id的CoueseItem
	List<CourseItem> findCourseItemByTeacherId(int id);

	// 通过专业查找课程，这里输入的是专业id
	// 通过三元组中的行号（专业号）输出列号（课程号）
	List<Course> findCourseListByProfessionId(int professionId);
	
	// 删除教师对应的全部专业和课程
	void deleteCourseItemByTeacherId(int teacherId);
}
