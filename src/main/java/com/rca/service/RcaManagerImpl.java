package com.rca.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.rca.dao.RcaCountDAO;
import com.rca.entity.RcaCount;

public class RcaManagerImpl implements RcaManager {
	//Employee dao injected by Spring context
    private RcaCountDAO rcaCountDAO;

	
	//This setter will be used by Spring context to inject the dao's instance
	
    public void setRcaCountDAO(RcaCountDAO rcaCountDAO) {
		this.rcaCountDAO = rcaCountDAO;
	}

	@Override
	@Transactional
	public void persist(RcaCount transientInstance) {
		rcaCountDAO.persist(transientInstance);		
	}


	@Override
	@Transactional
	public void attachDirty(RcaCount instance) {
		rcaCountDAO.attachDirty(instance);
		
	}


	@Override
	@Transactional
	public void attachClean(RcaCount instance) {
		rcaCountDAO.attachClean(instance);
		
	}


	@Override
	@Transactional
	public void delete(RcaCount persistentInstance) {
		rcaCountDAO.delete(persistentInstance);
		
	}


	@Override
	@Transactional
	public RcaCount merge(RcaCount detachedInstance) {
		return rcaCountDAO.merge(detachedInstance);
		
	}


	@Override
	@Transactional
	public RcaCount findById(Integer id) {
		return rcaCountDAO.findById(id);
		
	}


	@Override
	@Transactional
	public List<RcaCount> findByExample(RcaCount instance) {
		return rcaCountDAO.findByExample(instance);
		
	}

	@Override
	@Transactional
	public List<RcaCount> getRCACounts() {
		return rcaCountDAO.getRCACounts();
		
	}

	@Override
	@Transactional
	public List<RcaCount> findRCAfromWeekPeriod(String week) {
		
		return rcaCountDAO.findRCAfromWeekPeriod(week);
	}

	@Override
	@Transactional
	public RcaCount findWeeklyRCAReportByProjectId(String week, int projectId) {
		
		return rcaCountDAO.findWeeklyRCAReportByProjectId(week, projectId);
	}
}
