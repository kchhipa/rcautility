package com.rca.dao;

import java.util.ArrayList;
import java.util.List;

import com.rca.entity.SprintReport;

public interface SprintReportDAO {
	
	public abstract void persistSprintReport(SprintReport transientInstance);

	public abstract void updateSprintReport(SprintReport instance);
	
	public abstract SprintReport findWeeklySprintReportByProjectId(String week, int projectId);
	
	public abstract ArrayList<SprintReport> findExistingSprintReportByProjectId(String date, int projectId);

}
