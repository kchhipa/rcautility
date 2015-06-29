package com.rca.service;

import java.util.List;

import org.hibernate.HibernateException;

import com.rca.entity.LoginDetails;

public interface LoginDetailService {

	public List<LoginDetails> getLoginDetail() throws Exception;
	
	public String saveOrUpdateLoginDetails(LoginDetails loginDetails)  throws Exception;
	
	public String getProjectsOfUserById(String login_id)  throws HibernateException;
}
