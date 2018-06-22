package com.luo.dao.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import com.luo.base.list.LinkedMatrix;
import com.luo.base.list.Node;
import com.luo.base.list.SeqList;
import com.luo.base.list.SortedSinglyList;
import com.luo.base.list.Triple;
import com.luo.dao.CourseItemDao;
import com.luo.entity.Course;
import com.luo.entity.CourseItem;
import com.luo.entity.Profession;
import com.luo.entity.Teacher;

/**
 * 课程、教师、专业对应实现类
 * 
 * @author L99
 *
 */
public class CourseItemDaoImpl implements CourseItemDao {

	// 建立一个稀疏矩阵的三元组链表类
	// 矩阵行代表专业，列代表课程，只有专业和课程才能表示教师
	public LinkedMatrix courseItemLinkedMatrix;

	public CourseItemDaoImpl() {
	}

	static File file = new File("F:\\JAVA项目\\UMSystem\\文件表\\教师课程表.txt");

	// 读取文件内容
	public void readContent(File file) {
		String thisLine;
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
			CourseItem courseItem = new CourseItem();

			// 创建一个以专业数量为行数、课程数量（不包括选修课）为列数的矩阵
			courseItemLinkedMatrix = new LinkedMatrix(new ProfessionDaoImpl().professionCount(),
					new CourseDaoImpl().countCourse());
			while ((thisLine = in.readLine()) != null) {
				String[] str = thisLine.split("/");

				// 存放专业
				String[] professionStr = str[0].split("-");
				Profession profession = new Profession();
				// 建立一个专业对象，存放专业id和专业名
				profession.setId(Integer.valueOf(professionStr[0]));
				profession.setName(professionStr[1]);
				// CourseItem中的对应属性赋值
				courseItem.setProfession(profession);
				courseItem.setProfessionId(profession.getId());

				// 存放课程
				String[] courseStr = str[1].split("-");
				Course course = new Course();
				// 建立一个课程对象，存放那个课程id和课程名
				course.setId(Integer.valueOf(courseStr[0]));
				course.setName(courseStr[1]);
				// CourseItem中的对应属性赋值
				courseItem.setCourse(course);
				courseItem.setCoueseId(course.getId());

				// 存放教师
				String[] teacherStr = str[2].split("-");
				Teacher teacher = new Teacher();
				// 建立一个教师对象，存放教师的id和名字
				teacher.setId(Integer.valueOf(teacherStr[0]));
				teacher.setName(teacherStr[1]);
				// CourseItem中的对应属性赋值
				courseItem.setTeacher(teacher);
				courseItem.setTeacherId(teacher.getId());
				// 把专业id当成行号、课程id当成列号、教师Id当作值存储在三元组里
				Triple tri = new Triple(courseItem.getProfessionId(), courseItem.getCoueseId(),
						courseItem.getTeacherId());

				courseItemLinkedMatrix.set(tri);
			}
			in.close();
		} catch (IOException e) {
			System.out.println("读取教师课程表失败" + e);
		}
	}

	@Override
	public void insertCourse(Course course, Teacher teacher, Profession[] profession) {
		// 新增课程类
		// 要写入稀疏三元数组链表中，也要写入本地文件中
		// 判断新增课程是专业必修课还是必修课
		if (course.isRequired()) {
			// 如果是必须课，那么每个专业都要上
			for (int i = 0; i < new ProfessionDaoImpl().professionCount(); i++) {
				Triple tri = new Triple(new ProfessionDaoImpl().professionList.get(i).getId(), course.getId(),
						teacher.getId());
				courseItemLinkedMatrix.set(tri);
			}
		} else {
			for (int j = 0; j < profession.length; j++) {
				Triple tri = new Triple(profession[j].getId(), course.getId(), teacher.getId());
				courseItemLinkedMatrix.set(tri);
			}
		}

		String errorMessage = "新增课表失败";
		writerContent(file, errorMessage);

	}

	@Override
	public void alertCourseItem(CourseItem course) {
		// 修改专业
		Triple tri = new Triple(course.getProfessionId(), course.getCoueseId(), course.getTeacherId());
		courseItemLinkedMatrix.set(tri);
		String errorMessage = "修改失败";
		writerContent(file, errorMessage);
	}

	// 写入本地文件
	public void writerContent(File file, String message) {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			// 把引用复制给seqTemp
			SeqList<SortedSinglyList<Triple>> seqTemp = courseItemLinkedMatrix.rowlist;
			for (int i = 0; i < seqTemp.size(); i++) {
				// 获取每一行的链表
				Node<Triple> p = seqTemp.get(i).head.next;
				if (p != null) {
					// 获取专业id
					int professionId = p.data.getRow();
					// 获取专业排序表，以便通过专业id查找专业名
					ProfessionDaoImpl professionList = new ProfessionDaoImpl();
					Profession p1 = new Profession();
					p1.setId(professionId);
					p1 = professionList.getProfessionList().find(p1);
					// 获取专业名
					String professionName = p1.getName();

					// 获取课程id
					int courseId = p.data.getColumn();
					// 获取课程排序表，以便通过课程id查找课程名
					CourseDaoImpl courseList = new CourseDaoImpl();
					Course c1 = new Course();
					c1.setId(courseId);
					c1 = courseList.getCourseList().find(c1);
					// 获取课程名
					String courseName = c1.getName();

					// 获取教师id
					int teacherId = p.data.getValue();
					// 获取教师排序表，以便通过教师id查找教师名
					TeacherDaoImpl teacherList = new TeacherDaoImpl();
					Teacher t1 = new Teacher();
					t1.setId(teacherId);
					t1 = teacherList.getTeacherList().find(t1);
					// 获取教师名
					String teacherName = t1.getName();

					// 写入本地
					// 格式为 01-信息管理与信息系统/01-高数/01-罗老师
					out.write(professionId + "-" + professionName + "/" + courseId + "-" + courseName + "/" + teacherId
							+ "-" + teacherName);
					out.newLine();
				}
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			System.out.println(message + e);
		}
	}

}
