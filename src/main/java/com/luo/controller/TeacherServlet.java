package com.luo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.luo.entity.CourseItem;
import com.luo.entity.Page;
import com.luo.entity.Teacher;
import com.luo.service.TeacherService;
import com.luo.service.impl.TeacherServiceImpl;

public class TeacherServlet extends HttpServlet {

	TeacherService teacherService = new TeacherServiceImpl();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取请求的方法
		String method = request.getParameter("method");
		if ("toTeacherListView".equalsIgnoreCase(method)) { // 转发到教师列表页
			request.getRequestDispatcher("/WEB-INF/view/teacher/teacherList.jsp").forward(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 获取请求的方法
		String method = request.getParameter("method");
		// 请求分发
		if ("TeacherList".equalsIgnoreCase(method)) { // 获取所有教师数据
			teacherList(request, response);
		} else if ("AddTeacher".equalsIgnoreCase(method)) {
			addTeacher(request, response);
		} else if ("EditTeacher".equalsIgnoreCase(method)) {
			editTeacher(request, response);
		} else if ("DeleteTeacher".equalsIgnoreCase(method)) {
			deleteTeacher(request, response);
		}
	}

	public void teacherList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 获取分页参数
		int page = Integer.parseInt(request.getParameter("page"));
		int rows = Integer.parseInt(request.getParameter("rows"));
		String result = teacherService.getTeacherListByPage(new Page(page, rows));
		// 返回数据
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(result);
	}

	// 添加老师功能
	public void addTeacher(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Teacher teacher = new Teacher();
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String sex = request.getParameter("sex");
		String entryYear = request.getParameter("entryYear");
		String jobTitle = request.getParameter("jobTitle");
		// 获取教师的任课课程
		// 如果没有的话就先增加老师基本信息，课程以后可以修改
		String[] courses = request.getParameterValues("course[]");

		teacher.setId(Integer.parseInt(id));
		teacher.setName(name);
		teacher.setSex(sex);
		teacher.setEntryYear(entryYear);
		teacher.setJobTitle(jobTitle);

		CourseItem courseItem;
		List<CourseItem> teacherCourseList = new ArrayList<CourseItem>();
		if (courses != null) {
			for (int i = 0; i < courses.length && courses[i] != null; i++) {
				courseItem = new CourseItem();
				// 分割字符，得出任课的专业和课程
				String[] a = courses[i].split("_");
				// 专业id
				courseItem.setProfessionId(Integer.parseInt(a[0]));
				// 课程id
				courseItem.setCourseId(Integer.valueOf(a[1]));
				// 教师id
				courseItem.setTeacher(teacher);
				courseItem.setTeacherId(teacher.getId());

				teacherCourseList.add(courseItem);
			}
			teacher.setCourseList(teacherCourseList);
		}
		try {
			// 添加到教师表
			teacherService.insertTeacher(teacher);
			teacherService  = new TeacherServiceImpl();
			response.getWriter().write("success");
		} catch (Exception e) {
			response.getWriter().write("fail");
			e.printStackTrace();
		}
	}

	// 修改教师
	public void editTeacher(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Teacher teacher = new Teacher();
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String sex = request.getParameter("sex");
		String entryYear = request.getParameter("entryYear");
		String jobTitle = request.getParameter("jobTitle");
		// 获取教师的任课课程
		// 如果没有的话就先增加老师基本信息，课程以后可以修改
		String[] courses = request.getParameterValues("course[]");

		teacher.setId(Integer.parseInt(id));
		teacher.setName(name);
		teacher.setSex(sex);
		teacher.setEntryYear(entryYear);
		teacher.setJobTitle(jobTitle);

		CourseItem courseItem;
		List<CourseItem> teacherCourseList = new ArrayList<CourseItem>();
		if (courses != null) {
			for (int i = 0; i < courses.length && courses[i] != null; i++) {
				courseItem = new CourseItem();
				// 分割字符，得出任课的专业和课程
				String[] a = courses[i].split("_");
				// 专业id
				courseItem.setProfessionId(Integer.parseInt(a[0]));
				// 课程id
				courseItem.setCourseId(Integer.valueOf(a[1]));
				// 教师id
				courseItem.setTeacher(teacher);
				courseItem.setTeacherId(teacher.getId());

				teacherCourseList.add(courseItem);
			}
			teacher.setCourseList(teacherCourseList);
		}

		try {
			teacherService.alertTeacher(teacher);
			teacherService = new TeacherServiceImpl();
			response.getWriter().write("success");
		} catch (Exception e) {
			response.getWriter().write("fail");
			e.printStackTrace();
		}
	}

	// 删除教师
	public void deleteTeacher(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String[] ids = request.getParameterValues("ids[]");
		for (int i = 0; i < ids.length; i++) {
			Teacher teacher = new Teacher();
			teacher.setId(Integer.parseInt(ids[i]));
			try {
				teacherService.deleteTeacher(teacher);
				//teacherService = new TeacherServiceImpl();
				response.getWriter().write("success");
			} catch (Exception e) {
				response.getWriter().write("fail");
				e.printStackTrace();
			}
		}
	}
}
