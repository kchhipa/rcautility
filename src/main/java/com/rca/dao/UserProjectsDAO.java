package com.rca.dao;

import org.hibernate.HibernateException;

import com.rca.entity.UserProjects;

public interface UserProjectsDAO {

	public UserProjects getProjectOfUserDaoById(String login_id) throws HibernateException;
	
	public void updateProjectToUserDao(UserProjects userProjects) throws HibernateException;
	
	public String deleteUserDao(String login_id) throws HibernateException;
	
}
