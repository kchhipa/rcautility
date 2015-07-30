package com.rca.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rca.common.RCAConstants;
import com.rca.entity.LoginDetails;
import com.rca.entity.ProjectDetails;
import com.rca.entity.UserProjects;

@Repository
public class UserProjectsDAOImpl implements UserProjectsDAO{


	private static Log log = LogFactory.getLog(UserProjectsDAOImpl.class);
	private SessionFactory sessionFactory;
	
	@Override
	@Transactional
	public UserProjects getProjectOfUserDaoById(String login_id) {
		
		UserProjects userProjects = null;
		try
		{
			Session session = sessionFactory.getCurrentSession();	
			userProjects = (UserProjects) session.get(UserProjects.class, login_id);	
			log.debug("UserProjects fetched successfully");
		}
		catch(HibernateException he)
		{
			log.error("Exception in fetching UserProjects");
			throw he;
		}
		return userProjects;
	}

	
	@Override
	@Transactional
	public void updateProjectToUserDao(UserProjects userProjects) {
		try
		{
			Session session = sessionFactory.getCurrentSession();	
			session.update(userProjects);	
			log.debug("User's project updated successfully");
		}
		catch(HibernateException he)
		{
			log.error("Exception in updating UserProjects");
			throw he;
		}
		
	}

	@Override
	@Transactional
	public String deleteUserDao(String login_id)
	{
		try
		{
			Session session = sessionFactory.getCurrentSession();
			LoginDetails loginDetails = (LoginDetails)session.get(LoginDetails.class, login_id);
			if(loginDetails != null)
			{
				session.delete(loginDetails);
				return RCAConstants.SUCCESS;
			}
			else
				return RCAConstants.UserProjectsMessage.NO_USER;
		}
		catch(HibernateException he)
		{
			log.error("Exception in deleting User");
			throw he;
		}
	}
	
	@Transactional
	public List<ProjectDetails> getProjectWithTeamDao()
	{
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from ProjectDetails");
		List<ProjectDetails> projectDetailList = query.list();
		return projectDetailList;
	}
	
	@Transactional
	public int updateTeamNameDao(int projectId, String actionTeam)
	{
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("update ProjectDetails set actionTeam=? where projectId=?");
		query.setString(0, actionTeam);
		query.setInteger(1, projectId);
		return query.executeUpdate();		 
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}


}
