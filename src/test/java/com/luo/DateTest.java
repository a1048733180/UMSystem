package com.luo;

import com.luo.dao.*;
import com.luo.entity.*;
import com.luo.service.*;
import com.luo.service.impl.CourseItemServiceImpl;
import com.luo.service.impl.CourseServiceImpl;
import com.luo.service.impl.StudentServiceImpl;
import com.luo.service.impl.TeacherServiceImpl;
import com.luo.util.MyBatisUtil;
import net.sf.json.JSONArray;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author L99
 * @createDate 2018/12/19
 *
 */

public class DateTest {
    public static void main(String[] args) {

        SqlSession sqlSession = MyBatisUtil.getSqlSession();
//        StudentDao studentDao = MyBatisUtil.getSqlSession().getMapper(StudentDao.class);
//        studentDao.getStudent().stream().forEach(System.out::println);


//        String statement = "com.luo.dao.StudentDao.getStudent";
//        SqlSession sqlSession = MyBatisUtil.getSqlSession();
//        List<Student> s = sqlSession.selectList(statement);
//        s.stream().forEach(System.out::println);
//        sqlSession.close();

        StudentDao studentDao = sqlSession.getMapper(StudentDao.class);
//        studentDao.getStudent().stream().forEach(System.out::println);
//        long num = Long.parseLong("3116004240");
//        Student student = studentDao.selectStudentById(num);
//        System.out.println(student);
        Student student = new Student();
        student.setStudentId(3116003100l);
        student.setStudentName("刘文文");
        student.setStudentSex("女");
        student.setStudentBirthday("1998-02-04");
        student.setStudentTel("13692194572");
        Profession profession = new Profession();
        profession.setProfessionId(2);
        student.setStudentProfession(profession);
        StudentService studentService = new StudentServiceImpl();
        System.out.println(studentService.insertStudent(student));

//        studentDao.insertStudent(student);
//        sqlSession.commit();
//        sqlSession.close();
        //studentDao.deleteStudent(3116004244l);
    }

    @Test
    public void test() {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        StudentDao studentDao = sqlSession.getMapper(StudentDao.class);
        studentDao.getStudent().stream().forEach(System.out::println);
    }

    /**
     * 教师查询测试
     */
    @Test
    public void teacherTest() {
        TeacherService teacherService = new TeacherServiceImpl();
        Page page = new Page(1, 10);
        System.out.println(teacherService.getTeacherListByPage(page));
    }

    @Test
    public void courseTest() {
        CourseDao courseDao = MyBatisUtil.getSqlSession().getMapper(CourseDao.class);
        courseDao.getCourse().stream().forEach(System.out::println);
    }

    @Test
    public void professionItemTest() {
        CourseService courseService = new CourseServiceImpl();
        Profession profession = new Profession();
        profession.setProfessionId(2);
        System.out.println(courseService.getCourseList(null));
    }

    @Test
    public void intToCharTest() {
        List<Integer> lists = new ArrayList<>();
        lists.add(1);
        lists.add(2);
        lists.stream().forEach(System.out::println);
    }

    @Test
    public void courseItemTest(){
        List<CourseItem> courseList = new ArrayList<>();
        CourseItem courseItem1 = new CourseItem();
        courseItem1.setTeacherId(3);
        courseItem1.setCourseId(3);
        courseItem1.setProfessionId(3);

        CourseItem courseItem2 = new CourseItem();
        courseItem2.setTeacherId(4);
        courseItem2.setProfessionId(4);
        courseItem2.setCourseId(4);

        courseList.add(courseItem1);
        courseList.add(courseItem2);

        CourseItemService courseItemService = new CourseItemServiceImpl();
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        CourseItemDao courseItemDao = sqlSession.getMapper(CourseItemDao.class);
        courseList.stream().forEach((courseItem) -> courseItemDao.insertCourseItem(courseItem));
        sqlSession.commit();
        sqlSession.close();
    }


}



