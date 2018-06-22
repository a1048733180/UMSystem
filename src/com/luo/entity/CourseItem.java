package com.luo.entity;

/**
 * 教师和课程绑定
 * 
 * @author L99
 *
 */
public class CourseItem implements Comparable<CourseItem> {
	private Profession profession; // 专业

	private int professionId; // 专业id

	private Teacher teacher; // 教师

	private int teacherId; // 教师id

	private Course course; // 课程

	private int coueseId; // 课程id

	public Profession getProfession() {
		return profession;
	}

	public void setProfession(Profession profession) {
		this.profession = profession;
	}

	public int getProfessionId() {
		return professionId;
	}

	public void setProfessionId(int professionId) {
		this.professionId = professionId;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public int getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public int getCoueseId() {
		return coueseId;
	}

	public void setCoueseId(int coueseId) {
		this.coueseId = coueseId;
	}

	@Override
	public int compareTo(CourseItem courseItem) {
		return (this.professionId < courseItem.professionId ? -1
				: (this.professionId == courseItem.professionId) ? 0 : 1);
	}
}
