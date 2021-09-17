package com.me.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.me.dao.UserDao;
import com.me.exception.UserException;
import com.me.pojo.User;
import com.me.validator.UsersValidator;

@Controller
public class LoginController {

	@Autowired
	@Qualifier("usersValidator")
	private UsersValidator userValidator;

	@InitBinder("user")
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(userValidator);
	}

	@RequestMapping(value = "/accessdenied", method = RequestMethod.GET)
	public String loginerror(ModelMap model) {
		model.addAttribute("error", "true");
		return "denied";
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView displayRegistration(ModelAndView modelAndView) {
		modelAndView.addObject("user", new User());
		modelAndView.setViewName("register");
		return modelAndView;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView registerUser(ModelAndView modelAndView, @ModelAttribute("user") @Validated User user,BindingResult result, UserDao userDao, HttpServletRequest request) throws UserException, IOException, EmailException {
		if (result.hasErrors()) {
			modelAndView.addObject("user", user);
			modelAndView.setViewName("register");
			return modelAndView;
		}
		User existingUser;
		existingUser = userDao.findByUserName(user.getUsername());
		if (existingUser != null) {
			modelAndView.addObject("error", "User is already registered!");
			modelAndView.setViewName("register");
		} else {
			String role = request.getParameter("role");
			user = userDao.register(user, role);
			SendEmail(user);

			modelAndView.addObject("emailId", user.getUsername());
			modelAndView.setViewName("home");
		}
		return modelAndView;
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView displayLogin(ModelAndView mv) {
		mv.addObject("user", new User());
		mv.setViewName("login");
		return mv;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView getUser(@ModelAttribute("user") User user, ModelAndView modelAndView, HttpServletRequest request,
			UserDao userDao, BindingResult result) {
		HttpSession session = (HttpSession) request.getSession();
		String email = request.getParameter("username");
		String password = request.getParameter("password");
		User existingUser;
		try {
			existingUser = userDao.getUser(email, password);
			if (existingUser != null) {
				{
					if (existingUser.getUserRole().getRole().equalsIgnoreCase("ROLE_STUDENT")) {
						modelAndView.addObject("user", existingUser);
						modelAndView.setViewName("student");
						modelAndView.addObject("role", "student");
						session.setAttribute("userSession", existingUser);
					} else if (existingUser.getUserRole().getRole().equalsIgnoreCase("ROLE_EMPLOYEE")) {
						modelAndView.addObject("user", existingUser);
						modelAndView.setViewName("employer");
						modelAndView.addObject("role", "employer");
						session.setAttribute("userSession", existingUser);
					}
				}

			}else {
				modelAndView.addObject("error", "Incorrect credentials!! Please, Login with correct credentials");
				modelAndView.setViewName("login");

			}
		} catch (UserException e) {
			e.printStackTrace();
			modelAndView.addObject("message", "Please try again!");
			modelAndView.setViewName("message");

		}
		return modelAndView;

	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request, ModelAndView modelAndView) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			User user = (User) session.getAttribute("userSession");
			if (user == null) {
				modelAndView.addObject("message", "Please, Login into the system!");
				modelAndView.setViewName("/home");
			} else {
				request.getSession(false).invalidate();
				modelAndView.setViewName("/home");
			}
		}
		return modelAndView;

	}

	private void SendEmail(User user) throws EmailException{
		Email email = new SimpleEmail();
		email.setHostName("smtp.gmail.com");
		email.setSmtpPort(587);
		email.setAuthenticator(new DefaultAuthenticator("addyw425", "Love@123"));
		email.setSSLOnConnect(true);
		email.setFrom("jobportal@gmail.com");
		email.setSubject("Registration  complete");
		email.setMsg("Dear "+ user.getFirstName() + "," + "\n\n Your registration is completed. \n Username: " + user.getUsername() + "\n Password: " + user.getPassword() + "\n\n Thank You!");
		email.addTo(user.getUsername());
		email.send();    
	}
}
