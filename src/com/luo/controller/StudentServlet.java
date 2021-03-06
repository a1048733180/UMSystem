package com.luo.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.luo.entity.Page;
import com.luo.entity.Student;
import com.luo.service.StudentService;
import com.luo.service.impl.StudentServiceImpl;

public class StudentServlet extends HttpServlet {

	StudentService studentService = new StudentServiceImpl();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String method = request.getParameter("method");
		System.out.println("进入了学生方法");
		if ("toStudentListView".equalsIgnoreCase(method)) { // 转发到学生列表页
			request.getRequestDispatcher("/WEB-INF/view/student/studentList.jsp").forward(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String method = request.getParameter("method");

		if ("StudentList".equalsIgnoreCase(method)) {
			studentList(request, response);
		} else if ("AddStudent".equalsIgnoreCase(method)) {
			addStudent(request, response);
		} else if ("EditStudent".equalsIgnoreCase(method)) {
			editStudent(request, response);
		} else if ("DeleteStudent".equalsIgnoreCase(method)) {
			deleteStudent(request, response);
		}
	}

	public void studentList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 分页获取学生列表
		String professionId = request.getParameter("professionid");
		System.out.println(professionId);

		// 获取分页参数
		int page = Integer.parseInt(request.getParameter("page"));
		int rows = Integer.parseInt(request.getParameter("rows"));

		Student student = new Student();
		if (!(professionId == null || "".equals(professionId.trim()))) {
			System.out.println("进入了判断方法");
			student.setProfessionId(Integer.valueOf(professionId));
		}

		// 获取分页学生
		// 如果学生存在的话那么就是查找特定专业的学生，否则就是查找全部学生
		// 这里的result是json格式
		String result = studentService.getStudentListByPage(student, new Page(page, rows));
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(result);
	}

	// 添加学生
	public void addStudent(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 获取姓名
		String name = request.getParameter("name");
		// 获取学号
		String id = request.getParameter("id");
		// 获取性别
		String sex = request.getParameter("sex");
		// 获取年龄
		String age = request.getParameter("age");
		// 获取专业
		String professionId = request.getParameter("professionid");
		// 获取简介
		String introduce = request.getParameter("introduce");

		Student stu = new Student();
		stu.setId(Integer.valueOf(id));
		stu.setName(name);
		stu.setSex(sex);
		stu.setAge(Integer.valueOf(age));
		stu.setProfessionId(Integer.valueOf(professionId));
		stu.setIntroduce(introduce);
		System.out.println(id + " " + name + " " + age + " " + sex + " " + professionId + " " + introduce);

		studentService.insertStudent(stu);
		response.getWriter().write("success");
	}

	// 修改学生
	public void editStudent(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 获取姓名
		String name = request.getParameter("name");
		// 获取学号
		String id = request.getParameter("id");
		// 获取性别
		String sex = request.getParameter("sex");
		// 获取年龄
		String age = request.getParameter("age");
		// 获取专业
		String professionId = request.getParameter("professionid");
		// 获取简介
		String introduce = request.getParameter("introduce");

		Student stu = new Student();
		stu.setId(Integer.valueOf(id));
		stu.setName(name);
		stu.setSex(sex);
		stu.setAge(Integer.valueOf(age));
		stu.setProfessionId(Integer.valueOf(professionId));
		stu.setIntroduce(introduce);
		System.out.println(id + " " + name + " " + age + " " + sex + " " + professionId + " " + introduce);

		studentService.alertStudent(stu);
		response.getWriter().write("success");
	}

	// 删除学生
	public void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 获取要删除的学生学号集合
		String[] ids = request.getParameterValues("ids[]");
		Integer[] sid = new Integer[ids.length];
		for (int i = 0; i < ids.length; i++) {
			sid[i] = Integer.parseInt(ids[i]);
		}
		for (int i = 0; i < sid.length; i++) {
			studentService.deleteStudent(sid[i]);
		}

		response.getWriter().write("success");
	}
}
