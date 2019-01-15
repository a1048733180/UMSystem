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

	/**
	 * 修改课程和专业对应的教师
	 * @param courseItem
	 * @return 受影响的行数 0：失败  1：成功
	 */
	int alertCourseItem(CourseItem courseItem);

	/**
	 * 增加新课程对应的教师和专业
	 * @param course
	 * @param teacher
	 * @param profession
	 */
	void insertCourseItemByCourse(Course course, Teacher teacher, Profession profession[]);

	/**
	 * 增加新教师对应的课程和专业
	 * @param courseItem
	 * @return 0：失败  1：成功
	 */
	int insertCourseItem(CourseItem courseItem);

	// 从教师id查找特定id的CoueseItem
	List<CourseItem> findCourseItemByTeacherId(int id);

	// 通过专业查找课程，这里输入的是专业id
	// 通过三元组中的行号（专业号）输出列号（课程号）
	List<Course> findCourseListByProfessionId(int professionId);

	/**
	 * 删除教师对应的全部专业和课程
	 * 这里不进行删除结果的判断 默认为成功删除（以后修改）
	 * @param teacherId
	 */
	void deleteCourseItemByTeacherId(int teacherId);

	/**
	 * 查找是否存在对应的专业和课程
	 * @param courseItem
	 * @return 1：成功  0：失败
	 */
	int selectProfessionAndCourse(CourseItem courseItem);

	/**
	 * 通过专业id删除对应的任课信息
	 * @param professionId
	 */
	void deleteCourseItemByProfessionId(int professionId);

	/**
	 * 通过课程id删除对应的任课信息
	 * @param courseId
	 */
	void deleteCourseItemByCourseId(int courseId);
}
