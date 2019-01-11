package com.luo.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.luo.service.ProfessionService;
import com.luo.service.impl.ProfessionServiceImpl;

public class ProfessionServlet extends HttpServlet {

	ProfessionService professionService = new ProfessionServiceImpl();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 转发到专业列表
		String method = request.getParameter("method");
		if ("toProfessionListView".equalsIgnoreCase(method)) {
			request.getRequestDispatcher("/WEB-INF/view/other/professionList.jsp").forward(request, response);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("进入了专业方法");
		String method = request.getParameter("method");

		if ("ProfessionList".equalsIgnoreCase(method)) {
			professionList(request, response);
		}
	}

	public void professionList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("进入了professionList方法");
		String course =request.getParameter("course");

		String result = professionService.getProfessionList(course);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(result);
	}
}
