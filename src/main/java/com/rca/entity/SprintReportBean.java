package com.rca.entity;

import java.util.Date;

public class SprintReportBean {

	private Integer sprintReportId;
	private ProjectDetails projectDetails;
	private String sprintName;
	private Integer spCommitted;
	private Integer spDelivered;
	private Integer spAddedInMid;
	private Integer teamCapacity;
	private Date startDate;
	private Date endDate;
	private Integer devMembers;
	private Integer qaMembers;
	//adding new field
	private String isKanbanFollowed;
	
  public SprintReportBean(){
		
	}
	
	public SprintReportBean(Integer sprintReportId, ProjectDetails projectDetails,
			String sprintName, Integer spCommitted,
			Integer spDelivered, Integer spAddedInMid,
			Integer teamCapacity,Integer devMembers, Integer qaMembers,Date startDate,Date endDate,String isKanbanFollowed ) {
		this.sprintReportId = sprintReportId;
		this.projectDetails = projectDetails;
		this.sprintName = sprintName;
		this.spCommitted = spCommitted;
		this.spDelivered = spDelivered;
		this.spAddedInMid = spAddedInMid;
		this.teamCapacity = teamCapacity;
		this.startDate = startDate;
		this.endDate = endDate;
		this.devMembers = qaMembers;
		this.qaMembers = qaMembers;
		this.isKanbanFollowed=isKanbanFollowed;
	}
	public Integer getSprintReportId() {
		return sprintReportId;
	}
	public void setSprintReportId(Integer sprintReportId) {
		this.sprintReportId = sprintReportId;
	}
	public ProjectDetails getProjectDetails() {
		return projectDetails;
	}
	public void setProjectDetails(ProjectDetails projectDetails) {
		this.projectDetails = projectDetails;
	}
	public String getSprintName() {
		return sprintName.trim();
	}
	public void setSprintName(String sprintName) {
		this.sprintName = sprintName;
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
	public Integer getTeamCapacity() {
		return teamCapacity;
	}
	public void setTeamCapacity(Integer teamCapacity) {
		this.teamCapacity = teamCapacity;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Integer getDevMembers() {
		return devMembers;
	}
	public void setDevMembers(Integer devMembers) {
		this.devMembers = devMembers;
	}
	public Integer getQaMembers() {
		return qaMembers;
	}
	public void setQaMembers(Integer qaMembers) {
		this.qaMembers = qaMembers;
	}

    public String getIsKanbanFollowed() {
    return isKanbanFollowed;
    }

    public void setIsKanbanFollowed(String isKanbanFollowed) {
    this.isKanbanFollowed = isKanbanFollowed;
    }
	
	
	
	
	
}
