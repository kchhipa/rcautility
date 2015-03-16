package com.rca.service;

import org.springframework.transaction.annotation.Transactional;

import com.rca.dao.ProjectDetailsDAO;
import com.rca.entity.ProjectDetails;

public class ProjectDetailsManagerImpl implements ProjectDetailsManager {

	private ProjectDetailsDAO projectDetailsDAO;
	
	
	public void setProjectDetailsDAO(ProjectDetailsDAO projectDetailsDAO) {
		this.projectDetailsDAO = projectDetailsDAO;
	}


	@Override
	@Transactional
	public ProjectDetails findProjectDetailsByIdWithoutRcaCount(int id) {
		
		return projectDetailsDAO.findProjectDetailsByIdWithoutRcaCount(id);
	}


	@Override
	@Transactional
	public ProjectDetails findProjectDetailsByIdWithRcaCount(int id) {
		// TODO Auto-generated method stub
		return projectDetailsDAO.findProjectDetailsByIdWithRcaCount(id);
	}


}
