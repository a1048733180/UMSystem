package com.luo.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.luo.base.list.SortedSinglyList;
import com.luo.dao.ProfessionDao;
import com.luo.dao.impl.ProfessionDaoImpl;
import com.luo.entity.Profession;
import com.luo.service.ProfessionService;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

public class ProfessionServiceImpl implements ProfessionService {

	public ProfessionDao professionDao;

	public ProfessionServiceImpl() {
		this.professionDao = new ProfessionDaoImpl();
	}

	@Override
	public void insertProfession(Profession profession) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getProfessionList(Profession profession) {
		// 获取全部专业链表
		SortedSinglyList<Profession> professionList = professionDao.getProfessionList();

		// 存放经过筛选后的专业，如果没有筛选，则为全部专业
		List<Profession> professionTemp = new ArrayList<Profession>();

		for (int i = 0; i < professionList.size(); i++) {
			professionTemp.add(professionList.get(i));
		}
	
		JsonConfig config = new JsonConfig();		
		config.setExcludes(new String[] {"couseList"});
		String result = JSONArray.fromObject(professionTemp, config).toString();
		return result;
	}

	@Override
	public void alertProfession(Profession profession) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteProfession(Profession profession) {
		// TODO Auto-generated method stub

	}
}
