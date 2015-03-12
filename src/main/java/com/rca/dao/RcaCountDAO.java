package com.rca.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.rca.entity.RcaCount;

public interface RcaCountDAO {

	public abstract void persist(RcaCount transientInstance);

	public abstract void attachDirty(RcaCount instance);

	public abstract void attachClean(RcaCount instance);

	public abstract void delete(RcaCount persistentInstance);

	public abstract RcaCount merge(RcaCount detachedInstance);

	public abstract RcaCount findById(java.lang.Integer id);

	public abstract List<RcaCount> findByExample(RcaCount instance);
	
	public abstract List<RcaCount> getRCACounts();
	
	public abstract List<RcaCount> findRCAfromWeekPeriod(String week);
	
	public abstract RcaCount findWeeklyRCAReportByProjectId(String week, int projectId);

}