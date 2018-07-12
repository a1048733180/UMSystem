package com.luo.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SystemServlet extends HttpServlet{
	// 序列化id
	final static long serialVersionUID = 1L;
	
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 获取get内容
		String method = request.getParameter("method");
		if("LoginOut".equals(method)) {
			// 退出登陆
			loginOut(request,response);
		}else if("toAdminView".equals(method)) {
			// 跳到管理员界面
			request.getRequestDispatcher("/WEB-INF/view/admin/admin.jsp").forward(request, response);
		}else if("toStudentView".equals(method)) {
			// 跳到学生界面
			request.getRequestDispatcher("/WEB-INF/view/student/student.jsp").forward(request, response);
		}else if("toTeacherView".equals(method)) {
			// 跳到教师界面
			request.getRequestDispatcher("/WEB-INF/view/teacher/teacher.jsp").forward(request, response);
		}
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}
	
	// 注销操作
	public void loginOut(HttpServletRequest request,HttpServletResponse response) throws IOException{
		// 清除账户信息
		request.getSession().removeAttribute("user");
		// 重定向跳转到首页
		String contextPath = request.getContextPath();
		response.sendRedirect(contextPath);
	}
}
