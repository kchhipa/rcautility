package com.rca.service;

import java.util.ArrayList;
import java.util.Date;

import com.rca.entity.SprintReport;

public interface SprintReportManager {
	
	public abstract String persistSprintReport(SprintReport transientInstance);

	public abstract void updateSprintReport(SprintReport instance);
		
	public abstract SprintReport findWeeklySprintReportByProjectId(String week, int projectId);
	
	public abstract ArrayList<SprintReport> findExistingSprintReportByProjectId(String date, int projectId);
	
	public abstract ArrayList<SprintReport> findLatestClosedSprintDataByProjectId(Date date, int projectId);
	
}
