package com.luo.service;

import com.luo.entity.Profession;

/**
 * 专业信息处理接口
 * 
 * @author L99
 *
 */
public interface ProfessionService {
	
	// 添加专业
	void insertProfession(Profession profession);
	
	// 删除专业
	void deleteProfession(Profession profession);
	
	// 修改专业
	void alertProfession(Profession profession);
	
	// 返回专业列表
	String getProfessionList(String course);
	
}
