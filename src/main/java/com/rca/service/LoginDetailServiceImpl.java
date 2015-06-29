package com.rca.service;

import java.util.List;

import org.hibernate.HibernateException;

import com.rca.common.RCAConstants;
import com.rca.dao.LoginDetailsDAO;
import com.rca.dao.UserProjectsDAO;
import com.rca.entity.LoginDetails;
import com.rca.entity.UserProjects;

public class LoginDetailServiceImpl implements LoginDetailService{

	private LoginDetailsDAO loginDetailsDao;
	private UserProjectsDAO userProjectsDao;
	
	@Override
	public List<LoginDetails> getLoginDetail() throws Exception {
		// TODO Auto-generated method stub
		return loginDetailsDao.getLoginDetailsDao();
		
	}
	@Override
	public String saveOrUpdateLoginDetails(LoginDetails loginDetails) throws Exception
	{
		LoginDetails loginDetailObj = loginDetailsDao.findById(loginDetails.getLoginId());
		if(loginDetailObj!=null)
			return RCAConstants.ALLREADY_EXIST;
		else
		{
			loginDetailsDao.persist(loginDetails);
			return RCAConstants.SUCCESS;
		}
	}
	
	@Override
	public String getProjectsOfUserById(String login_id)  throws HibernateException
	{
		UserProjects userProjects = userProjectsDao.getProjectOfUserDaoById(login_id);
		if(userProjects!= null)
			return userProjects.getProjects();
		
		return null;
	}
	
	public LoginDetailsDAO getLoginDetailsDao() {
		return loginDetailsDao;
	}

	public void setLoginDetailsDao(LoginDetailsDAO loginDetailsDao) {
		this.loginDetailsDao = loginDetailsDao;
	}
	
	public void setUserProjectsDao(UserProjectsDAO userProjectsDao) {
		this.userProjectsDao = userProjectsDao;
	}

	
	
}
