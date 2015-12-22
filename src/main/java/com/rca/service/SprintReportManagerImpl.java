package com.rca.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rca.common.RCAConstants;
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
	public String persistSprintReport(SprintReport transientInstance) {
    	SprintReport sprintReport=sprintReportDAO.findSprintReportByName(transientInstance);
    	if(sprintReport!=null){
			return RCAConstants.ALLREADY_EXIST;
    	}else{
			sprintReportDAO.persistSprintReport(transientInstance);
			return RCAConstants.SUCCESS;
		}
		
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
    
    public ArrayList<SprintReport> findExistingSprintReportByProjectId(String date, int projectId) {
		return sprintReportDAO.findExistingSprintReportByProjectId(date, projectId);
	}


}
