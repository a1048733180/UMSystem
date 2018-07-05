package com.luo.entity;

import java.util.List;

import com.luo.base.list.SeqList;

/**
 * 专业类
 * 
 * @author L99
 * 
 */
public class Profession implements Comparable<Profession>{
	private int id; // 专业Id

	private String name; // 专业名字

	private List<Course> courseList; // 专业要上的课程

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Course> getCourseList() {
		return courseList;
	}

	public void setCourseList(List<Course> courseList) {
		this.courseList = courseList;
	}

	@Override
	public String toString() {
		return name;
	}
	
	@Override
	public int compareTo(Profession pro) {
		return (this.id < pro.id ? -1 : (this.id == pro.id) ? 0 : 1);
	}

}
