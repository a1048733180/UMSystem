package com.luo.service.impl;

import com.luo.dao.CourseItemDao;
import com.luo.dao.impl.CourseItemDaoImpl;
import com.luo.entity.Course;
import com.luo.entity.CourseItem;
import com.luo.entity.CourseItem;
import com.luo.entity.Profession;
import com.luo.service.CourseItemService;

public class CourseItemServiceImpl implements CourseItemService {
    CourseItemDao courseItemDao;


    public CourseItemServiceImpl() {
        this.courseItemDao = new CourseItemDaoImpl();
    }

    @Override
    public void insertCourseItem(CourseItem courseItem) {
        // 添加教师专业课程对应对象
        Profession profession = new Profession();
        Course course = new Course();

        profession.setId(courseItem.getProfessionId());
        course.setId(courseItem.getCourseId());

        courseItemDao.insertCourseItemByTeacher(course, courseItem.getTeacher(), profession);
    }

    @Override
    public void alertCourseItem(CourseItem[] courseItem, int teacherId) {

        courseItemDao.alertCourseItem(courseItem, teacherId);
    }

    @Override
    public void deleteCourseItem(int teacherId) {

        courseItemDao.deleteCourseItemByTeacherId(teacherId);
    }
}
