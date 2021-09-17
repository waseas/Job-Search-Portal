package com.me.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.me.dao.EmployeeDao;
import com.me.dao.StudentDao;
import com.me.dao.UserDao;
import com.me.exception.JobApplicationException;
import com.me.exception.JobDetailsException;
import com.me.exception.UserException;
import com.me.pojo.JobApplication;
import com.me.pojo.JobDetails;
import com.me.pojo.User;
import com.me.validator.JobDetailsValidator;

@Controller
public class EmployeeController {

	@Autowired
	@Qualifier("jobDetailsValidator")
	private JobDetailsValidator jobDetailsValidator;

	@InitBinder("job")
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(jobDetailsValidator);
	}

	@RequestMapping(value="/employer", method=RequestMethod.GET)
	public ModelAndView displayEmployerPage(ModelAndView modelAndView ,JobDetails jobDetails)
	{	modelAndView.addObject("job", jobDetails);
	modelAndView.setViewName("employer");
	return modelAndView;

	}

	@RequestMapping(value="/employer/postJob", method=RequestMethod.GET)
	public ModelAndView displayJobPosting(ModelAndView modelAndView ,JobDetails jobDetails)
	{	modelAndView.addObject("job", jobDetails);
	modelAndView.setViewName("postJob");
	return modelAndView;
	}

	@RequestMapping(value="/employer/postJob", method=RequestMethod.POST)
	public ModelAndView postJob(ModelAndView modelAndView ,@ModelAttribute("job") @Validated JobDetails jobDetails , BindingResult result , HttpServletRequest request,EmployeeDao employeeDao) throws JobDetailsException
	{	
		if (result.hasErrors()) {
			modelAndView.addObject("job", jobDetails);
			modelAndView.setViewName("postJob");
			return modelAndView;
		}
		HttpSession session = request.getSession(false);
		if(session != null) {
			User user = (User)session.getAttribute("userSession");
			if(user==null) {
				modelAndView.addObject("message","Please, Login into the system!");
				modelAndView.setViewName("error");
			}else {
				jobDetails.setUser(user);
				jobDetails = employeeDao.jobPosting(jobDetails);
				if (jobDetails != null) {
					modelAndView.addObject("message", "Job has been posted successfully!");
					modelAndView.setViewName("employer");
				} else {
					modelAndView.addObject("message", "Some issue occurred while posting the job. \n Please try again !");
					modelAndView.setViewName("postJob");
				}
			}
		}
		return modelAndView;
	}

	@RequestMapping(value="/employer/viewPost", method=RequestMethod.GET)
	public ModelAndView viewJobPosting(ModelAndView modelAndView ,HttpServletRequest request,EmployeeDao employeeDao) throws JobDetailsException
	{	HttpSession session = request.getSession(false);
	if(session != null) {
		User user = (User)session.getAttribute("userSession");
		if(user==null) {
			modelAndView.addObject("message","Please, Login into the system!");
			modelAndView.setViewName("error");
		}else {
			List<JobDetails> list = employeeDao.viewJobPosting(user);
			modelAndView.addObject("list" ,list);
			modelAndView.setViewName("employer-viewPost");
		}

	}
	return modelAndView ;
	}
	
	@RequestMapping(value="/employer/viewApplicants.htm", method=RequestMethod.GET)
	public ModelAndView viewApplicantsApplied(ModelAndView modelAndView ,HttpServletRequest request, UserDao userDao ,EmployeeDao employeeDao , JobDetails jobDetails,StudentDao studentDao) throws  JobApplicationException, UserException, JobDetailsException
	{	HttpSession session = request.getSession(false);
	User user = null;
	if(session != null) {
		user = (User)session.getAttribute("userSession");
		if(user==null) {
			modelAndView.addObject("message","Please, Login into the system!");
			modelAndView.setViewName("error");
		}else {
			Long id = Long.parseLong(request.getParameter("jobID"));
			boolean ifPosted = employeeDao.checkIfUserPostedJob(user, id) ;
			if(ifPosted) {
				List<JobApplication> jobApplications = studentDao.listOfApplicants(id);
				if(jobApplications==null || jobApplications.isEmpty()) {
					List<JobDetails> list = employeeDao.viewJobPosting(user);
					modelAndView.addObject("list" ,list);
					modelAndView.addObject("message", "No one applied for this job");
					modelAndView.setViewName("employer-viewPost");
				} else {
					List<User> users = new ArrayList<User>();
					for(JobApplication a : jobApplications) {
						Long userid = a.getUser().getUserId();							
						users.add(userDao.getUser(userid));

						modelAndView.addObject("listapp",jobApplications);
						modelAndView.addObject("listuser",users);
						modelAndView.setViewName("applicantsInfo");
					}
				}

			}else {
				List<JobDetails> list = employeeDao.viewJobPosting(user);
				modelAndView.addObject("list" ,list);
				modelAndView.addObject("message", "This Job does not belong to you.");
				modelAndView.setViewName("employer-viewPost");
			}
		}

	}
	return modelAndView ;
	}

	@RequestMapping(value="/employer/deleteJob", method=RequestMethod.GET)
	public ModelAndView JobPosting(ModelAndView modelAndView ,HttpServletRequest request,EmployeeDao employeeDao,StudentDao studentDao,UserDao userDao) throws JobDetailsException, JobApplicationException, UserException
	{	HttpSession session = request.getSession(false);
	User user ;
	if(session != null) {
		user = (User)session.getAttribute("userSession");
		if(user==null) {
			modelAndView.addObject("message","Please, Login into the system!");
			modelAndView.setViewName("unauthorizedaccess");
		}else {

			Long id = Long.parseLong(request.getParameter("jobID"));

			boolean isPosted = employeeDao.checkIfUserPostedJob(user,id);
			if(isPosted) {
				JobDetails job=	employeeDao.getJobDetails(id);
				studentDao.removeAllApplications(job);
				employeeDao.deleteJob(id);
				modelAndView.addObject("message", "Post has been deleted");
				modelAndView.setViewName("employer-message");

			}else {
				List<JobDetails> list = employeeDao.viewJobPosting(user);
				modelAndView.addObject("list" ,list);
				modelAndView.addObject("message", "This Job does not belong to you.");
				modelAndView.setViewName("employer-viewPost");
			}
		}	
	}
	return modelAndView ;
	}

	@RequestMapping(value="/employer/updateJobDetails.htm", method=RequestMethod.GET)
	public ModelAndView showUpdatePage(ModelAndView modelAndView ,HttpServletRequest request,EmployeeDao employeeDao , JobDetails jobDetails) throws JobDetailsException
	{	HttpSession session = request.getSession(false);
	if(session != null) {
		User user = (User)session.getAttribute("userSession");
		if(user==null) {
			modelAndView.addObject("message","Please, Login into the system!");
			modelAndView.setViewName("error");
		}else {
			Long id = Long.parseLong(request.getParameter("jobID"));
			boolean isPosted = employeeDao.checkIfUserPostedJob(user,id);
			if(isPosted) {
				jobDetails = employeeDao.getJobDetails(id);
				modelAndView.addObject("job", jobDetails);
				modelAndView.setViewName("updateJobPost");
			}else {
				List<JobDetails> list = employeeDao.viewJobPosting(user);
				modelAndView.addObject("list" ,list);
				modelAndView.addObject("message", "");
				modelAndView.setViewName("employer-viewPost");
			}
		}


	}
	return modelAndView ;
	}

	@RequestMapping(value="/employer/updateJob", method=RequestMethod.POST)
	public ModelAndView UpdateJobPosting(ModelAndView modelAndView ,HttpServletRequest request,EmployeeDao employeeDao ,@ModelAttribute("job") @Validated JobDetails jobDetails ,BindingResult result) throws JobDetailsException
	{	
		if (result.hasErrors()) {
			modelAndView.addObject("job", jobDetails);
			modelAndView.setViewName("updateJobPost");
			return modelAndView;
		}

		HttpSession session = request.getSession(false);
		if(session != null) {
			User user = (User)session.getAttribute("userSession");
			if(user==null) {
				modelAndView.addObject("message","Please, Login into the system!");
				modelAndView.setViewName("error");
			}else {
				Long id = Long.parseLong(request.getParameter("id"));
				jobDetails.setJobId(id);
				employeeDao.updateJob(jobDetails);

				List<JobDetails> list = employeeDao.viewJobPosting(user);
				modelAndView.addObject("list" ,list);
				modelAndView.addObject("message", "Job is Posted Successfully!");
				modelAndView.setViewName("employer-viewPost");
			}

		}
		return modelAndView ;
	}

	

}
