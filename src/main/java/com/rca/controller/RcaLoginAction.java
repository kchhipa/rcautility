package com.rca.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.rca.dao.LoginDAO;
import com.rca.dao.ProjectDAO;
import com.rca.entity.RcaCount;
import com.rca.service.RcaManager;

public class RcaLoginAction extends ActionSupport implements SessionAware {

	private String userName;
	private String passWord;
	private List projectList;
	private Map projectNameWithId;
	public SessionMap session;
	
	//Employee manager injected by spring context
		private RcaManager rcaManager;
	
	public String execute()
	{
		String result = LoginDAO.validateUser(userName,passWord);
		RcaCount rca = rcaManager.findById(304);
		
		List<RcaCount> rcas = rcaManager.getRCACounts();
		
		List<RcaCount> rcaWeeks = rcaManager.findRCAfromWeekPeriod("9/29/2014-10/5/2014");
		
		RcaCount projectReport = rcaManager.findWeeklyRCAReportByProjectId("9/29/2014-10/5/2014", 1);
		
		
	   if(result.equals("success"))
	   {			
			  getProjectDetails();			
	   }
	   else{
 			addActionMessage("incorrect username or password");
	   }
		
		return result;
	}
	
	public void getProjectDetails()
	{
		try {			
				Properties properties = new Properties();
				properties.load(getClass().getResourceAsStream("rca.properties"));
				userName = userName.toUpperCase();
				String projects = properties.getProperty(userName);
				if(projects!=null && projects.length()!=0)
				{
					String projectArray[] = projects.split(",");
					projectList = Arrays.asList(projectArray);					
					projectNameWithId = new TreeMap<Integer, String>();
					ProjectDAO.getProjectIds(projectNameWithId,projectList);
				}
				session.put("projectNameWithId", projectNameWithId);
			
		} catch (IOException e) {
			 
			e.printStackTrace();
		}
	}
	
	public String logoutRca()
	{		
		if(session!=null)
		{
			session.invalidate();
			addActionMessage("Logged out successfully");
		}
		return "success";
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public List getProjectList() {
		return projectList;
	}
	public void setProjectList(List projectList) {
		this.projectList = projectList;
	}
	public Map getProjectNameWithId() {
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

	public RcaManager getRcaManager() {
		return rcaManager;
	}

	public void setRcaManager(RcaManager rcaManager) {
		this.rcaManager = rcaManager;
	}
	
	
}
