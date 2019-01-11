package com.luo.service.impl;

import com.luo.dao.CourseDao;
import com.luo.dao.ProfessionItemDao;
import com.luo.dao.impl.CourseDaoImpl;
import java.util.ArrayList;
import java.util.List;

import com.luo.entity.Course;
import com.luo.entity.Profession;
import com.luo.service.CourseService;

import com.luo.util.MyBatisUtil;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.apache.ibatis.session.SqlSession;

public class CourseServiceImpl implements CourseService {

	private SqlSession sqlSession;

	public SqlSession getSqlSession() {
		sqlSession = MyBatisUtil.getSqlSession();
		return sqlSession;
	}

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
			CourseDao courseDao = getSqlSession().getMapper(CourseDao.class);
			List<Course> courseList = courseDao.getCourse();
			for (int i = 0; i < courseList.size(); i++) {
				courseTemp.add(courseList.get(i));
			}
		} else {
			// 从ProfessionItemDao 中获取专业对应的专业（专业上课计划）
			ProfessionItemDao professionItemDao = getSqlSession().getMapper(ProfessionItemDao.class);
			courseTemp = professionItemDao.findCourseByProfessionId(profession.getProfessionId());
		}
		
		JsonConfig config = new JsonConfig();
		config.setExcludes(new String[] { "isRequired", "isMajorRequired", "isMajorOption","isOptional"});
		String result = JSONArray.fromObject(courseTemp, config).toString();
		return result;

	}
}
