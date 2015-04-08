package com.rca.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;
import org.jfree.util.Log;
import org.junit.runner.notification.Failure;

import com.opensymphony.xwork2.ActionSupport;
import com.rca.dao.LoginDAO;
import com.rca.dao.LoginDetailsDAO;
import com.rca.dao.ProjectDAO;
import com.rca.entity.LoginDetails;
import com.rca.entity.RcaCount;
import com.rca.service.LoginDetailService;
import com.rca.service.RcaManager;

public class RcaLoginAction extends ActionSupport implements SessionAware {

	private String userName;
	private String passWord;
	private List projectList;
	private Map projectNameWithId;
	private List<LoginDetails> loginDetailsList;
	public SessionMap session;
	public String role; 

	
	//Employee manager injected by spring context
		private RcaManager rcaManager;
		private LoginDetailService loginDetailsService;
		public static final String Manger = "manager";
		public static final String Lead = "lead";
	
	public String execute() throws FileNotFoundException, IOException, URISyntaxException
	{
		String result = LoginDAO.validateUser(userName,passWord);
		RcaCount rca = rcaManager.findById(304);
		
		List<RcaCount> rcas = rcaManager.getRCACounts();
		
		List<RcaCount> rcaWeeks = rcaManager.findRCAByWeekPeriod("9/29/2014-10/5/2014");
		
		RcaCount projectReport = rcaManager.findWeeklyRCAReportByProjectId("9/29/2014-10/5/2014", 1);
		
     /*  String path = System.getProperty("rcaUtility.config");
        
        Properties prop = new Properties();
        prop.load(new FileInputStream(new File(new java.net.URI(path))));
        String weekInterval = prop.getProperty("rca.weekinterval");
		*/
		
		
	   if(result.contains("success"))
	   {		
		   if(result.contains(Manger)){
			   setRole(Manger);
			   session.put("role", Manger);
		   }
		   else if(result.contains(Lead))
		   {
			   session.put("role", Lead);
		   }
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
	
	public String showUserDetails()
	{	
	 try
	 {
		loginDetailsList =  loginDetailsService.getLoginDetail();		
		Collections.sort(loginDetailsList, new loginNameComparator());		
		return SUCCESS;
	 }
	 catch(Exception e)
	 {
		 Log.info("Problem in getting login details " +e);
		 return ERROR;
	 }
	}
	
	public String userView()
	{		
		return SUCCESS;
	}
	
	public String addUser()
	{
		try
		{
			LoginDetails loginDetail = new LoginDetails();
			loginDetail.setLoginId(userName);
			loginDetail.setPassword(passWord);
			loginDetail.setRole(role);
			loginDetailsService.saveOrUpdateLoginDetails(loginDetail);
			addActionMessage("Save or updated successfully");
			return SUCCESS;
		}
		catch(Exception e)
		{
			 Log.info("Problem in adding user " +e);
			 addActionError("Problem in adding user " +e.getMessage());
			 return ERROR;
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
	
	public String homeRca()
	{	return "success";
	}

	public static class loginNameComparator implements Comparator<LoginDetails>
	{

		@Override
		public int compare(LoginDetails loginDetails1, LoginDetails loginDetails2) {
			return loginDetails1.getPassword().compareToIgnoreCase(loginDetails2.getPassword());
		}
		
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
	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

    public LoginDetailService getLoginDetailsService() {
		return loginDetailsService;
	}



	public void setLoginDetailsService(LoginDetailService loginDetailsService) {
		this.loginDetailsService = loginDetailsService;
	}



	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}



	public List<LoginDetails> getLoginDetailsList() {
		return loginDetailsList;
	}



	public void setLoginDetailsList(List<LoginDetails> loginDetailsList) {
		this.loginDetailsList = loginDetailsList;
	}
	
}
