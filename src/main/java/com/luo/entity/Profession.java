package com.luo.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 专业类
 *
 * @author L99
 *
 */
public class Profession implements Comparable<Profession> {

    /**
     * 专业Id
     */
    private int professionId;

    /**
     * 专业名字
     */
    private String professionName;

    /**
     * 专业要上的课程
     */
    private List<Course> courseList;

    public int getProfessionId() {
        return professionId;
    }

    public void setProfessionId(int professionId) {
        this.professionId = professionId;
    }

    public String getProfessionName() {
        return professionName;
    }

    public void setProfessionName(String professionName) {
        this.professionName = professionName;
    }

    public List<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }

    @Override
    public String toString() {
        return "Profession{" +
                "professionId=" + professionId +
                ", professionName='" + professionName + '\'' +
                '}';
    }

    @Override
    public int compareTo(Profession pro) {
        return (this.professionId < pro.professionId ? -1 : (this.professionId == pro.professionId) ? 0 : 1);
    }

}
