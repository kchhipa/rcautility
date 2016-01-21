/**
 * 
 */
package com.rca.entity;

/**
 * @author Priyanka.kala
 * 
 */

public class SprintReportRanking {
	/**
	 * A
	 */
	private String teamName;

	/**
	 * B = project name
	 */
	private String client;

	
	private String  sprintName;
	private int sNo;
	private String  sprintStartDate;
	private String  sprintEndDate;
	private int  committedSP;
	private int  completedSP;
	private int clientCodeDefect;
	private int reopenCount;
	private int spAddedMidSprint;
	private String ranking;
	private String realizedPercentage;
	private String teamSize;
	private int teamCapacity;
	private String isKanbanFollowed;
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
	public String getSprintName() {
		return sprintName;
	}
	public void setSprintName(String sprintName) {
		this.sprintName = sprintName;
	}
	
	public int getCommittedSP() {
		return committedSP;
	}
	public void setCommittedSP(int committedSP) {
		this.committedSP = committedSP;
	}
	public int getCompletedSP() {
		return completedSP;
	}
	public void setCompletedSP(int completedSP) {
		this.completedSP = completedSP;
	}
	public int getClientCodeDefect() {
		return clientCodeDefect;
	}
	public void setClientCodeDefect(int clientCodeDefect) {
		this.clientCodeDefect = clientCodeDefect;
	}
	public int getReopenCount() {
		return reopenCount;
	}
	public void setReopenCount(int reopenCount) {
		this.reopenCount = reopenCount;
	}
	public int getSpAddedMidSprint() {
		return spAddedMidSprint;
	}
	public void setSpAddedMidSprint(int spAddedMidSprint) {
		this.spAddedMidSprint = spAddedMidSprint;
	}
	public String getRanking() {
		return ranking;
	}
	public void setRanking(String ranking) {
		this.ranking = ranking;
	}
	public String getRealizedPercentage() {
		return realizedPercentage;
	}
	public void setRealizedPercentage(String realizedPercentage) {
		this.realizedPercentage = realizedPercentage;
	}
	
	public String getTeamSize() {
		return teamSize;
	}
	public void setTeamSize(String teamSize) {
		this.teamSize = teamSize;
	}
	public int getsNo() {
		return sNo;
	}
	public void setsNo(int sNo) {
		this.sNo = sNo;
	}
	public String getSprintStartDate() {
		return sprintStartDate;
	}
	public void setSprintStartDate(String sprintStartDate) {
		this.sprintStartDate = sprintStartDate;
	}
	public String getSprintEndDate() {
		return sprintEndDate;
	}
	public void setSprintEndDate(String sprintEndDate) {
		this.sprintEndDate = sprintEndDate;
	}
	public int getTeamCapacity() {
		return teamCapacity;
	}
	public void setTeamCapacity(int teamCapacity) {
		this.teamCapacity = teamCapacity;
	}
	public String getIsKanbanFollowed() {
		return isKanbanFollowed;
	}
	public void setIsKanbanFollowed(String isKanbanFollowed) {
		this.isKanbanFollowed = isKanbanFollowed;
	}
		
}

