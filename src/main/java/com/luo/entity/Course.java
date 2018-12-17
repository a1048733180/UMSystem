package com.luo.entity;

/**
 * 课程类
 * 
 * @author L99
 * 
 */
public class Course implements Comparable<Course>{
	private int id; // 课程id

	private String name; // 课程名称

	private boolean isRequired; // 是否为必修课

	private boolean isMajorRequired; // 是否为专业必修课

	private boolean isOptional; // 是否为专业选修课

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

	public boolean isRequired() {
		return isRequired;
	}

	public void setRequired(boolean isRequired) {
		this.isRequired = isRequired;
	}

	public boolean isMajorRequired() {
		return isMajorRequired;
	}

	public void setMajorRequired(boolean isMajorRequired) {
		this.isMajorRequired = isMajorRequired;
	}

	public boolean isOptional() {
		return isOptional;
	}

	public void setOptional(boolean isOptional) {
		this.isOptional = isOptional;
	}
	
	
	@Override
	public int compareTo(Course cour) {
		return (this.id < cour.id ? -1 : (this.id == cour.id) ? 0 : 1);
	}

}
