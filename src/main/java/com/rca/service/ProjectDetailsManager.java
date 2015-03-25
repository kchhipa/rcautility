package com.rca.service;

import java.util.List;

import com.rca.entity.ProjectDetails;

public interface ProjectDetailsManager {
	
	public abstract ProjectDetails findProjectDetailsByIdWithoutRcaCount(int id);
	
	public abstract ProjectDetails findProjectDetailsByIdWithRcaCount(int id);
	
	public abstract List<ProjectDetails> getAllActiveProjects();

}
