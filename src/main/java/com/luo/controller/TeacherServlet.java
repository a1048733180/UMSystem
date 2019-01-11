package com.luo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.luo.entity.CourseItem;
import com.luo.entity.Page;
import com.luo.entity.Teacher;
import com.luo.service.TeacherService;
import com.luo.service.impl.TeacherServiceImpl;

import org.apache.ibatis.exceptions.PersistenceException;

/**
 *  @author L99
 */
public class TeacherServlet extends HttpServlet {

    TeacherService teacherService = new TeacherServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 获取请求的方法
        String method = request.getParameter("method");
        if ("toTeacherListView".equalsIgnoreCase(method)) {
            // 转发到教师列表页
            request.getRequestDispatcher("/WEB-INF/view/teacher/teacherList.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 获取请求的方法
        String method = request.getParameter("method");

        // 请求分发
        // 获取所有教师数据
        if ("TeacherList".equalsIgnoreCase(method)) {
            teacherList(request, response);
        } else if ("AddTeacher".equalsIgnoreCase(method)) {
            addTeacher(request, response);
        } else if ("EditTeacher".equalsIgnoreCase(method)) {
            editTeacher(request, response);
        } else if ("DeleteTeacher".equalsIgnoreCase(method)) {
            deleteTeacher(request, response);
        }
    }

    public void teacherList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 获取分页参数
        int page = Integer.parseInt(request.getParameter("page"));
        int rows = Integer.parseInt(request.getParameter("rows"));
        String result = teacherService.getTeacherListByPage(new Page(page, rows));
        // 返回数据
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(result);
    }

    /**
     * 添加教师信息
     * @param request
     * @param response
     * @throws IOException
     */
    public void addTeacher(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Teacher teacher = new Teacher();
        String id = request.getParameter("teacherId");
        String name = request.getParameter("teacherName");
        String sex = request.getParameter("teacherSex");
        String entryYear = request.getParameter("entryYear");
        String tel = request.getParameter("teacherTel");
        String jobTitle = request.getParameter("jobTitle");
        // 获取教师的任课课程
        // 如果没有的话就先增加老师基本信息，课程以后可以修改
        String[] courses = request.getParameterValues("course[]");

        teacher.setTeacherId(Integer.parseInt(id));
        teacher.setTeacherName(name);
        teacher.setTeacherSex(sex);
        teacher.setEntryYear(entryYear);
        teacher.setTeacherTel(tel);
        teacher.setJobTitle(jobTitle);


        // 判断前端传来的数据是否含有课程，如果有的话添加到教师中的 courseList，没有的话默认 courseList（教师的属性） 为 null
        courseJudgment(teacher, courses);
        try {
            // 添加到教师表
            boolean result = teacherService.insertTeacher(teacher);
            if (result) {
                response.getWriter().write("success");
            } else {
                /* 这个错误可能是插入多个课程时，有重复课程或者因为其他原因没有成功插入数据库，那么返回fail */
                response.getWriter().write("fail");
            }
        } catch (PersistenceException e) {
            // 插入课程是通过课程名+专业名为主键  在数据库中不能重复，如果设置的课程已经存在，那么就会报错
            e.printStackTrace();
            response.getWriter().write("existed");
        } catch (Exception e) {
            /* 这个 fail 可能是一些异常导致的*/
            response.getWriter().write("fail");
            e.printStackTrace();
        }
    }

    /**
     * 判断类
     * 如果课程集合不为空，那么把课程集合分割得到的字符分别存放在 courseItem 中，并把 courseItem 对应的集合存放到 teacher 中的 courseList 中
     * @param teacher 教师
     * @param courses 课程集合 格式为对应的课程id （1_3）
     */
    private void courseJudgment(Teacher teacher, String[] courses) {
        if (courses != null) {
            List<CourseItem> teacherCourseList = new ArrayList<CourseItem>();
            CourseItem courseItem;
            for (int i = 0; i < courses.length && courses[i] != null; i++) {
                courseItem = new CourseItem();
                /* 分割字符，得出任课的专业和课程 */
                String[] a = courses[i].split("_");
                /* 专业id */
                courseItem.setProfessionId(Integer.parseInt(a[0]));
                /* 课程id */
                courseItem.setCourseId(Integer.valueOf(a[1]));
                /* 教师id */
                courseItem.setTeacherId(teacher.getTeacherId());

                teacherCourseList.add(courseItem);
            }
            /* 把courseItem 存入到 teacher 中*/
            teacher.setCourseList(teacherCourseList);
        }
    }

    /**
     * 修改教师信息
     * @param request
     * @param response
     * @throws IOException
     */
    public void editTeacher(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Teacher teacher = new Teacher();
        String id = request.getParameter("teacherId");
        String name = request.getParameter("teacherName");
        String sex = request.getParameter("teacherSex");
        String entryYear = request.getParameter("entryYear");
        String tel = request.getParameter("teacherTel");
        String jobTitle = request.getParameter("jobTitle");
        // 获取教师的任课课程
        // 如果没有的话就先增加老师基本信息，课程以后可以修改
        String[] courses = request.getParameterValues("course[]");

        teacher.setTeacherId(Integer.parseInt(id));
        teacher.setTeacherName(name);
        teacher.setTeacherSex(sex);
        teacher.setEntryYear(entryYear);
        teacher.setJobTitle(jobTitle);


        // 判断前端传来的数据是否含有课程，如果有的话添加到教师中的 courseList，没有的话默认 courseList（教师的属性） 为 null
        courseJudgment(teacher, courses);

        try {
            boolean result = teacherService.alertTeacher(teacher);
            if (result) {
                response.getWriter().write("success");
            } else {
                response.getWriter().write("fail");
            }
        } catch (Exception e) {
            response.getWriter().write("fail");
            e.printStackTrace();
        }
    }

    /**
     * 删除教师信息
     * @param request
     * @param response
     * @throws IOException
     */
    public void deleteTeacher(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String[] ids = request.getParameterValues("ids[]");
        for (int i = 0; i < ids.length; i++) {
            Teacher teacher = new Teacher();
            teacher.setTeacherId(Integer.parseInt(ids[i]));
            try {
                boolean result = teacherService.deleteTeacher(teacher);
                if(!result){
                   throw new Exception("删除教师失败");
                }
            } catch (Exception e) {
                response.getWriter().write("fail");
                e.printStackTrace();
            }
        }
        response.getWriter().write("success");
    }
}
