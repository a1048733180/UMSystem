package com.luo.controller;

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
import com.luo.tools.CodeTool;

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
		String account = request.getParameter("account");
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
			user.setAccount(account);
			user.setPassword(password);
			user.setType(type);
			if (userService.getAdmin(user) == null) {
				msg = "loginError";
			}else {
				if(user.getType() == user.USER_ADMIN) {
					msg = "admin";
				}else if(user.getType() == user.USER_STUDENT) {
					msg = "student";
				}else if(user.getType() == user.USER_TEACHER) {
					msg = "teacher";
				}
				
				// 把用户名存储到session中
				request.getSession().setAttribute("user", user);
			}
		}else {
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
