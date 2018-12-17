package com.luo.entity;

/**
 * 学生类
 * 
 * @author L99
 * 
 */
public class Student implements Comparable<Student> {

	private int id; // 学号

	private String name; // 名字

	private String sex; // 性别

	private int age; // 年龄

	private Profession profession; // 专业

	private int professionId; // 专业id

	private String introduce; // 简介

	public Student() {
	}

	public Student(int id) {
		this.id = id;
	}

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

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

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

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	@Override
	public int compareTo(Student st) {
		return (this.id < st.id ? -1 : (this.id == st.id) ? 0 : 1);
	}

//	@Override
//	public String toString() {
//		return "Student [id=" + id + ", name=" + name + ", sex=" + sex + ", age=" + age + ", professionId="
//				+ professionId + ", introduce=" + introduce + "]";
//	}
	
	
}
