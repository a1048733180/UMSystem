package com.luo.dao.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import com.luo.base.list.SeqList;
import com.luo.base.list.SortedCirDoublyList;
import com.luo.dao.CourseItemDao;
import com.luo.dao.TeacherDao;
import com.luo.entity.Teacher;

/**
 * 教师信息查询实现类
 * 
 * @author L99
 *
 */
public class TeacherDaoImpl implements TeacherDao {

	static File file = new File("F:\\JAVA项目\\UMSystem\\文件表\\教师表.txt");

	// 建立一个教师排序链表
	SortedCirDoublyList<Teacher> teacherList;

	// 存放一个由链表转化的教师顺序表
	public SeqList<Teacher> teacherSeqList;

	public TeacherDaoImpl() {
		readContent(file);
	}

	public void readContent(File file) {
		String thisLine;
		teacherList = new SortedCirDoublyList<>();
		try {
			// 从课程教师联立类中读取教师的CourseList(课程和专业)
			CourseItemDao courseItemDao = new CourseItemDaoImpl();

			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
			while ((thisLine = in.readLine()) != null) {
				Teacher teacher = new Teacher();
				String[] str = thisLine.split("/");
				teacher.setId(Integer.valueOf(str[0]));
				teacher.setName(str[1]);
				teacher.setSex(str[2]);
				teacher.setEntryYear(str[3]); // 这里日期设置成String,并没有设置成Data
				teacher.setJobTitle(str[4]);
				teacher.setCourseList(courseItemDao.findCourseItemByTeacherId(teacher.getId()));
				teacherList.insert(teacher);
			}
			in.close();
		} catch (IOException e) {
			System.out.println("读取文件失败" + e);
		}
	}

	@Override
	public void insertTeacher(Teacher teacher) {
		// 新增教师信息
		teacherList.insert(teacher);

		writerContent(file, "添加学生");

		refresh();
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

		writerContent(file, "修改教师");
		// 更新数据
		refresh();
	}

	@Override
	public void deleteTeacher(Teacher teacher) {
		// 先从教师排序表中删除
		teacherList.delete(teacher);

		writerContent(file, "删除教师");
		// 更新数据
		refresh();
	}

	@Override
	public SortedCirDoublyList<Teacher> getTeacherList() {
		// 返回教师排序链表
		return this.teacherList;
	}

	@Override
	public SeqList<Teacher> getTeacherSeqList() {
		// 返回教师顺序表
		this.teacherSeqList = this.teacherList.traverse();
		return this.teacherSeqList;
	}

	// 把教师排序表更新到本地
	public void writerContent(File file, String message) {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			for (int i = 0; i < teacherList.size(); i++) {
				Teacher tea = teacherList.get(i);
				out.write(tea.getId() + "/" + tea.getName() + "/" + tea.getSex() + "/" + tea.getEntryYear() + "/"
						+ tea.getJobTitle());
				out.newLine();
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			System.out.println("更新本地发生错误：" + message);
		}
	}

	// 更新数据
	private void refresh() {
		readContent(file);
	}
}
