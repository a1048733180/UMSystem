package com.luo.dao;

import com.luo.base.list.SortedCirDoublyList;
import com.luo.entity.Profession;

/**
 * 专业信息操作接口
 * 
 * @author L99
 *
 */
public interface ProfessionDao {

	//新增专业
	void insertProfession(Profession profession);
	
	//修改专业
	void alertProfessioin(Profession profession);
	
	// 删除专业
	void deleteProfession(Profession profession);
	
	// 记录专业数量
	int professionCount();
	
	// 返回专业列表
	SortedCirDoublyList<Profession> getProfessionList();
	
	// 通过专业id查找专业
	Profession findProfession(int professionId);
}
