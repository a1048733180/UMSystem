package com.luo.service;

import com.luo.entity.ProfessionItem;

import java.util.List;

/**
 * 专业课程信息管理接口
 * 
 * @author L99
 *
 */
public interface ProfessionItemService {

	/**
	 * 获取专业对应的课程信息
	 * @param professionItem
	 */
	void findCoursesByProfessionId(ProfessionItem professionItem);

	void insertCourseForProfession(List<ProfessionItem> list);

	/**
	 * 通过课程id删除专业对应的课程信息 <- （课程中删除功能）
	 * 非严格删除，无法得知是否删除成功
	 *（可以通过先查询数据库中得出符合条件的数量 再与删除受影响的行数比较是否相等来判断）
	 * @param courseId
	 */
	void deleteProfessionItemByCourseId(int courseId);
}
