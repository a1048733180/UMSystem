package com.luo.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.luo.entity.Course;
import com.luo.entity.Profession;
import com.luo.service.CourseService;
import com.luo.service.impl.CourseServiceImpl;

public class CourseServlet extends HttpServlet {

    CourseService courseService = new CourseServiceImpl();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String method = request.getParameter("method");
        if ("toCourseListView".equalsIgnoreCase(method)) {
            request.getRequestDispatcher("/WEB-INF/view/other/courseList.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String method = request.getParameter("method");

        if ("CourseList".equalsIgnoreCase(method)) {
            courseList(request, response);
        } else if ("AddCourse".equalsIgnoreCase(method)) {
            addCourse(request, response);
        } else if ("DeleteCourse".equalsIgnoreCase(method)) {
            deleteCourse(request, response);
        }
    }


    private void addCourse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String courseId = request.getParameter("courseId");
        String courseName = request.getParameter("courseName");
        String courseNature = request.getParameter("courseNature");
        String courseHour = request.getParameter("courseHour");

        Course course = new Course();
        course.setCourseId(Integer.parseInt(courseId));
        course.setCourseName(courseName);

        if (!(courseNature == null || "".equals(courseNature.trim()))) {
            // 如果课程性质不为空，那么要设置对应的课程
            course.setCourseNature(courseNature);
        }
        if (!(courseHour == null || "".equals(courseHour.trim()))) {
            course.setCourseHour(Integer.parseInt(courseHour));
        }

        try {
            courseService.insertCourse(course);
            response.getWriter().write("success");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("fail");
        }
    }


    public void courseList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String professionId = request.getParameter("professionid");
        Profession profession = null;
        // 如果传进的专业为空 则获取全部专业列表
        if (!(professionId == null || "".equals(professionId.trim()))) {
            // 建一个可以从专业获取课程的方法，通过专业id获取全部课程id和name的list集合
            profession = new Profession();
            profession.setProfessionId(Integer.parseInt(professionId));
        }

        String result = courseService.getCourseList(profession);

        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(result);
    }

    private void deleteCourse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String courseId = request.getParameter("courseId");
        try {
            Course course = new Course();
            course.setCourseId(Integer.valueOf(courseId));
            boolean result = courseService.deleteCourse(course);
            if (result) {
                response.getWriter().write("success");
            } else {
                response.getWriter().write("fail");
            }
        } catch (Exception e) {
            response.getWriter().write("fail");
        }
    }
}
