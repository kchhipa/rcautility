package com.rca.dao;

import java.util.List;

import org.hibernate.SessionFactory;

import com.rca.entity.ProjectDetails;

public interface ProjectDetailsDAO {

	public abstract void persist(ProjectDetails transientInstance);

	public abstract void attachDirty(ProjectDetails instance);

	public abstract void attachClean(ProjectDetails instance);

	public abstract void delete(ProjectDetails persistentInstance);

	public abstract ProjectDetails merge(ProjectDetails detachedInstance);

	public abstract ProjectDetails findProjectDetailsByIdWithoutRcaCount(int id);
	
	public abstract ProjectDetails findProjectDetailsByIdWithRcaCount(int id);

	public abstract List<ProjectDetails> findByExample(ProjectDetails instance);

	//This setter will be used by Spring context to inject the sessionFactory instance
	public abstract void setSessionFactory(SessionFactory sessionFactory);
	
	public abstract List<ProjectDetails> getAllActiveProjects();

}