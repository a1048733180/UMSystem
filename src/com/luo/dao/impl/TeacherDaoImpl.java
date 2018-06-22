package com.luo.dao.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import com.luo.base.list.SortedSinglyList;
import com.luo.dao.TeacherDao;
import com.luo.entity.Profession;
import com.luo.entity.Teacher;

/**
 * 教师信息查询实现类
 * 
 * @author L99
 *
 */
public class TeacherDaoImpl implements TeacherDao {

	static File file = new File("F:\\JAVA项目\\UMSystem\\文件表\\教师表.txt");

	// 建立一个教师排序表
	SortedSinglyList<Teacher> teacherList = new SortedSinglyList<>();

	public TeacherDaoImpl() {
		readContent(file);
	}

	public void readContent(File file) {
		String thisLine;
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
			while ((thisLine = in.readLine()) != null) {
				Teacher teacher = new Teacher();
				String[] str = thisLine.split("/");
				teacher.setId(Integer.valueOf(str[0]));
				teacher.setName(str[1]);
				teacher.setSex(str[2]);
				teacher.setEntryYear(str[3]); // 这里日期设置成String,并没有设置成Data
				teacher.setJobTitle(str[4]);
				teacherList.insert(teacher);
			}
		} catch (IOException e) {
			System.out.println("读取文件失败" + e);
		}
	}

	public SortedSinglyList<Teacher> getTeacherList() {
		return this.teacherList;
	}
	
	@Override
	public void insertTeacher(Teacher teacher) {
		// 新增教师xinxi
		// 直接添加到文件
		try {
			teacherList.insert(teacher);
			BufferedWriter out = new BufferedWriter(new FileWriter(file, true));
			out.write(teacher.getId() + "/" + teacher.getName() + "/" + teacher.getSex() + "/" + teacher.getEntryYear()
					+ "/" + teacher.getJobTitle());
			out.newLine();
			out.flush();
			out.close();
		} catch (Exception e) {
			System.out.println("增加失败" + e);
		}
	}

	@Override
	public void alertTeacher(Teacher teacher) {
		// 修改老师信息
		// 规定教师id是不能更改的，所以要先找到原数教师集合中的特定教师，然后更改完把整个集合更新到本地
		Teacher teacherTemp = teacherList.find(teacher);
		teacherTemp.setName(teacher.getName());
		teacherTemp.setSex(teacher.getSex());
		teacherTemp.setEntryYear(teacher.getEntryYear());
		teacherTemp.setJobTitle(teacher.getJobTitle());

		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			for (int i = 0; i < teacherList.getCount(); i++) {
				Teacher tea = teacherList.get(i);
				out.write(tea.getId() + "/" + tea.getName() + "/" + tea.getSex() + "/" + tea.getEntryYear() + "/"
						+ tea.getJobTitle());
				out.newLine();
			}
		} catch (IOException e) {
			System.out.println("修改失败" + e);
		}

	}

	@Override
	public void deleteTeacher(Teacher teacher) {
		// 先从教师排序表中删除
		teacherList.delete(teacher);
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			for (int i = 0; i < teacherList.getCount(); i++) {
				Teacher tea = teacherList.get(i);
				out.write(tea.getId() + "/" + tea.getName() + "/" + tea.getSex() + "/" + tea.getEntryYear() + "/"
						+ tea.getJobTitle());
				out.newLine();
			}
		} catch (IOException e) {
			System.out.println("删除失败" + e);
		}
	}

}
