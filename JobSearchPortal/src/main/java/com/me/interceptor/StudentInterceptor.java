package com.me.interceptor;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.me.pojo.User;

public class StudentInterceptor extends HandlerInterceptorAdapter {
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
		System.out.println("*************Inside Student Interceptor**************");
		User user = (User) session.getAttribute("userSession");
		if(user != null && user.getUserRole().getRole().equalsIgnoreCase("ROLE_STUDENT")){
			System.out.println("*****************Returing true******************");
			return true;
		}
		
		System.out.println("*****************no user******************");
		
		RequestDispatcher rd=request.getRequestDispatcher(errorPage);  
		rd.forward(request, response);
		return false;
	}

}
