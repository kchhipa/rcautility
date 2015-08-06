package com.rca.controller;

import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;
import org.hibernate.HibernateException;
import com.opensymphony.xwork2.ActionSupport;
import com.rca.common.RCAConstants;
import com.rca.entity.ProjectDetails;
import com.rca.service.UserProjectsServiceImpl;

public class UserProjectsAction extends ActionSupport implements SessionAware {

	private UserProjectsServiceImpl userProjectsServiceImpl;
	private Map<String,String> projectNameWithId;
	private SessionMap session;	
	private String userName;
	private String projectName;
	private ArrayList<ProjectDetails> projectDetailsList;
	private int projectId;
	private String actionTeam;
	private InputStream inputStream;
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
	
	// This method used to get all project with team name 
	
	public String getProjectWithTeam()
	{
		projectDetailsList = (ArrayList<ProjectDetails>) userProjectsServiceImpl.getProjectWithTeamService();
		return SUCCESS;
	}
	
	public String updateTeamName()
	{
		try
		{
			int result = userProjectsServiceImpl.updateTeamNameService(projectId, actionTeam);
			if(result!=0)
				addActionMessage(RCAConstants.UserProjectsMessage.TEAMNAME_UPDATE_SUCCESS);
			
			projectDetailsList = (ArrayList<ProjectDetails>) userProjectsServiceImpl.getProjectWithTeamService();
			inputStream = new StringBufferInputStream(SUCCESS);
		}
		catch(HibernateException re)
		{
			addActionError("Exception in updating team name");
			inputStream = new StringBufferInputStream(ERROR);
		}
		return SUCCESS;
	}
	
	@SuppressWarnings("deprecation")
	public String getTeamNameForProject()
	{
		String actionTeam  = "";
		try
		{
			actionTeam = userProjectsServiceImpl.getTeamNameForProjectService(projectId);
			if(actionTeam==null)
				actionTeam = "";
			
			inputStream = new StringBufferInputStream(actionTeam);
		}
		catch(HibernateException he)
		{
			addActionError("Exception in getting team name");
		}	
		return SUCCESS;
	}
	
	public static final Comparator<ProjectDetails> byProjectName = new Comparator<ProjectDetails>() {
		@Override
		public int compare(ProjectDetails p1, ProjectDetails p2) {
			String firstProject = p1.getProjectName();
			String secondProject = p2.getProjectName();
			if(firstProject == null)
				firstProject = "";
			if(secondProject == null)
				secondProject = "";
			
			return firstProject
					.compareTo(secondProject);
		}
	};
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

	public ArrayList<ProjectDetails> getProjectDetailsList() {
		Collections.sort(projectDetailsList,UserProjectsAction.byProjectName);
		return projectDetailsList;
	}

	public void setProjectDetailsList(ArrayList<ProjectDetails> projectDetailsList) {
		this.projectDetailsList = projectDetailsList;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public String getActionTeam() {
		return actionTeam;
	}

	public void setActionTeam(String actionTeam) {
		this.actionTeam = actionTeam;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	
	
}
