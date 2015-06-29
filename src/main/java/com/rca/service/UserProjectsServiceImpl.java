package com.rca.service;

import org.hibernate.HibernateException;

import com.rca.common.RCAConstants;
import com.rca.dao.UserProjectsDAO;
import com.rca.dao.UserProjectsDAOImpl;
import com.rca.entity.UserProjects;

public class UserProjectsServiceImpl implements UserProjectsService{

	private UserProjectsDAO userProjectsDAO;
	private String result = null;
	@Override
	public String addProjectToUserService(String login_id, String projectName) throws HibernateException {
			
		UserProjects userProjects  = userProjectsDAO.getProjectOfUserDaoById(login_id);
		String projects = null;
		if(userProjects !=null)
		{
			projects = userProjects.getProjects();
			if(projects == null || projects == "")
			{
				projects = projectName;
				userProjects.setProjects(projects);
				userProjectsDAO.updateProjectToUserDao(userProjects);
				return RCAConstants.SUCCESS;
			}
			else if(!projects.contains(projectName))
			{
				projects = projects+","+projectName;
				userProjects.setProjects(projects);
				userProjectsDAO.updateProjectToUserDao(userProjects);
				return RCAConstants.SUCCESS;
			}
			else
			{
				return RCAConstants.UserProjectsMessage.ALLREADY_ASSIGNED;
			}
			
		}
		else
		{
			return RCAConstants.UserProjectsMessage.NO_USER;
		}
	}

	@Override
	public String deleteProjectOfUserService(String login_id, String projectName) throws HibernateException {
				
		UserProjects userProjects  = userProjectsDAO.getProjectOfUserDaoById(login_id);
		String projects = null;
		if(userProjects !=null)
		{
			projects = userProjects.getProjects();
			String projectNameWithComma = ","+projectName;
			if(projects.contains(projectNameWithComma))
			{
				projects = projects.replace(projectNameWithComma, "");
				userProjects.setProjects(projects);
				userProjectsDAO.updateProjectToUserDao(userProjects);
				return RCAConstants.SUCCESS;
			}
			else if(projects.contains(projectName))
			{
				projects = projects.replace(projectName, "");
				userProjects.setProjects(projects);
				userProjectsDAO.updateProjectToUserDao(userProjects);
				return RCAConstants.SUCCESS;
			}
			else
			{
				return RCAConstants.UserProjectsMessage.NOT_ASSIGNED;
			}
			
		}
		else
		{
			return RCAConstants.UserProjectsMessage.NO_USER;
		}
				
	}

	@Override
	public String deleteUserService(String login_id) throws HibernateException {
		
		
			result = userProjectsDAO.deleteUserDao(login_id);
			return result;
		
	}

	
	public void setUserProjectsDAOImpl(UserProjectsDAO userProjectsDAO) {
		this.userProjectsDAO = userProjectsDAO;
	}

}
