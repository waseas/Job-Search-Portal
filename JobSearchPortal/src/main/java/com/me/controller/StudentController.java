package com.me.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.me.dao.EmployeeDao;
import com.me.dao.StudentDao;
import com.me.exception.JobApplicationException;
import com.me.exception.JobDetailsException;
import com.me.pojo.JobApplication;
import com.me.pojo.JobDetails;
import com.me.pojo.User;
import com.me.utilservices.DirectoryManager;
import com.me.utilservices.DownloadFile;

@Controller
public class StudentController {

	@RequestMapping(value = "/student", method = RequestMethod.GET)
	public ModelAndView displayEmployerPage(ModelAndView modelAndView) {
		modelAndView.setViewName("student");
		return modelAndView;

	}

	@RequestMapping(value = "/student/viewJobs", method = RequestMethod.GET)
	public ModelAndView viewAllJobs(HttpServletRequest request, StudentDao studentDao) throws Exception {
		User user = (User) request.getSession().getAttribute("userSession");
		try {
			request.getSession().setAttribute("name", user);
			List<JobDetails> jobDetails = studentDao.listAllJobs();
			request.getSession().setAttribute("jobID", jobDetails);
			return new ModelAndView("student-viewJob", "allJobs", jobDetails);
		} catch (JobApplicationException e) {
			System.out.println(e.getMessage());
			return new ModelAndView("student-message", "message", "Some Error Occured! Please Try again!");
		}

	}

	@RequestMapping(value = "/student/applyJob.htm", method = RequestMethod.GET)
	public ModelAndView showApplicationPage(ModelAndView modelAndView, HttpServletRequest request, EmployeeDao employeeDao,
			StudentDao studentDao) throws JobDetailsException, JobApplicationException {
		HttpSession session = request.getSession(false);
		if (session != null) {
			User user = (User) session.getAttribute("userSession");
			if (user == null) {
				modelAndView.addObject("message", "Please, Login into the system!");
				modelAndView.setViewName("unauthorizedaccess");
			} else {

				boolean ifAlreadyApplied = true;
				Long id = Long.parseLong(request.getParameter("jobID"));

				JobDetails jobdetails = employeeDao.getJobDetails(id);

				ifAlreadyApplied = studentDao.checkIfAlreadyApplied(user, jobdetails);
				if (ifAlreadyApplied == true) {
					List<JobDetails> jobDetails = studentDao.listAllJobs();
					request.getSession().setAttribute("jobID", jobDetails);
					modelAndView.addObject("error", "Already applied for this Job Posting!");
					modelAndView.setViewName("student-viewJob");
				} else {
					JobDetails job = null;
					try {
						job = employeeDao.getJobDetails(id);
					} catch (JobDetailsException e) {
						return new ModelAndView("student-message", "message",
								"Some Error Occured! Please Try again!");
					}
					modelAndView.addObject("job", job);
					modelAndView.addObject("id", id);
					modelAndView.addObject("files", new JobApplication());
					modelAndView.setViewName("applyJob");
				}
			}
		}
		return modelAndView;
	}

	@RequestMapping(value = "/student/applyJob", method = RequestMethod.POST)
	public ModelAndView applyJob(@ModelAttribute("files") JobApplication jobApplication, ModelAndView modelAndView, HttpServletRequest request,
			EmployeeDao employeeDao, StudentDao studentDao)
					throws JobApplicationException, JobDetailsException, IllegalStateException, IOException {
		HttpSession session = request.getSession(false);
		User user;
		if (session != null) {
			user = (User) session.getAttribute("userSession");
			if (user == null) {
				modelAndView.addObject("message", "Please, Login into the system!");
				modelAndView.setViewName("student-message");
			} else {

				Long id = Long.parseLong(request.getParameter("id"));
				JobDetails jobdetails = employeeDao.getJobDetails(id);

				DirectoryManager m = new DirectoryManager();
				String resumePath = m.createResumeJobFolder(request.getParameter("id"), user);
				String coverPath = m.createCoverJobFolder(request.getParameter("id"), user);

				CommonsMultipartFile resumeUploaded = (CommonsMultipartFile) jobApplication.getResume();
				String fileName = resumeUploaded.getOriginalFilename().replace(" ", "_");
				File localFile = new File(resumePath + File.separator + fileName);
				resumeUploaded.transferTo(localFile);
				request.getSession().setAttribute("JobID", jobdetails);

				// Going to set path for Cover Letter
				CommonsMultipartFile coverLetterUploaded = (CommonsMultipartFile) jobApplication.getCoverLetter();
				String coverName = coverLetterUploaded.getOriginalFilename().replace(" ", "_");

				try {

					File file1 = new File(coverPath, coverName);
					FileOutputStream fos = new FileOutputStream(file1);
					fos.write(jobApplication.getCoverLetter().getBytes());
					fos.close();
					fos.flush();

				} catch (Exception e) {
					e.printStackTrace();
				}
				boolean allowed = DownloadFile.checkExtension(localFile.getPath());
				boolean isAllowed= DownloadFile.checkExtension(coverPath + File.separator + coverName) ;
				if(!allowed || !isAllowed) {
					modelAndView.addObject("message", "Only files with extensions .pdf ,.doc ,.docx are Allowed!!");
					modelAndView.setViewName("student-message");
					return modelAndView;
				}else {
					jobApplication.setResumePath(localFile.getPath());
					jobApplication.setCoverLetterPath(coverPath + File.separator + coverName);
					jobApplication.setUser(user);
					jobApplication.setJobdetails(jobdetails);
					studentDao.saveFiles(jobApplication);
				}}
		}
		modelAndView.addObject("message", "Sucessfully Applied for job !!");
		modelAndView.setViewName("student-message");
		return modelAndView;

	}

	@RequestMapping(value = "/student/appliedJobs", method = RequestMethod.GET)
	public ModelAndView getAllAppliedJobs(ModelAndView modelAndView, HttpServletRequest request, JobApplication application,
			EmployeeDao empDao, StudentDao studentDao) throws JobApplicationException, JobDetailsException {
		HttpSession session = request.getSession(false);
		User user;
		if (session != null) {
			user = (User) session.getAttribute("userSession");
			if (user == null) {
				modelAndView.addObject("message", "Please, Login into the system!");
				modelAndView.setViewName("unauthorizedaccess");
			} else {
				List<JobApplication> jobApplications = studentDao.listAllAppliedJobs(user);
				List<JobDetails> jobs = new ArrayList<JobDetails>();

				for (JobApplication a : jobApplications) {
					long jobId = a.getJobdetails().getJobId();
					jobs.add(empDao.getJobDetails(jobId));
				}

				modelAndView.addObject("jobs", jobs);
				modelAndView.setViewName("student-appliedJobs");
			}
		}
		return modelAndView;
	}

	@RequestMapping(value = "/student/deleteApplication", method = RequestMethod.GET)
	public ModelAndView deleteApplication(ModelAndView modelAndView, HttpServletRequest request, JobApplication application,
			EmployeeDao empDao, StudentDao studentDao) throws JobApplicationException, JobDetailsException {

		HttpSession session = request.getSession(false);
		User user;
		if (session != null) {
			user = (User) session.getAttribute("userSession");
			if (user == null) {
				modelAndView.addObject("message", "Please, Login into the system!");
				modelAndView.setViewName("unauthorizedaccess");
			} else {

				Long id = Long.parseLong(request.getParameter("jobId"));
				JobDetails job = empDao.getJobDetails(id);
				boolean isApplied = studentDao.checkIfAlreadyApplied(user, job);
				if (isApplied) {
					studentDao.deleteApplication(job, user);

					modelAndView.addObject("message", "Application has been withdrawn");
					modelAndView.setViewName("student-message");
				} else {
					modelAndView.addObject("message", "Apllication does not belong to you!");
					modelAndView.setViewName("student-message");
				}
			}
		}
		return modelAndView;
	}

	@RequestMapping(value = "/student/downloadPdf", method = RequestMethod.GET)
	public ModelAndView downloadPdf(ModelAndView modelAndView, HttpServletRequest request, JobApplication application,
			EmployeeDao employeeDao, StudentDao studentDao) throws JobApplicationException, JobDetailsException {

		List<JobDetails> jobs = new ArrayList<JobDetails>();
		HttpSession session = request.getSession(false);
		User user;
		if (session != null) {
			user = (User) session.getAttribute("userSession");
			if (user == null) {
				modelAndView.addObject("message", "Kindly Login to post a Job");
				modelAndView.setViewName("unauthorizedaccess");
			} else {
				List<JobApplication> jobApplications = studentDao.listAllAppliedJobs(user);
				for (JobApplication a : jobApplications) {
					long jobId = a.getJobdetails().getJobId();
					jobs.add(employeeDao.getJobDetails(jobId));
				}
			}
		}
		return new ModelAndView("pdfView", "jobs", jobs);
	}

}