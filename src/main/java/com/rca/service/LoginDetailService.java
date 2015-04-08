package com.rca.service;

import java.util.List;

import com.rca.entity.LoginDetails;

public interface LoginDetailService {

	public List<LoginDetails> getLoginDetail() throws Exception;
	
	public void saveOrUpdateLoginDetails(LoginDetails loginDetails)  throws Exception;
}
