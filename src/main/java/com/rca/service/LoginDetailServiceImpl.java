package com.rca.service;

import java.util.List;

import com.rca.dao.LoginDetailsDAO;
import com.rca.entity.LoginDetails;

public class LoginDetailServiceImpl implements LoginDetailService{

	private LoginDetailsDAO loginDetailsDao;

	
	@Override
	public List<LoginDetails> getLoginDetail() throws Exception {
		// TODO Auto-generated method stub
		return loginDetailsDao.getLoginDetailsDao();
		
	}
	@Override
	public void saveOrUpdateLoginDetails(LoginDetails loginDetails) throws Exception
	{
		loginDetailsDao.attachDirty(loginDetails);
	}
	
	
	public LoginDetailsDAO getLoginDetailsDao() {
		return loginDetailsDao;
	}

	public void setLoginDetailsDao(LoginDetailsDAO loginDetailsDao) {
		this.loginDetailsDao = loginDetailsDao;
	}


	
}
