package com.rca.controller;

import java.util.Map;

import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;
import org.hibernate.HibernateException;

import com.opensymphony.xwork2.ActionSupport;
import com.rca.common.RCAConstants;
import com.rca.service.UserProjectsServiceImpl;

public class UserProjectsAction extends ActionSupport implements SessionAware {

	private UserProjectsServiceImpl userProjectsServiceImpl;
	private Map<String,String> projectNameWithId;
	private SessionMap session;	
	private String userName;
	private String projectName;
	
	public String projectView()
	{	
		return SUCCESS;
	}
	
	public String assignProject()
	{
		try
		{
			String result = userProjectsServiceImpl.addProjectToUserService(userName,projectName);
			if(result.equals(SUCCESS))
				addActionMessage(RCAConstants.UserProjectsMessage.ADD_SUCCESS);
			else if(result.equals(RCAConstants.UserProjectsMessage.ALLREADY_ASSIGNED))
				addActionMessage(RCAConstants.UserProjectsMessage.ALLREADY_ASSIGNED);
			else if(result.equals(RCAConstants.UserProjectsMessage.NO_USER))
				addActionMessage(RCAConstants.UserProjectsMessage.NO_USER);
		}
		catch(HibernateException re)
		{
			addActionError("Exception in assigning project");
		}
		return SUCCESS;
	}
	
	public String deleteProject()
	{
		try
		{
			String result = userProjectsServiceImpl.deleteProjectOfUserService(userName,projectName);
			if(result.equals(SUCCESS))
				addActionMessage(RCAConstants.UserProjectsMessage.DELETE_SUCCESS);
			else if(result.equals(RCAConstants.UserProjectsMessage.NOT_ASSIGNED))
				addActionMessage(RCAConstants.UserProjectsMessage.NOT_ASSIGNED);
			else if(result.equals(RCAConstants.UserProjectsMessage.NO_USER))
				addActionMessage(RCAConstants.UserProjectsMessage.NO_USER);
		}
		catch(HibernateException re)
		{
			addActionError("Exception in deleting project");
		}
		return SUCCESS;
	}
	
	public String deleteUser()
	{
		try
		{
			String result = userProjectsServiceImpl.deleteUserService(userName);
			if(result.equals(SUCCESS))
				addActionMessage(RCAConstants.UserProjectsMessage.DELETE_USER_SUCCESS);
			else
				addActionMessage(RCAConstants.UserProjectsMessage.NO_USER);
			
		}
		catch(HibernateException re)
		{
			addActionError("Exception in deleting user");
		}
		return SUCCESS;
	}
	
	
	public Map getProjectNameWithId() {
		projectNameWithId=(Map) session.get("projectNameWithId");
		return projectNameWithId;
	}
	public void setProjectNameWithId(Map projectNameWithId) {
		this.projectNameWithId = projectNameWithId;
	}

	public Map getSession() {
		return session;
	}

	public void setSession(Map session) {
		this.session = (SessionMap)session;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}	

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public void setUserProjectsServiceImpl(UserProjectsServiceImpl userProjectsServiceImpl) {
		this.userProjectsServiceImpl = userProjectsServiceImpl;
	}
	
}
