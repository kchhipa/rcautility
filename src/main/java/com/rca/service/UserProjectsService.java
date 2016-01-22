package com.rca.service;

import org.hibernate.HibernateException;

public interface UserProjectsService {

	public String addProjectToUserService(String login_id, String projectName) throws HibernateException;
	
	public String deleteProjectOfUserService(String login_id, String projectName) throws HibernateException;
	
	public String deleteUserService(String login_id) throws HibernateException;
	public String getSprintNameForProjectService(int projectId)throws HibernateException;
}
