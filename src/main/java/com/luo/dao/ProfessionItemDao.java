package com.luo.dao;

import java.util.List;

import com.luo.entity.Course;
import com.luo.entity.Profession;
import com.luo.entity.ProfessionItem;

/**
 * 专业对应课程信息操作接口
 * 
 * @author L99
 *
 */
public interface ProfessionItemDao {
	
	// 通过专业查找课程，这里输入的是专业id
	List<Course> findCourseByProfessionId(int professionId);

	/**
	 * 插入专业时设置专业对应的课程信息
	 * @param professionItem
	 */
	void insertCourseForProfession(ProfessionItem professionItem);


	List<Profession> getProfessionItem();

	/**
	 * 通过专业id删除专业对应课程信息
	 * @param professionId
	 */
	void deleteProfessionItemByProfessionId(int professionId);

	/**
	 * 通过课程id删除专业对应的课程信息
	 * @param courseId
	 */
	void deleteProfessionItemByCourseId(int courseId);
}
