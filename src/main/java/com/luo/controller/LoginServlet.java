package com.luo.controller;

import com.luo.tools.CodeTool;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.luo.entity.User;
import com.luo.service.UserService;
import com.luo.service.impl.UserServiceImpl;

public class LoginServlet extends HttpServlet {

    // 定义一个序列化的id
    private static final long serialVersionUID = 1L;

    private UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 获取验证码的请求
        String method = request.getParameter("method");

        if ("GetVCode".equalsIgnoreCase(method)) {
            getVCode(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 登陆功能
        String method = request.getParameter("method");

        if ("Login".equalsIgnoreCase(method)) {
            login(request, response);
        }

    }

    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 获取用户名
        String userName = request.getParameter("userName");
        // 获取密码
        String password = request.getParameter("password");
        // 获取验证码
        String vcode = request.getParameter("vcode");
        // 获取登陆权限
        int type = Integer.valueOf(request.getParameter("type"));

        // 返回消息
        String msg = "";

        // 检验验证码是否正确
        if (vcode.equalsIgnoreCase((String) request.getSession().getAttribute("vcode"))) {
            User user = new User();
            user.setUserName(userName);
            user.setPassword(password);
            user.setType(type);
            try {
                if (!userService.getAdmin(user)) {
                    msg = "loginError";
                } else {
                    if (user.getType() == User.USER_ADMIN) {
                        msg = "admin";
                    } else if (user.getType() == User.USER_STUDENT) {
                        msg = "student";
                    } else if (user.getType() == User.USER_TEACHER) {
                        msg = "teacher";
                    }

                    // 把用户名存储到session中
                    request.getSession().setAttribute("user", user);
                }
            } catch (Exception e) {
                msg = "loginError";
                response.getWriter().write(msg);
            }
        } else {
            msg = "vcodeError";
        }

        // 返回登陆检验后的结果
        response.getWriter().write(msg);

    }

    public void getVCode(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // 验证码生成器对象
        CodeTool codeGenrate = new CodeTool();

        // 获取验证码
        String vcode = codeGenrate.generatorVCode();

        // 放进session中
        request.getSession().setAttribute("vcode", vcode);

        // 制作验证码图像
        BufferedImage vImg = codeGenrate.generatorRotateVCodeImage(vcode, true);
        // 输出图像
        ImageIO.write(vImg, "gif", response.getOutputStream());

    }
}
