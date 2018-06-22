package com.luo.dao.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import com.luo.base.list.SeqList;
import com.luo.base.list.SortedSinglyList;
import com.luo.dao.CourseDao;
import com.luo.entity.Course;
import com.luo.entity.Profession;

/**
 * 课程实现类
 * 
 * @author L99
 *
 */
public class CourseDaoImpl implements CourseDao {

	// 记录必修课和公共必修课的数目
	private int majorCourseCount = 0;

	public SortedSinglyList<Course> courseList = new SortedSinglyList<>();

	static File file = new File("F:\\JAVA项目\\UMSystem\\文件表\\课程表.txt");

	public CourseDaoImpl() {
		readContent(file);
	}

	// 读取文件内容
	public void readContent(File file) {
		String thisLine;
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
			while ((thisLine = in.readLine()) != null) {
				String[] str = thisLine.split("/");

				Course course = new Course();

				course.setId(Integer.valueOf(str[0]));
				course.setName(str[1]);

				switch (str[2]) {
				case "1":
					course.setRequired(true);
					break;
				case "2":
					course.setMajorRequired(true);
					break;
				case "3":
					course.setOptional(true);
					break;
				}
				courseList.insert(course);
			}
			in.close();
		} catch (IOException e) {
			System.out.println("读取课程表失败" + e);
		}
	}

	public SortedSinglyList<Course> getCourseList() {
		return this.courseList;
	}
	
	@Override
	public void insertCourse(Course course) {
		// 新增课程
		try {
			courseList.insert(course);
			BufferedWriter out = new BufferedWriter(new FileWriter(file, true));
			String str = "";
			if (course.isRequired()) {
				str += "1";
			} else if (course.isMajorRequired()) {
				str += "2";
			} else {
				str += "3";
			}
			out.write(course.getId() + "/" + course.getName() + "/" + str);
			out.newLine();
			out.flush();
			out.close();
		} catch (IOException e) {
			System.out.println("插入课程表失败" + e);
		}
	}

	@Override
	public void deleteCourse(Course course) {
		// 删除课程
		// 从课程表排序表中删除后更新到本地文件
		courseList.delete(course);

		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			for (int i = 0; i < courseList.getCount(); i++) {
				Course courseTemp = courseList.get(i);
				String str = "";
				if (courseTemp.isRequired()) {
					str += "1";
				} else if (courseTemp.isMajorRequired()) {
					str += "2";
				} else {
					str += "3";
				}
				out.write(courseTemp.getId() + "/" + courseTemp.getName() + "/" + str);
				out.newLine();
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			System.out.println("删除课程失败" + e);
		}

	}

	@Override
	public void alertCourse(Course course) {
		// 修改课程表
		// 默认id是不可修改的
		// 根据id寻找课程表中特定id的课程，寻找是根据comparaTo方法比较id
		Course courseTemp = courseList.find(course);

		courseTemp.setName(course.getName());
		courseTemp.setRequired(course.isRequired());
		courseTemp.setMajorRequired(course.isMajorRequired());
		courseTemp.setOptional(course.isOptional());

		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			for (int i = 0; i < courseList.getCount(); i++) {
				Course courseTemp2 = courseList.get(i);
				String str = "";
				if (courseTemp2.isRequired()) {
					str += "1";
				} else if (courseTemp2.isMajorRequired()) {
					str += "2";
				} else {
					str += "3";
				}
				out.write(courseTemp2.getId() + "/" + courseTemp2.getName() + "/" + str);
				out.newLine();
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			System.out.println("修改课程失败" + e);
		}
	}

	@Override
	public int countCourse() {
		// 记录有多少门必修课和专业必修课
		// 建立一个课程顺序表，查找起来比在链表查表时间复杂度低
		SeqList<Course> courseSeqList = courseList.traverse();

		for (int j = 0; j < courseSeqList.size(); j++) {
			Course courseTemp = courseSeqList.get(j);
			if (courseTemp.isRequired() || courseTemp.isMajorRequired()) {
				majorCourseCount++;
			}
		}
		return majorCourseCount;
	}
}
