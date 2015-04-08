package com.rca.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rca.dao.SprintReportDAO;
import com.rca.entity.SprintReport;

@Service
public class SprintReportManagerImpl implements SprintReportManager {
	
	//Employee dao injected by Spring context
    private SprintReportDAO sprintReportDAO;

	
	//This setter will be used by Spring context to inject the dao's instance
	
    public void setSprintReportDAO(SprintReportDAO sprintReportDAO) {
		this.sprintReportDAO = sprintReportDAO;
	}


    @Override
	@Transactional
	public void persistSprintReport(SprintReport transientInstance) {
		sprintReportDAO.persistSprintReport(transientInstance);
		
	}


    @Override
	@Transactional
	public void updateSprintReport(SprintReport instance) {
    	sprintReportDAO.updateSprintReport(instance);
		
	}


    @Override
	public SprintReport findWeeklySprintReportByProjectId(String week,
			int projectId) {
		
		return sprintReportDAO.findWeeklySprintReportByProjectId(week, projectId);
	}


}
