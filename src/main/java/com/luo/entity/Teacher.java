package com.luo.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 教师类
 *
 * @author 10487
 *
 */
public class Teacher implements Comparable<Teacher> {
    private int teacherId; // 教师id

    private String teacherName; // 教师名字

    private String teacherSex; // 性别

    private String entryYear; // 入职年份

    private String jobTitle; // 职称

    private String teacherTel;

    private String[] courses = new String[]{}; // 课程集合

    private List<CourseItem> courseList = new ArrayList<>(); // 课表和哪里上课

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherSex() {
        return teacherSex;
    }

    public void setTeacherSex(String teacherSex) {
        this.teacherSex = teacherSex;
    }

    public String getEntryYear() {
        return entryYear;
    }

    public void setEntryYear(String entryYear) {
        this.entryYear = entryYear;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getTeacherTel() {
        return teacherTel;
    }

    public void setTeacherTel(String teacherTel) {
        this.teacherTel = teacherTel;
    }

    public String[] getCourses() {
        return courses;
    }

    public void setCourses(String[] courses) {
        this.courses = courses;
    }

    public List<CourseItem> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<CourseItem> courseList) {
        this.courseList = courseList;
    }

    @Override
    public int compareTo(Teacher te) {
        return (this.teacherId < te.teacherId ? -1 : (this.teacherId == te.teacherId) ? 0 : 1);
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "teacherId=" + teacherId +
                ", teacherName='" + teacherName + '\'' +
                ", teacherSex='" + teacherSex + '\'' +
                ", entryYear='" + entryYear + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ", teacherTel='" + teacherTel + '\'' +
                ", courses=" + Arrays.toString(courses) +
                ", courseList=" + courseList +
                '}';
    }
}
