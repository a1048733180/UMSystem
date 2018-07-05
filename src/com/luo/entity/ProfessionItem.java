package com.luo.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 专业课程对应类
 * 
 * @author L99
 *
 */
public class ProfessionItem {
	
	private Profession profession; // 专业

	private int professionId; // 专业id
	
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

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + professionId;
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
		ProfessionItem other = (ProfessionItem) obj;
		if (professionId != other.professionId)
			return false;
		return true;
	}

	
}
