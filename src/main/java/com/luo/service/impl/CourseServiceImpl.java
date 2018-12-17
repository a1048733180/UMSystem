package com.luo.service.impl;

import com.luo.base.list.SeqList;
import com.luo.dao.CourseDao;
import com.luo.dao.ProfessionItemDao;
import com.luo.dao.impl.CourseDaoImpl;
import com.luo.dao.impl.ProfessionItemDaoImpl;
import java.util.ArrayList;
import java.util.List;

import com.luo.entity.Course;
import com.luo.entity.Profession;
import com.luo.service.CourseService;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

public class CourseServiceImpl implements CourseService {

	CourseDao courseDao;

	public CourseServiceImpl() {
		this.courseDao = new CourseDaoImpl();
	}

	@Override
	public void insertCourse(Course course) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteCourse(Course course) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getCourseList(Profession profession) {
		// 获取课程列表

		// 建立一个课程集合，方便json化
		List<Course> courseTemp = new ArrayList<Course>();

		// 获取全部课程
		if (profession == null) {
			SeqList<Course> courseList = courseDao.getCourseSeqList();

			for (int i = 0; i < courseList.size(); i++) {
				courseTemp.add(courseList.get(i));
			}
		} else {
//			CourseItemDao courseItem = new CourseItemDaoImpl();
//			courseTemp = courseItem.findCourseListByProfessionId(profession.getId());
			ProfessionItemDao professionItem = new ProfessionItemDaoImpl();
			courseTemp = professionItem.findCourseListByProfessionId(profession.getId());
		}
		
		JsonConfig config = new JsonConfig();
		config.setExcludes(new String[] { "isRequired", "isMajorRequired", "isOptional" });
		String result = JSONArray.fromObject(courseTemp, config).toString();
		return result;

	}
}
