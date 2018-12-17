package com.luo.service.impl;

import com.luo.base.list.SeqList;
import com.luo.dao.TeacherDao;
import com.luo.dao.TeacherDao;
import com.luo.dao.impl.TeacherDaoImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.luo.entity.CourseItem;
import com.luo.entity.CourseItem;
import com.luo.entity.Page;
import com.luo.entity.Teacher;
import com.luo.service.CourseItemService;
import com.luo.service.TeacherService;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

public class TeacherServiceImpl implements TeacherService {

	public TeacherDao teacherDao;

	public TeacherServiceImpl() {
		this.teacherDao = new TeacherDaoImpl();

	}

	@Override
	public void alertTeacher(Teacher teacher) {
		// 修改教师
		teacherDao.alertTeacher(teacher);

		CourseItemService courseItemService = new CourseItemServiceImpl();
		if (!teacher.getCourseList().isEmpty()) {
			int site = teacher.getCourseList().size();

			// 说明增加教师时也增加了专业，要添加到教师课程表中
			CourseItem[] courses = new CourseItem[site];
			for (int i = 0; i < site; i++) {
				courses[i] = teacher.getCourseList().get(i);
			}

			courseItemService.alertCourseItem(courses, teacher.getId());
		} else {
			// 如果为空的话，得删除掉教师课程表中的数据，这里先不做任何处理，因为从专业获取课表是从教师课程表中得到的，如果直接删除的话那么专业讲获取不到该门课程
			// 改进：建立一个专业课程表对应，这样就可以
			CourseItem[] courses = new CourseItem[] {};
			courseItemService.alertCourseItem(courses, teacher.getId());
		}

	}

	@Override
	public void deleteTeacher(Teacher teacher) {
		// 删除教师

		teacherDao.deleteTeacher(teacher);

		CourseItemService courseItemService = new CourseItemServiceImpl();
		courseItemService.deleteCourseItem(teacher.getId());

	}

	@Override
	public String getTeacherListByPage(Page page) {

		// 存放分页后的学生信息(默认的数组大小是64)
		List<Teacher> teacherTemp = new ArrayList<Teacher>();

		// 通过页数获取不同页面的教师
		// 先获取教师全部数据的顺序表（方便分页）
		SeqList<Teacher> teacherSeqList = teacherDao.getTeacherSeqList();

		// 定义Map
		Map<String, Object> jsonMap = new HashMap<String, Object>();

		for (int i = 0; i < page.getPageSize(); i++) {
			if (teacherSeqList.get(i + page.getStart()) != null) {
				teacherTemp.add(teacherSeqList.get(i + page.getStart()));
			} else {
				break;
			}
		}
		// 存放学生总人数
		jsonMap.put("total", teacherSeqList.size());

		// 存放某页的n行学生
		jsonMap.put("rows", teacherTemp);

		JsonConfig config = new JsonConfig();
		config.setExcludes(new String[] { "courses", "teacher", "teacherId" });
		// 格式化Map,以json格式返回数据
		String message = JSONObject.fromObject(jsonMap, config).toString();
		System.out.println(message);
		return message;
	}

	@Override
	public void insertTeacher(Teacher teacher) {
		// 增加老师
		// (如果增加的老师中有课程安排的话，那么除了增加到教师表中，还要把教师对应的专业和课程添加到教师课程表中)
		teacherDao.insertTeacher(teacher);

		if (!teacher.getCourseList().isEmpty()) {
			CourseItemService courseItemService = new CourseItemServiceImpl();
			// 说明增加教师时也增加了专业，要添加到教师课程表中
			Iterator<CourseItem> it = teacher.getCourseList().iterator();
			while (it.hasNext()) {
				CourseItem courseItem = it.next();
				// 调用courseItemService中的增加courseItem方法去增加教师及对应的专业和课程
				courseItemService.insertCourseItem(courseItem);
			}
		}

	}
}
