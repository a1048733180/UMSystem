package com.luo.entity;

/**
 * 课程类
 * 
 * @author L99
 * 
 */
public class Course implements Comparable<Course>{
	/**
	 * 课程id
	 */
	private int courseId;

	/**
	 * 课程名称
	 */
	private String courseName;


	/**
	 * 公共基础
	 */
	private boolean isRequired;

	/**
	 * 专业基础课
	 */
	private boolean isMajorRequired;

	/**
	 * 专业选修课
	 */
	private boolean isMajorOption;

	/**
	 * 任意选修课
	 */
	private boolean isOptional;

	/**
	 * 课程学时
	 */
	private int courseHour;


	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public boolean isRequired() {
		return isRequired;
	}

	public void setRequired(boolean required) {
		isRequired = required;
	}

	public boolean isMajorRequired() {
		return isMajorRequired;
	}

	public void setMajorRequired(boolean majorRequired) {
		isMajorRequired = majorRequired;
	}

	public boolean isMajorOption() {
		return isMajorOption;
	}

	public void setMajorOption(boolean majorOption) {
		isMajorOption = majorOption;
	}

	public boolean isOptional() {
		return isOptional;
	}

	public void setOptional(boolean optional) {
		isOptional = optional;
	}

	public int getCourseHour() {
		return courseHour;
	}

	public void setCourseHour(int courseHour) {
		this.courseHour = courseHour;
	}

	@Override
	public int compareTo(Course cour) {
		return (this.courseId < cour.courseId ? -1 : (this.courseId == cour.courseId) ? 0 : 1);
	}

	@Override
	public String toString() {
		return "Course{" +
				"courseId=" + courseId +
				", courseName='" + courseName + '\'' +
				'}';
	}
}
