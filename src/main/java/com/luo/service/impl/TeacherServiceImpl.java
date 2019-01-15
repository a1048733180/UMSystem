package com.luo.service.impl;

import com.luo.dao.CourseItemDao;
import com.luo.dao.TeacherDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.luo.entity.CourseItem;
import com.luo.entity.Page;
import com.luo.entity.Teacher;
import com.luo.service.CourseItemService;
import com.luo.service.TeacherService;

import com.luo.util.MyBatisUtil;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.ibatis.session.SqlSession;

/**
 * @author L99
 */
public class TeacherServiceImpl implements TeacherService {

    private SqlSession sqlSession;

    public SqlSession getSqlSession() {
        this.sqlSession = MyBatisUtil.getSqlSession();
        return sqlSession;
    }

    @Override
    public boolean alertTeacher(Teacher teacher) {

        boolean result = false;

        // 修改教师
        try {
            TeacherDao teacherDao = getSqlSession().getMapper(TeacherDao.class);
            int result1 = teacherDao.alertTeacher(teacher);

            if (result1 > 0) {
                result = true;
            }

            List<CourseItem> courseList = teacher.getCourseList();


            CourseItemDao courseItemDao = sqlSession.getMapper(CourseItemDao.class);

            if (!courseList.isEmpty()) {
                //修改教师信息中也修改了任课信息

                // 修改的行数，做判断
                int result2 = 0;

                // 这里要先判断新增的任课信息是否已经存在，如果存在，那么要修改操作；如果不存在，要进行插入操作
                Iterator<CourseItem> iterator = courseList.iterator();
                while (iterator.hasNext()) {
                    CourseItem courseItem = iterator.next();
                    int result3 = courseItemDao.selectProfessionAndCourse(courseItem);
                    if (result3 > 0) {
                        // 说明存在该任课信息，进行修改操作
                        result2 += courseItemDao.alertCourseItem(courseItem);
                    } else {
                        // 说明不存在任课信息，进行插入操作
                        result2 += courseItemDao.insertCourseItem(courseItem);
                    }
                }
                if (result2 != courseList.size()) {
                    result = false;
                }
            } else {
                // 如果为空，可能存在之前有课程但修改操作时删除课程的可能，所以执行一次删除 任课信息 操作
                courseItemDao.deleteCourseItemByTeacherId(teacher.getTeacherId());
            }
        } catch (Exception e) {
            // 如果发生异常，那么关闭数据库连接，抛出异常
            sqlSession.close();
            throw e;
        }

        if (result) {
            sqlSession.commit();
        }

        sqlSession.close();
        return result;
    }

    @Override
    public boolean deleteTeacher(Teacher teacher) {
        boolean result = false;
        try {
            CourseItemDao courseItemDao = getSqlSession().getMapper(CourseItemDao.class);
            // 删除任课信息（不管是否存在都执行删除操作）
            courseItemDao.deleteCourseItemByTeacherId(teacher.getTeacherId());

            TeacherDao teacherDao = sqlSession.getMapper(TeacherDao.class);
            int result1 = teacherDao.deleteTeacher(teacher);
            if (result1 > 0) {
                result = true;
            }
        } catch (Exception e) {
            // 如果发生异常，那么关闭数据库连接，抛出异常
            sqlSession.close();
            throw e;
        }

        if (result) {
            sqlSession.commit();
        }
        sqlSession.close();
        return result;
    }

    @Override
    public String getTeacherListByPage(Page page) {

        // 存放分页后的学生信息(默认的数组大小是64)
        List<Teacher> teacherTemp = new ArrayList<Teacher>();

        // 通过页数获取不同页面的教师
        // 先获取教师全部数据的顺序表（方便分页）
        TeacherDao teacherDao = getSqlSession().getMapper(TeacherDao.class);
        List<Teacher> teacherList = teacherDao.getTeacher();

        // 定义Map
        Map<String, Object> jsonMap = new HashMap<String, Object>();

        for (int i = 0; i < page.getPageSize(); i++) {
            int range = i + page.getStart();
            if (range < teacherList.size()) {
                teacherTemp.add(teacherList.get(range));
            } else {
                break;
            }
        }
        // 存放学生总人数
        jsonMap.put("total", teacherList.size());

        // 存放某页的n行学生
        jsonMap.put("rows", teacherTemp);

        JsonConfig config = new JsonConfig();
        config.setExcludes(new String[]{"courses", "teacher"});
        // 格式化Map,以json格式返回数据
        String message = JSONObject.fromObject(jsonMap, config).toString();
        System.out.println(message);
        return message;
    }

    @Override
    public boolean insertTeacher(Teacher teacher) {
        boolean result = false;
        // 增加老师
        // (如果增加的老师中有课程安排的话，那么除了增加到教师表中，还要把教师对应的专业和课程添加到教师课程表中)
        try {
            TeacherDao teacherDao = getSqlSession().getMapper(TeacherDao.class);
            int result1 = teacherDao.insertTeacher(teacher);
            if (result1 > 0) {
                result = true;
            }

            // 查看新增加的教师中是否添加了任课课程
            List<CourseItem> courseList = teacher.getCourseList();

            if (!courseList.isEmpty()) {
                // 说明增加教师时也增加了任课课程，要添加到教师课程表中

                // 记录添加专业-课程-教师时受影响的行数 正常情况下等于 courseList 的大小
                int result2 = 0;
                CourseItemService courseItemService = new CourseItemServiceImpl();
                CourseItemDao courseItemDao = sqlSession.getMapper(CourseItemDao.class);

                // 采用 Lambda 操作
                // courseList.stream().forEach((courseItem) -> courseItemDao.insertCourseItemByTeacher(courseItem));


                Iterator<CourseItem> iterator = courseList.iterator();

                while (iterator.hasNext()) {
                    result2 += courseItemDao.insertCourseItem(iterator.next());
                }

                if (result2 != courseList.size()) {
                    result = false;
                }
            }
        } catch (Exception e) {
            // 如果发生异常，那么关闭数据库连接，抛出异常
            sqlSession.close();
            throw e;
        }

        if (result) {
            sqlSession.commit();
        }

        sqlSession.close();
        return result;
    }
}
