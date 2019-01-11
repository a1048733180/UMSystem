package com.luo.service.impl;

import com.luo.dao.ProfessionItemDao;
import com.luo.dao.ProfessionItemDao;
import com.luo.dao.impl.ProfessionItemDaoImpl;
import com.luo.entity.ProfessionItem;
import com.luo.service.ProfessionItemService;

/**
 * 专业课程信息操作实现
 * 
 * @author L99
 *
 */
public class ProfessionItemServiceImpl implements ProfessionItemService {

	ProfessionItemDao professionItemDao;

	public ProfessionItemServiceImpl() {
		this.professionItemDao = new ProfessionItemDaoImpl();
	}

	@Override
	public void findCoursesByProfessionId(ProfessionItem professionItem) {
		// 通过peofessionItem中的专业id获取专业要上的课程
		professionItemDao.findCourseByProfessionId(professionItem.getProfessionId());
	}
}
