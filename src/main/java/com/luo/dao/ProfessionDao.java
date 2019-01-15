package com.luo.dao;

import com.luo.base.list.SortedCirDoublyList;
import com.luo.entity.Profession;

import java.util.List;

/**
 * 专业信息操作接口
 * 
 * @author L99
 *
 */
public interface ProfessionDao {

	/**
	 * 新增专业
	 * @param profession
	 */
	int insertProfession(Profession profession);
	
	//修改专业
	void alertProfessioin(Profession profession);

	/**
	 * 删除专业
	 * @param professionId
	 * @return 受影响的行数
	 */
	int deleteProfession(int professionId);
	
	// 记录专业数量
	int professionCount();

	/**
	 * 获取专业信息
	 * @return
	 */
	List<Profession> getProfession();
	
	// 通过专业id查找专业
	Profession findProfession(int professionId);

	/**
	 * 获取专业、课程信息
	 * @return
	 */
	List<Profession> getProfessionItem();
}
