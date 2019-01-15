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

	/**
	 * 通过课程id删除对应的教师任课信息 <-（课程的删除功能）
	 * 非严格删除，无法得知是否删除成功
	 * （可以通过先查询数据库中得出符合条件的数量 再与删除受影响的行数比较是否相等来判断）
	 * @param courseId
	 */
	void deleteCourseItemByCourseId(int courseId);
}
