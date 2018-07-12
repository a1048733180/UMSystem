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

	private int courseId; // 课程id

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

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int coueseId) {
		this.courseId = coueseId;
	}

	@Override
	public int compareTo(CourseItem courseItem) {
		return (this.professionId < courseItem.professionId ? -1
				: (this.professionId == courseItem.professionId) ? 0 : 1);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + teacherId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CourseItem other = (CourseItem) obj;
		if (teacherId != other.teacherId)
			return false;
		return true;
	}

	
	
}
