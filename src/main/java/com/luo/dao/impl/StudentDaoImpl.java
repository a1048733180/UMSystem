package com.luo.dao.impl;

import com.luo.base.list.SeqList;
import com.luo.base.list.SortedCirDoublyList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import com.luo.dao.StudentDao;
import com.luo.entity.Profession;
import com.luo.entity.Student;

/**
 * 学生信息查询实现类
 *
 * @author L99
 *
 */
public class StudentDaoImpl implements StudentDao {

    private Student student;

    // 创建一个排序学生表，按照学号排序
    public SortedCirDoublyList<Student> studentList = new SortedCirDoublyList<>();

    // 存放一个链表转化的数组
    public SeqList<Student> studentSeqList;

    static File file = new File("F:\\JAVA项目\\UMSystem\\文件表\\学生表.txt");

    public StudentDaoImpl() {
        readContent(file);
    }

    // 读取本地学生表中的内容放入studentList中，
    public void readContent(File file) {
        String thisLine;
        try {
            BufferedReader bfr = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            while ((thisLine = bfr.readLine()) != null) {
                String[] str = thisLine.split("/");

                student = new Student();
                student.setId(Integer.valueOf(str[0])); // ID
                student.setName(str[1]); // 姓名
                student.setSex(str[2]); // 性别
                student.setAge(Integer.valueOf(str[3])); // 年龄

                String[] professionList = str[4].split("-"); // 分割专业
                Profession profession = new Profession();
                profession.setId(Integer.valueOf(professionList[0])); // 专业id
                profession.setName(professionList[1]); // 专业名称

                student.setProfessionId(profession.getId());
                student.setProfession(profession); // 专业
                student.setIntroduce(str[5]); // 简介

                studentList.insert(student);// 插入学生
            }
            bfr.close();
        } catch (IOException e) {
            System.out.println("读取学生表失败" + e);
        }
    }

    @Override
    public void insertStudent(Student student) {
        // 增加学生

        studentList.insert(student);

        writerContent(file, "添加学生");
    }

    @Override
    public void deleteStudent(int id) {
        // 从集合中删除特定Id的学生
        studentList.delete(new Student(id));

        writerContent(file, "删除学生");

    }

    @Override
    public Student selectStudentById(int id) {
        // 从学生集合中查找特定id的学生
        Student stu = new Student(id);
        return studentList.find(stu);
    }

    @Override
    public void alertStudent(Student student) {
        // 这里可能出现问题
        // 修改特定学生
        // 查找该学生
        Student stu = selectStudentById(student.getId());

        stu.setName(student.getName());
        stu.setAge(student.getAge());
        stu.setSex(student.getSex());
        stu.setProfession(student.getProfession());
        stu.setProfessionId(student.getProfessionId());
        stu.setIntroduce(student.getIntroduce());

        writerContent(file, "修改学生");

    }

    @Override
    public SortedCirDoublyList<Student> getStudentList() {
        // 获取全部学生列表
        return this.studentList;
    }

    @Override
    public void changeToSeqList() {
        // 把排序链表转化成顺序表
        this.studentSeqList = this.studentList.traverse();
    }

    @Override
    public SeqList<Student> getStudentSeqList() {
        // 返回学生顺序表，方便分页查询
        return this.studentSeqList;
    }

    // 把学生排序表更新到本地
    public void writerContent(File file, String message) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            for (int i = 0; i < studentList.size(); i++) {
                Student stuTemp = studentList.get(i);
                String str = "";
                str += stuTemp.getProfessionId() + "-" + stuTemp.getProfession();
                out.write(stuTemp.getId() + "/" + stuTemp.getName() + "/" + stuTemp.getSex() + "/" + stuTemp.getAge()
                        + "/" + str + "/" + stuTemp.getIntroduce());
                out.newLine();
            }
            out.flush();
            out.close();
        } catch (IOException e) {
            System.out.println("更新本地发生错误：" + message);
        }
    }
}
