package com.luo.service;

import com.luo.entity.ProfessionItem;

/**
 * 专业课程信息管理接口
 * 
 * @author L99
 *
 */
public interface ProfessionItemService {
	
	// 获取专业对应的课程信息
	void findCoursesByProfessionId(ProfessionItem professionItem);
}
