package com.luo.dao.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.luo.base.hash.HashSet;
import com.luo.base.list.LinkedMatrix;
import com.luo.base.list.Node;
import com.luo.base.list.SeqList;
import com.luo.base.list.SortedSinglyList;
import com.luo.base.list.Triple;
import com.luo.dao.CourseDao;
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

	// 建立一个以教师id为索引的哈希表
	HashSet<CourseItem> teacherSet;

	public CourseItemDaoImpl() {
		readContent(file);
	}

	static File file = new File("F:\\JAVA项目\\UMSystem\\文件表\\教师课程表.txt");

	// 读取文件内容
	public void readContent(File file) {
		String thisLine;
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
			CourseItem courseItem;

			// 创建一个以专业数量为行数、课程数量（不包括选修课）为列数的矩阵
			courseItemLinkedMatrix = new LinkedMatrix(50, 50);

			teacherSet = new HashSet<CourseItem>();
			while ((thisLine = in.readLine()) != null) {
				courseItem = new CourseItem();
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
				courseItem.setCourseId(course.getId());

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
				Triple tri = new Triple(courseItem.getProfessionId(), courseItem.getCourseId(),
						courseItem.getTeacherId());

				// 添加进三元稀疏矩阵
				courseItemLinkedMatrix.set(tri);

				// 添加到哈希表中
				teacherSet.add(courseItem);
			}
			in.close();
		} catch (IOException e) {
			System.out.println("读取教师课程表失败" + e);
		}
	}

	@Override
	public void insertCourseItemByCourse(Course course, Teacher teacher, Profession[] profession) {
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

		String errorMessage = "新增专业-课表失败";
		writerContent(file, errorMessage);

	}

	@Override
	public void insertCourseItemByTeacher(Course course, Teacher teacher, Profession profession) {
		// 通过增加新教师来增加教师对应的课程和专业
		Triple tri = new Triple(profession.getId(), course.getId(), teacher.getId());
		courseItemLinkedMatrix.set(tri);
		String errorMessage = "新增教师-课表失败";
		writerContent(file, errorMessage);

		refresh();
	}

	@Override
	public void alertCourseItem(CourseItem[] courses, int teacherId) {

		// 先把原来教师所教的专业和课程全部清空。这里清除的是teacherSet(通过教师id查找特定的courseItem)
		CourseItem courseItemTemp = new CourseItem();
		courseItemTemp.setTeacherId(teacherId);
		// 得到教师对应的所有专业和课程
		SeqList<CourseItem> courseItemForThisTeacher = this.teacherSet.search(courseItemTemp);
		// 清空三元矩阵中所有与该教师有关的专业和课程
		// 只要设置值为0，就会删除
		for (int i = 0; i < courseItemForThisTeacher.size() && courseItemForThisTeacher.get(i) != null; i++) {
			Triple tri = new Triple(courseItemForThisTeacher.get(i).getProfessionId(),
					courseItemForThisTeacher.get(i).getCourseId(), 0);
			courseItemLinkedMatrix.set(tri);

		}

		// 修改专业
		// 如果courses为空，那么不会执行下列循环
		for (int i = 0; i < courses.length; i++) {
			Triple tri = new Triple(courses[i].getProfessionId(), courses[i].getCourseId(), courses[i].getTeacherId());
			courseItemLinkedMatrix.set(tri);
		}
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

				while (p != null) {
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
					p = p.next;
				}
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			System.out.println(message + e);
		}
	}

	// private HashSet<CourseItem> getHashSet() {
	// return this.teacherSet;
	// }

	@Override
	public List<CourseItem> findCourseItemByTeacherId(int id) {
		// 返回教师id对应的courseItem

		CourseItem courseItemTemp = new CourseItem();
		courseItemTemp.setTeacherId(id);
		SeqList<CourseItem> courseItemForTeacher = this.teacherSet.search(courseItemTemp);
		List<CourseItem> courseList = new ArrayList<CourseItem>(courseItemForTeacher.size());
		for (int i = 0; i < courseItemForTeacher.size() && courseItemForTeacher.get(i) != null; i++) {
			courseList.add(courseItemForTeacher.get(i));
		}
		return courseList;
	}

	@Override
	// 通过专业查找课程，这里输入的是专业id
	// 通过三元组中的行号（专业号）输出列号（课程号）
	public List<Course> findCourseListByProfessionId(int professionId) {
		CourseDao courseDao = new CourseDaoImpl();
		Triple[] courses = this.courseItemLinkedMatrix.getElementsOfRow(professionId);
		List<Course> coursesList = new ArrayList<Course>();
		// 需要返回的是列号
		for (int i = 0; i < courses.length && courses[i] != null; i++) {
			// 课程id
			int courseId = courses[i].getColumn();
			// 从课程id找到课程然后放入课程集合中
			coursesList.add(courseDao.getCourseById(courseId));
		}

		return coursesList;
	}

	@Override
	public void deleteCourseItemByTeacherId(int teacherId) {
		// 通过传进来的教师id删除该教师对应的专业和课程
		// 先从teacherSet中找到该教师id对应的所有专业和课程，然后清空
		CourseItem courseItem = new CourseItem();
		courseItem.setTeacherId(teacherId);
		SeqList<CourseItem> courseItemForTeacher = teacherSet.search(courseItem);
		// 从集合中获取全部专业和课程，然后删除对应在三元矩阵中的数据
		for (int i = 0; i < courseItemForTeacher.size(); i++) {
			// 设置三元组的值为0就相当于删除该三元组
			Triple tri = new Triple(courseItemForTeacher.get(i).getProfessionId(),
					courseItemForTeacher.get(i).getCourseId(), 0);
			courseItemLinkedMatrix.set(tri);
		}
		String errorMessage = "删除失败";
		writerContent(file, errorMessage);
	}

	// 更新数据，重新从本地读取数据
	private void refresh() {
		readContent(file);
	}
}
