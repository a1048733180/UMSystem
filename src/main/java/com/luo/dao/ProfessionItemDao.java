package com.luo.dao;

import java.util.List;

import com.luo.entity.Course;

/**
 * 专业对应课程信息操作接口
 * 
 * @author L99
 *
 */
public interface ProfessionItemDao {
	
	// 通过专业查找课程，这里输入的是专业id
	List<Course> findCourseByProfessionId(int professionId);
}
