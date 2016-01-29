package com.rca.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.rca.entity.SprintReport;

public interface SprintReportDAO {
	
	public abstract void persistSprintReport(SprintReport transientInstance);
	
	public abstract SprintReport findSprintReportByName(SprintReport transientInstance);
	
	public abstract void updateSprintReport(SprintReport instance);
	
	public abstract SprintReport findWeeklySprintReportByProjectId(String week, int projectId);
	
	public abstract ArrayList<SprintReport> findExistingSprintReportByProjectId(String date, int projectId);
	
	public abstract ArrayList<SprintReport> findLatestClosedSprintDataByProjectId(Date date, int projectId);
	
	public abstract SprintReport getSprintdetails(int projectId, String sprintName);

	public abstract void saveUpdatedSprintReport(SprintReport sReport);

	public abstract List<SprintReport> getSprintNameByProjectId(int projectID);
	
	public abstract List<SprintReport> getSpDeliveredByProjectID(int projectID);

}
