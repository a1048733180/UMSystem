package com.luo;

import com.luo.dao.StudentDao;
//import com.luo.dao.impl.StudentDaoImpl;
import com.luo.entity.Profession;
import com.luo.entity.Student;
import com.luo.service.StudentService;
import com.luo.service.impl.StudentServiceImpl;
import com.luo.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;


import java.util.List;

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
        profession.setId(2);
        student.setStudentProfession(profession);

        studentDao.insertStudent(student);
        sqlSession.commit();
        sqlSession.close();
        //studentDao.deleteStudent(3116004244l);
    }
}
