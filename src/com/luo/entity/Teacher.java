 package com.luo.entity;

import com.luo.base.list.SeqList;

/**
 * 教师类
 * 
 * @author 10487
 *
 */
public class Teacher implements Comparable<Teacher>{
	private int id; // 教师id

	private String name; // 教师名字

	private String sex; // 性别

	private String entryYear; // 入职年份

	private String jobTitle; // 职称

	private String[] courses = new String[] {}; // 课程集合

	private SeqList<CourseItem> courseList = new SeqList<>(); // 课表和哪里上课

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

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getEntryYear() {
		return entryYear;
	}

	public void setEntryYear(String entryYear) {
		this.entryYear = entryYear;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String[] getCourses() {
		return courses;
	}

	public void setCourses(String[] courses) {
		this.courses = courses;
	}

	public SeqList<CourseItem> getCourseList() {
		return courseList;
	}

	public void setCourseList(SeqList<CourseItem> courseList) {
		this.courseList = courseList;
	}
	
	@Override
	public int compareTo(Teacher te) {
		return (this.id < te.id ? -1 : (this.id == te.id) ? 0 : 1);
	}

}
