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
	 * 课程学时
	 */
	private String courseNature;

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

	public String getCourseNature() {
		return courseNature;
	}

	public void setCourseNature(String courseNature) {
		this.courseNature = courseNature;
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
