package com.rca.dao;

//default package
//Generated Apr 02, 2015 4:28:21 PM by Hibernate Tools 3.4.0.CR1

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rca.entity.SprintReport;

/**
 * Home object for domain model class SprintReport.
 * 
 * @see .SprintReport
 * @author Hibernate Tools
 */
@Repository
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class SprintReportDAOImpl implements SprintReportDAO {

	private static final Log log = LogFactory.getLog(SprintReportDAOImpl.class);

	// Session factory injected by spring context
	private SessionFactory sessionFactory;
	protected HibernateTemplate template = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.rca.dao.SprintReportDAO#persist(com.rca.entity.SprintReport)
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void persistSprintReport(SprintReport transientInstance) {
		log.debug("persisting SprintReport instance");
		try {
			System.out.println(sessionFactory.getCurrentSession());
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.rca.dao.SprintReportDAO#attachDirty(com.rca.entity.SprintReport)
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void updateSprintReport(SprintReport instance) {
		log.debug("attaching dirty SprintReport instance");
		try {
			sessionFactory.getCurrentSession().update(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public SprintReport findWeeklySprintReportByProjectId(String week, int projectId) {

		String query = "from SprintReport where week=? and project_id =?";
		Object[] queryParam = {week, projectId};
		List<SprintReport> results = (List<SprintReport>) template.find(query, queryParam);
		if(results.size()>0){
			return results.get(0);
		}
		return null;
	}

	// This setter will be used by Spring context to inject the sessionFactory
	// instance
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
		template = new HibernateTemplate(sessionFactory);
	}

}
