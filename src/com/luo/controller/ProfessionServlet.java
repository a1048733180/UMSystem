package com.luo.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.luo.entity.Profession;
import com.luo.service.ProfessionService;
import com.luo.service.impl.ProfessionServiceImpl;

public class ProfessionServlet extends HttpServlet {

	ProfessionService professionService = new ProfessionServiceImpl();

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
		System.out.println("进入了1方法");
		String profession = request.getParameter("profession");
		Profession pro = new Profession();
		if (!(profession == null || "".equals(profession.trim()))) {
			pro.setName(profession);
		}
		String result = professionService.getProfessionList(pro);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(result);
	}
}
