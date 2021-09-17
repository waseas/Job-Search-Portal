package com.me.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.me.exception.UserException;
import com.me.pojo.UserRole;
import com.me.pojo.User;

@Component
public class UserDao extends DAO {

	public UserDao() {
		super();
	}

	public User getUser(String email ,String password) throws UserException {
		try {
			begin();
			Query q = getSession().createQuery("from User where username = :email and password = :password");
			q.setString("email", email);
			q.setString("password", password);
			User user = (User) q.uniqueResult();
			commit();
			return user;
		} catch (HibernateException e) {
			rollback();
			throw new UserException("Could not get user " + email, e);
		}
	}

	public User register(User u,String r) throws UserException {
		try {
			begin();
			UserRole role = new UserRole(r);
			User newUser = new User(u.getUsername(), u.getPassword(), true, u.getFirstName(),u.getLastName());
			newUser.setUserRole(role);
			role.setUser(newUser);
			u.setEnabled(true);
			getSession().save(newUser);
			getSession().save(role);
			commit();
			close();
			return u;
		}catch (HibernateException e) {
			rollback();
			throw new UserException("Exception while creating user: " + e.getMessage());
		}
	}

	public User findByUserName(String email) throws UserException{
		try {
			begin();
			Query q = getSession().createQuery("from User where username = :email");
			q.setString("email" , email);
			q.setMaxResults(1);
			User user = (User) q.uniqueResult();
			commit();
			return user ; 
		}catch (HibernateException e) {
			rollback();
			throw new UserException("Exception while getting user with email " +email + " : " + e.getMessage());
		}

	}

	public User getUser(long id) throws UserException{
		try {
			begin();
			Criteria  cr = getSession().createCriteria(User.class);
			cr.add(Restrictions.eq("userId" , id));
			cr.setMaxResults(1);
			User u  = (User) cr.uniqueResult();
			commit();
			return u;
		}catch (HibernateException e) {
			rollback();
			throw new UserException("Exception while fetching User info for user id -" +id+ " : " +e.getMessage());
		}
	}
}
