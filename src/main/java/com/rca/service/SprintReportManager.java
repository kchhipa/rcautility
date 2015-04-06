package com.rca.service;

import java.util.List;

import com.rca.entity.SprintReport;

public interface SprintReportManager {
	
	public abstract void persistSprintReport(SprintReport transientInstance);

	public abstract void updateSprintReport(SprintReport instance);
		
	public abstract SprintReport findWeeklySprintReportByProjectId(String week, int projectId); 
	
}
