package com.rca.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
	public RcaCount findWeeklyRCAReportByProjectId(String week, int projectId) {
		
		return rcaCountDAO.findWeeklyRCAReportByProjectId(week, projectId);
	}

	@Override
	public List<RcaCount> findRCAReportForMultipleWeek() {

	    List<String> weeks = new ArrayList<String>();
		SimpleDateFormat formatter = new SimpleDateFormat("M/d/yyyy");
	    
	    Calendar c1 = Calendar.getInstance();
	  
	    c1.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
	    c1.add(Calendar.WEEK_OF_MONTH, -1);
	    for(int i=0; i<13; i++)
	    {
	        String startDate = formatter.format(c1.getTime());
	        c1.add(Calendar.DAY_OF_WEEK, +6);
	        
	        String endDate = formatter.format(c1.getTime());
	        String finalRange = startDate +"-"+endDate;
	       
	        weeks.add(finalRange);
	        c1.add(Calendar.DAY_OF_WEEK, -14);
	        
	        c1.add(Calendar.DAY_OF_WEEK, 1);
	    }
	   
	
		return rcaCountDAO.findRCAReportForMultipleWeek(weeks);
	}

	@Override
	public List<RcaCount> findRCAByWeekPeriod(String week) {
		// TODO Auto-generated method stub
		return rcaCountDAO.findRCAByWeekPeriod(week);
	}
}