package com.rca.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.rca.dao.RcaDAO;

public class RcaManagerImpl implements RcaManager {
	//Employee dao injected by Spring context
    private RcaDAO rcaDAO;

	
	//This setter will be used by Spring context to inject the dao's instance
	public void setRcaDAO(RcaDAO rcaDAO) {
		this.rcaDAO = rcaDAO;
	}
}
