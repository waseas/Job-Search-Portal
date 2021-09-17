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
import com.me.exception.JobDetailsException;
import com.me.pojo.JobApplication;
import com.me.pojo.JobDetails;
import com.me.pojo.User;

public class EmployeeDao extends DAO {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeDao.class);

	public JobDetails jobPosting(JobDetails jobDetails) throws JobDetailsException {
		try {
			begin();
			getSession().save(jobDetails);
			commit();
			close();
			return jobDetails;
		} catch (HibernateException e) {
			rollback();
			throw new JobDetailsException(" Error while posting a Job " + e);
		}	
	}

	public List<JobDetails> viewJobPosting(User user) throws JobDetailsException {
		try {
			begin();
			Criteria c = getSession().createCriteria(JobDetails.class);
			c.add(Restrictions.eq("user", user));
			List<JobDetails> list = c.list();
			commit();
			return list;
		} catch (HibernateException e) {
			rollback();
			throw new JobDetailsException("Error while getting list of jobs for User : " + e);
		}
	}

	public JobDetails getJobDetails(Long id) throws JobDetailsException {
		try {
			getSession().clear();
			begin();
			Criteria c = getSession().createCriteria(JobDetails.class);
			c.add(Restrictions.eq("jobId", id));
			c.setMaxResults(1);
			JobDetails jobDetails = (JobDetails) c.uniqueResult();
			commit();
			close();
			return jobDetails;
		} catch (HibernateException e) {
			rollback();
			throw new JobDetailsException("Error while getting job Details of id = " + id + " : " + e);
		}
	}

	public boolean deleteJob(Long id) throws JobDetailsException {
		try {
			getSession().flush();
			getSession().clear();
			begin();
			Query q = getSession().createQuery("delete from JobDetails where jobId = :id");
			q.setLong("id", id);
			q.executeUpdate();
			commit();
			close();
			return true;
		} catch (HibernateException e) {
			rollback();
			throw new JobDetailsException("Error while deleting job Details of id = " + id + " : " + e);
		}
	}

	public JobDetails updateJob(JobDetails job) throws JobDetailsException {
		try {
			getSession().flush();
			getSession().clear();
			logger.info("Going to update job with id : " + job.getJobId());
			begin();
			Query q = getSession().createQuery(
					"update JobDetails set title = :t , companyName = :c ,typeOfJob = :type , country = :country , state = :state , industry = :industry , major = :major , minGpa = :minGpa , jobUrl = :jobUrl , description = :description where jobId = :id");
			q.setString("t", job.getTitle());
			q.setString("c", job.getCompanyName());
			q.setString("type", job.getTypeOfJob());
			q.setString("country", job.getCountry());
			q.setString("state", job.getState());
			q.setString("industry", job.getIndustry());
			q.setString("major", job.getMajor());
			q.setFloat("minGpa", job.getMinGpa());
			q.setString("jobUrl", job.getJobUrl());
			q.setString("description", job.getDescription());
			q.setLong("id", job.getJobId());
			q.executeUpdate();
			commit();
			return job;
		} catch (HibernateException e) {
			rollback();
			throw new JobDetailsException("Error while updating job Details of id = " + job.getJobId() + " : " + e);
		}
	}

	public boolean checkIfUserPostedJob(User user, long id) throws JobDetailsException {

		try {
			getSession().clear();
			boolean result = false;
			begin();
			Criteria c = getSession().createCriteria(JobDetails.class);
			c.add(Restrictions.eq("user", user));
			c.add(Restrictions.eq("jobId", id));
			c.setMaxResults(1);
			JobDetails job = (JobDetails) c.uniqueResult();
			if (job != null) {
				result = true;
			} else {
				result = false;
			}
			return result;
		} catch (HibernateException e) {
			rollback();
			throw new JobDetailsException("Error while updating job Details of id = " + id + " : " + e);
		}

	}

}
