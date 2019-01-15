package com.luo.dao.impl;

import com.luo.base.list.SeqList;
import com.luo.base.list.SortedCirDoublyList;
import com.luo.dao.CourseDao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import com.luo.entity.Course;

/**
 * 课程实现类
 *
 * @author L99
 *
 */
public class CourseDaoImpl implements CourseDao {

    // 记录必修课和公共必修课的数目
    private int majorCourseCount = 0;

    public SortedCirDoublyList<Course> courseList = new SortedCirDoublyList<>();

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

                course.setCourseId(Integer.valueOf(str[0]));
                course.setCourseName(str[1]);

                courseList.insert(course);
            }
            in.close();
        } catch (IOException e) {
            System.out.println("读取课程表失败" + e);
        }
    }

    @Override
    public List<Course> getCourse() {
        return null;
    }

    @Override
    public int insertCourse(Course course) {
        // 新增课程
        // 从课程表排序表中添加后更新到本地文件
        courseList.insert(course);

        writerContent(file, "增加课程");
        return 0;
    }

    @Override
    public int deleteCourse(int courseId) {
        // 删除课程
        // 从课程表排序表中删除后更新到本地文件


        writerContent(file, "删除课程");
        return 0;

    }

    @Override
    public void alertCourse(Course course) {
        // 修改课程表
        // 默认id是不可修改的
        // 根据id寻找课程表中特定id的课程，寻找是根据comparaTo方法比较id
        Course courseTemp = courseList.find(course);


        writerContent(file, "修改课程");
    }

    @Override
    public int countCourse() {
        // 记录有多少门必修课和专业必修课
        // 建立一个课程顺序表，查找起来比在链表查表时间复杂度低
        SeqList<Course> courseSeqList = courseList.traverse();

        for (int j = 0; j < courseSeqList.size(); j++) {
            Course courseTemp = courseSeqList.get(j);

        }
        return majorCourseCount;
    }

    @Override
    public List<Course> getCourseSeqList() {
        // 获取课程顺序表，由链表转换过来
        return this.getCourse();
    }

    @Override
    // 通过课程id返回课程名
    public Course getCourseById(int id) {
        Course course = new Course();
        course.setCourseId(id);
        course = this.courseList.find(course);
        return course;
    }

    // 把课程排序表更新到本地
    public void writerContent(File file, String message) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            for (int i = 0; i < courseList.size(); i++) {
                Course courseTemp = courseList.get(i);
                String str = "";

                out.write(courseTemp.getCourseId() + "/" + courseTemp.getCourseName() + "/" + str);
                out.newLine();
            }
            out.flush();
            out.close();
        } catch (IOException e) {
            System.out.println("课程更新本地发生错误：" + message);
        }
    }
}
