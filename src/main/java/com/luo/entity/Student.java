package com.luo.entity;

/**
 * 学生类
 * 18.12.19
 * @author L99
 *
 */
public class Student implements Comparable<Student> {

    // 学号
    private long studentId;

    // 名字
    private String studentName;

    // 性别
    private String studentSex;

    //
    private Profession studentProfession;

    // 生日
    private String studentBirthday;

    // 联系方式
    private String studentTel;

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentSex() {
        return studentSex;
    }

    public void setStudentSex(String studentSex) {
        this.studentSex = studentSex;
    }

    public Profession getStudentProfession() {
        return studentProfession;
    }

    public void setStudentProfession(Profession studentProfession) {
        this.studentProfession = studentProfession;
    }

    public String getStudentBirthday() {
        return studentBirthday;
    }

    public void setStudentBirthday(String studentBirthday) {
        this.studentBirthday = studentBirthday;
    }

    public String getStudentTel() {
        return studentTel;
    }

    public void setStudentTel(String studentTel) {
        this.studentTel = studentTel;
    }

    @Override
    public int compareTo(Student st) {
        return (this.studentId < st.studentId ? -1 : (this.studentId == st.studentId) ? 0 : 1);
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", studentName='" + studentName + '\'' +
                ", studentSex='" + studentSex + '\'' +
                ", studentProfession=" + studentProfession +
                ", studentBirthday='" + studentBirthday + '\'' +
                ", studentTel='" + studentTel + '\'' +
                '}';
    }
}
