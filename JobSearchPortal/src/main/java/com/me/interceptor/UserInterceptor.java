package com.me.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.me.pojo.User;

public class UserInterceptor extends HandlerInterceptorAdapter {

	String errorPage;

	public String getErrorPage() {
		return errorPage;
	}

	public void setErrorPage(String errorPage) {
		this.errorPage = errorPage;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		HttpSession session = (HttpSession) request.getSession();
		User user = (User) session.getAttribute("userSession");
		if(user != null){
			return true;
		}
		
		System.out.println("no user");
		response.sendRedirect(errorPage);
		return false;
	}
}
