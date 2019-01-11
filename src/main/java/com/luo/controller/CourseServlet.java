package com.luo.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.luo.entity.Profession;
import com.luo.service.CourseService;
import com.luo.service.impl.CourseServiceImpl;

public class CourseServlet extends HttpServlet{
	
	CourseService courseService = new CourseServiceImpl();
	
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		if("toCourseListView".equalsIgnoreCase(method)) {
			request.getRequestDispatcher("/WEB-INF/view/other/courseList.jsp").forward(request,response);
		}
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		
		if("CourseList".equalsIgnoreCase(method)) {
			courseList(request,response);
		}
	}
	
	
	public void courseList(HttpServletRequest request, HttpServletResponse response)throws IOException{
		String professionId = request.getParameter("professionid"); 
		Profession profession = null;
		// 如果传进的专业为空 则获取全部专业列表
		if (!(professionId == null || "".equals(professionId.trim()))) {
			// 建一个可以从专业获取课程的方法，通过专业id获取全部课程id和name的list集合
			profession = new Profession();
			profession.setProfessionId(Integer.parseInt(professionId));
		}
		
		String result = courseService.getCourseList(profession);
		
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(result);
	}
}
