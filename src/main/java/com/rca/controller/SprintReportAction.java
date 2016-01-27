package com.rca.controller;

import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;
import org.hibernate.exception.ConstraintViolationException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opensymphony.xwork2.ActionSupport;
import com.rca.common.RCAConstants;
import com.rca.entity.ProjectDetails;
import com.rca.entity.SprintReport;
import com.rca.entity.SprintReportBean;
import com.rca.service.ProjectDetailsManager;
import com.rca.service.SprintReportManager;

public class SprintReportAction extends ActionSupport implements  SessionAware {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5225872790000166576L;
	private SprintReportBean sprintReport;
	private int project_id;
	public static Map session;
	boolean isdisabled = false;
	private String weekStr = null;
	private static Map projectNameWithId;
	private InputStream inputStream;
	
	private SprintReportManager sprintReportManager;
	private ProjectDetailsManager projectDetailsManager;
	
	private String sprintName;
	private int teamCapacity, devMembers, qaMembers;
	private Date sprintStartdt, sprintEnddt;
	private Integer spCommitted;
	private Integer spDelivered;
	private Integer spAddedInMid;
	private String isKanbanFollowed;
	
	// persist spring report data into the sprint_report database table
	public String submitSprintReport(){
		String result = null;
			try{
				ProjectDetails projectDetails = projectDetailsManager.findProjectDetailsByIdWithoutRcaCount(project_id);
				SprintReport sr=new SprintReport();
				sr.setProjectDetails(projectDetails);
				SimpleDateFormat simpledata = new SimpleDateFormat("MM/dd");
				String startDate= simpledata.format(sprintReport.getStartDate());
				String endDate= simpledata.format(sprintReport.getEndDate());
				if(sprintReport.getIsKanbanFollowed().equals("No"))
				sr.setSprintName(sprintReport.getSprintName()+"("+startDate+"-"+endDate+")");
				else if((sprintReport.getIsKanbanFollowed().equals("Yes")))
				sr.setSprintName(startDate+"-"+endDate);
				sr.setStartDate(sprintReport.getStartDate());
				sr.setEndDate(sprintReport.getEndDate());
				if(sprintReport.getSpCommitted()==null){
				  sr.setSpCommitted(0);
				}
				else{
				sr.setSpCommitted(sprintReport.getSpCommitted());
				}
				if(sprintReport.getSpDelivered()==null){
				  sr.setSpDelivered(0);
				}
				else{
				sr.setSpDelivered(sprintReport.getSpDelivered());
				}
				if(sprintReport.getSpAddedInMid()==null){
				sr.setSpAddedInMid(0);
				}
				else{
				sr.setSpAddedInMid(sprintReport.getSpAddedInMid());
				}
				sr.setDevMembers(sprintReport.getDevMembers());
				sr.setQaMembers(sprintReport.getQaMembers());
				sr.setTeamCapacity(sprintReport.getTeamCapacity());
				sr.setIsKanbanFollowed(sprintReport.getIsKanbanFollowed());
				result=sprintReportManager.persistSprintReport(sr);
				if(result.equals(RCAConstants.SUCCESS)){
					addActionMessage("Sprint Report added successfuly");
					
				}else{
					addActionMessage("Sprint data already submitted");
				}
				this.sprintReport=new SprintReportBean();
				project_id=0;
				result=SUCCESS;
			}catch(ConstraintViolationException ex){
		       ex.printStackTrace();
		       addActionError("Sprint data already submitted");
			   project_id=0;
			   result=ERROR;
		    }catch(Exception ex){
				ex.printStackTrace();
			} 
			
		
		return result;
	}
	
	public String getSprintReportDetails() throws SQLException{
		
		//weekStr=sprintReport.getWeek();
		//sprintReport= sprintReportManager.findWeeklySprintReportByProjectId(weekStr, project_id);
		/*if(sprintReport==null){
			sprintReport=new SprintReport();
			sprintReport.setWeek(weekStr);
		}
		
		getWeekDates(weekStr);*/
		return SUCCESS;
		
	}
	
	/*public String getSprintDevQAMembers() throws SQLException{
			
		sprintReport= sprintReportManager.findWeeklySprintReportByProjectId(weekStr, project_id);
		if(sprintReport==null){
			sprintReport=new SprintReport();
		}			
	    inputStream = new StringBufferInputStream(sprintReport.getDevMembers()+"_"+ sprintReport.getQaMembers());
		return SUCCESS;
		
	}*/
	
	public String reportSprintView() throws SQLException{
		return SUCCESS;
	}
	
	public String updateTeamNames() throws SQLException{
      return SUCCESS;
  }
	
	private void getWeekDates(String weekdates)
	{
		String dates[] = null;
		Date currentDate = new Date();
		long diffDays=0;
		if(weekdates!=null && !weekdates.isEmpty()){
			if(weekdates.contains("-"))
			{
				dates = weekdates.split("-");	
			    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
				try {			 
						Date date = formatter.parse(dates[1]);								
						long diff = currentDate.getTime() - date.getTime();			 		
						diffDays = diff / (24 * 60 * 60 * 1000);
				}
				catch (ParseException e)
				{
					e.printStackTrace();
				}
				String role = (String) session.get("role");
				if(diffDays<2 || role.equals("manager"))
					isdisabled = false;
			    String y1=dates[0].substring(0,dates[0].lastIndexOf('/'));
			    String y2=dates[1].substring(0,dates[1].lastIndexOf('/'));
				weekStr = y1+"-"+y2;
			}
		}
	}
	
	public static List<String> findWeeks(){
	    List<String> weeks = new ArrayList<String>();
	    SimpleDateFormat formatter = new SimpleDateFormat("M/d/yyyy");

	    Calendar c1 = Calendar.getInstance();
	    c1.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
	    c1.add(Calendar.WEEK_OF_MONTH, -1);
	    for (int i = 0; i < 12; i++) {
	          String startDate = formatter.format(c1.getTime());
	          c1.add(Calendar.DAY_OF_WEEK, +6);
	          String endDate = formatter.format(c1.getTime());
	          String finalRange = startDate + "-" + endDate;
	          weeks.add(finalRange);
	          c1.add(Calendar.DAY_OF_WEEK, -14);
	          c1.add(Calendar.DAY_OF_WEEK, 1);
	    }

	    return weeks;
	}

	public String EditSprintView() throws SQLException {
		return SUCCESS;
	}
	
	@SuppressWarnings("deprecation")
	public String getSprintNameByProjectId() throws JsonProcessingException {
		List<SprintReport> sprintReportList;

		sprintReportList = sprintReportManager.getSprintNameByProjectId(project_id);

		List<String> sr1 = new ArrayList<String>();

		if (sprintReportList != null) {

			for (SprintReport st : sprintReportList) {
				sr1.add(st.getSprintName());

			}
			ObjectMapper mapper = new ObjectMapper();
			String jsonInString = mapper.writeValueAsString(sr1);

			inputStream = new StringBufferInputStream(jsonInString);
			return SUCCESS;
		} else {
			inputStream = new StringBufferInputStream("");
			return SUCCESS;
		}
	}

	public String getSprintDetails() {

		try {

			SprintReport spReport = sprintReportManager.getSprintDetails(project_id, sprintName);
			SprintReport sr2 = new SprintReport();

			if (spReport != null) {

				sr2.setSpCommitted(spReport.getSpCommitted());
				sr2.setDevMembers(spReport.getDevMembers());
				sr2.setStartDate(spReport.getStartDate());
				sr2.setEndDate(spReport.getEndDate());
				sr2.setSpCommitted(spReport.getSpCommitted());
				sr2.setSpDelivered(spReport.getSpDelivered());
				sr2.setSpAddedInMid(spReport.getSpAddedInMid());
				sr2.setTeamCapacity(spReport.getTeamCapacity());
				sr2.setQaMembers(spReport.getQaMembers());
				sr2.setIsKanbanFollowed(spReport.getIsKanbanFollowed());
				ObjectMapper mapper = new ObjectMapper();
				String jsonInString = mapper.writeValueAsString(sr2);
				inputStream = new StringBufferInputStream(jsonInString);

			}

			else {

				return "data not found";
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return SUCCESS;

	}

	public String updateSprintReport() {

		SprintReport sReport = sprintReportManager.getSprintDetails(project_id, sprintName);

		sReport.setSprintName(sprintName);
		sReport.setStartDate(sprintStartdt);
		sReport.setEndDate(sprintEnddt);
		sReport.setDevMembers(devMembers);
		sReport.setQaMembers(qaMembers);
		sReport.setTeamCapacity(teamCapacity);
		sReport.setSpAddedInMid(spAddedInMid);
		sReport.setSpAddedInMid(spAddedInMid);
		sReport.setSpDelivered(spDelivered);
		sReport.setSpCommitted(spCommitted);
		sReport.setIsKanbanFollowed(isKanbanFollowed);

		sprintReportManager.saveUpdatedSprintReport(sReport);

		return SUCCESS;
	}
	public static Map getSession() {
		return session;
	}

	public void setSession(Map session) {
		SprintReportAction.session = session;
	}

	public SprintReportBean getSprintReportBean() {
		return sprintReport;
	}

	public void setSprintReportBean(SprintReportBean sprintReport) {
		this.sprintReport = sprintReport;
	}
	public SprintReportManager getSprintReportManager() {
		return sprintReportManager;
	}

	public void setSprintReportManager(SprintReportManager sprintReportManager) {
		this.sprintReportManager = sprintReportManager;
	}
	
	public int getProject_id() {
		return project_id;
	}

	public void setProject_id(int project_id) {
		this.project_id = project_id;
	}

	public ProjectDetailsManager getProjectDetailsManager() {
		return projectDetailsManager;
	}

	public void setProjectDetailsManager(ProjectDetailsManager projectDetailsManager) {
		this.projectDetailsManager = projectDetailsManager;
	}
	

	public boolean isIsdisabled() {
		return isdisabled;
	}

	public void setIsdisabled(boolean isdisabled) {
		this.isdisabled = isdisabled;
	}

	public String getWeekStr() {
		return weekStr;
	}

	public void setWeekStr(String weekStr) {
		this.weekStr = weekStr;
	}

	public Map getProjectNameWithId() {
		
		projectNameWithId=(Map) session.get("projectNameWithId");
		return projectNameWithId;
	}

	public void setProjectNameWithId(Map projectNameWithId) {
		this.projectNameWithId = projectNameWithId;
	}
	public InputStream getInputStream() {
	    return inputStream;
	}

	public String getSprintName() {
		return sprintName;
	}

	public void setSprintName(String sprintName) {
		this.sprintName = sprintName;
	}

	public int getTeamCapacity() {
		return teamCapacity;
	}

	public void setTeamCapacity(int teamCapacity) {
		this.teamCapacity = teamCapacity;
	}

	public int getDevMembers() {
		return devMembers;
	}

	public void setDevMembers(int devMembers) {
		this.devMembers = devMembers;
	}

	public int getQaMembers() {
		return qaMembers;
	}

	public void setQaMembers(int qaMembers) {
		this.qaMembers = qaMembers;
	}

	public Date getSprintStartdt() {
		return sprintStartdt;
	}

	public void setSprintStartdt(Date sprintStartdt) {
		this.sprintStartdt = sprintStartdt;
	}

	public Date getSprintEnddt() {
		return sprintEnddt;
	}

	public void setSprintEnddt(Date sprintEnddt) {
		this.sprintEnddt = sprintEnddt;
	}

	public Integer getSpCommitted() {
		return spCommitted;
	}

	public void setSpCommitted(Integer spCommitted) {
		this.spCommitted = spCommitted;
	}

	public Integer getSpDelivered() {
		return spDelivered;
	}

	public void setSpDelivered(Integer spDelivered) {
		this.spDelivered = spDelivered;
	}

	public Integer getSpAddedInMid() {
		return spAddedInMid;
	}

	public void setSpAddedInMid(Integer spAddedInMid) {
		this.spAddedInMid = spAddedInMid;
	}

	public String getIsKanbanFollowed() {
		return isKanbanFollowed;
	}

	public void setIsKanbanFollowed(String isKanbanFollowed) {
		this.isKanbanFollowed = isKanbanFollowed;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	} 
	
	

}
