package com.rca.dao;

import java.util.List;

import org.hibernate.HibernateException;

import com.rca.entity.ProjectDetails;
import com.rca.entity.SprintReport;
import com.rca.entity.UserProjects;

public interface UserProjectsDAO {

	public UserProjects getProjectOfUserDaoById(String login_id) throws HibernateException;
	
	public void updateProjectToUserDao(UserProjects userProjects) throws HibernateException;
	
	public String deleteUserDao(String login_id) throws HibernateException;
	
	public List<ProjectDetails> getProjectWithTeamDao() throws HibernateException;
	
	public int updateTeamNameDao(int projectId, String actionTeam, String automation) throws HibernateException;
	
	public ProjectDetails getTeamNameByProjectIdDao(int projectId) throws HibernateException;
	public SprintReport getSprintNameByProjectIdDao(int projectId)throws HibernateException;
}
