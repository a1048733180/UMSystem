package com.luo.service.impl;

import com.luo.service.ProfessionService;

public class text {
	public static void main(String[] args) {
		ProfessionService p = new ProfessionServiceImpl();
		String course = "course";
		System.out.println(p.getProfessionList(course));
	}
}
