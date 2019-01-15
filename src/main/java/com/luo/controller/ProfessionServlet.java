package com.luo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.luo.entity.Course;
import com.luo.entity.Profession;
import com.luo.service.ProfessionService;
import com.luo.service.impl.ProfessionServiceImpl;

public class ProfessionServlet extends HttpServlet {

    ProfessionService professionService = new ProfessionServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 转发到专业列表
        String method = request.getParameter("method");
        if ("toProfessionListView".equalsIgnoreCase(method)) {
            request.getRequestDispatcher("/WEB-INF/view/other/professionList.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        System.out.println("进入了专业方法");
        String method = request.getParameter("method");

        if ("ProfessionList".equalsIgnoreCase(method)) {
            professionList(request, response);
        } else if ("AddProfession".equalsIgnoreCase(method)) {
            addProfession(request, response);
        } else if ("DeleteProfession".equalsIgnoreCase(method)) {
            deleteProfession(request, response);
        }
    }


    /**
     *  添加专业
     * @param request
     * @param response
     */
    private void addProfession(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String professionId = request.getParameter("professionId");
        String professionName = request.getParameter("prefessionName");
        String[] courseList = request.getParameterValues("courseList");

        Profession profession = new Profession();
        profession.setProfessionId(Integer.parseInt(professionId));
        profession.setProfessionName(professionName);

        List<Course> list = null;
        int courseListLength;
        if (courseList != null && (courseListLength = courseList.length) > 0) {
            list = new ArrayList<>(courseListLength);
            for (int i = 0; i < courseListLength; i++) {
                // 如果存在课程，则添加到课程集合中
                Course course = new Course();
                course.setCourseId(Integer.parseInt(courseList[i]));
                list.add(course);
            }
        }

        profession.setCourseList(list);

        try {
            professionService.insertProfession(profession);
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("fail");
        }

        response.getWriter().write("success");
    }

    /**
     * 专业列表
     * @param request
     * @param response
     * @throws IOException
     */
    public void professionList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("进入了professionList方法");
        String course = request.getParameter("course");

        String result = professionService.getProfessionList(course);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(result);
    }

    /**
     * 删除专业信息
     * @param request
     * @param response
     */
    private void deleteProfession(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String professionId = request.getParameter("professionId");
        Profession profession = new Profession();
        profession.setProfessionId(Integer.parseInt(professionId));
        try {
            boolean result = professionService.deleteProfession(profession);
            response.getWriter().write("success");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("fail");
        }

    }
}