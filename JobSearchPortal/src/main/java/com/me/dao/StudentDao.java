package com.me.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.me.controller.HomeController;
import com.me.exception.JobApplicationException;
import com.me.pojo.JobApplication;
import com.me.pojo.JobDetails;
import com.me.pojo.User;

public class StudentDao extends DAO {

	public List<JobDetails> listAllJobs() throws JobApplicationException {
		try {
			begin();
			Query q = getSession().createQuery("from JobDetails");
			List<JobDetails> list = q.list();
			commit();
			return list;
		} catch (HibernateException e) {
			rollback();
			throw new JobApplicationException("Error while getting jobs" + e.getMessage());
		}
	}

	public boolean checkIfAlreadyApplied(User user, JobDetails jobdetails) throws JobApplicationException {
		try {
			boolean result = false;
			
			begin();
			Criteria criteria = getSession().createCriteria(JobApplication.class);
			criteria.add(Restrictions.eq("jobdetails", jobdetails));
			criteria.add(Restrictions.eq("user", user));
			List<JobApplication> results = criteria.list();
			commit();
			if (results.isEmpty() || results == null) {
				result = false;
			} else {
				result = true;
			}
			return result;
		} catch (HibernateException e) {
			rollback();
			throw new JobApplicationException("Error while fetching job for Userid - " + user.getUserId() + " : " + e);
		}
	}

	public void saveFiles(JobApplication jobApplication) throws JobApplicationException {
		try {

			begin();
			getSession().save(jobApplication);
			commit();
		} catch (HibernateException e) {
			rollback();
			throw new JobApplicationException("Error while saving Job Application : " + e);
		}
	}

	public List<JobApplication> listAllAppliedJobs(User user) throws JobApplicationException {
		try {

			begin();
			Criteria c = getSession().createCriteria(JobApplication.class);
			c.add(Restrictions.eq("user", user));
			List<JobApplication> list = c.list();
			commit();
			return list;
		} catch (HibernateException e) {
			rollback();

			throw new JobApplicationException(
					"Error while fetching Job Application details for User - " + user.getUserId() + " : " + e);
		}
	}

	public void deleteApplication(JobDetails job, User user) throws JobApplicationException {
		try {

			begin();
			Query q = getSession().createQuery("delete from JobApplication where jobdetails = :job AND user = :user");
			q.setParameter("job", job);
			q.setParameter("user", user);
			q.executeUpdate();
			commit();
			close();
		} catch (HibernateException e) {
			rollback();

			throw new JobApplicationException("Error while deleting Job Application : " + e);
		}
	}

	public List<JobApplication> listOfApplicants(long jobId) throws JobApplicationException {
		try {
			begin();
			Query query = getSession().createQuery("from JobApplication where jobdetails= '" + jobId + "' ");
			List<JobApplication> list = query.list();
			commit();
			return list;
		} catch (HibernateException e) {
			rollback();
			throw new JobApplicationException(
					"Could not fetch the list for applicants for job Id :" + jobId + " : " + e.getMessage());
		}
	}
	
	public int removeAllApplications(JobDetails jobDetails) throws JobApplicationException {
		try {
			begin();
			Query query = getSession().createQuery("delete from JobApplication where jobdetails= '" + jobDetails.getJobId() + "' ");
			int i =query.executeUpdate();
			commit();
			close();
			return i;
		} catch (HibernateException e) {
			rollback();
			throw new JobApplicationException(
					"Could not fetch the list for applicants for job Id :" + jobDetails.getJobId() + " : " + e.getMessage());
		}
	}

}
