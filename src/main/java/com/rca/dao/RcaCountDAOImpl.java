package com.rca.dao;

// default package
// Generated Mar 10, 2015 6:01:20 AM by Hibernate Tools 3.4.0.CR1

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rca.entity.RcaCount;

/**
 * Home object for domain model class RcaCount.
 * 
 * @see .RcaCount
 * @author Hibernate Tools
 */
@Repository
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class RcaCountDAOImpl implements RcaCountDAO {

	private static final Log log = LogFactory.getLog(RcaCountDAOImpl.class);

	// Session factory injected by spring context
	private SessionFactory sessionFactory;
	protected HibernateTemplate template = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.rca.dao.RcaCountDAO#persist(com.rca.entity.RcaCount)
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void persist(RcaCount transientInstance) {
		log.debug("persisting RcaCount instance");
		try {
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
	 * @see com.rca.dao.RcaCountDAO#attachDirty(com.rca.entity.RcaCount)
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void attachDirty(RcaCount instance) {
		log.debug("attaching dirty RcaCount instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.rca.dao.RcaCountDAO#attachClean(com.rca.entity.RcaCount)
	 */
	@Override
	public void attachClean(RcaCount instance) {
		log.debug("attaching clean RcaCount instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.rca.dao.RcaCountDAO#delete(com.rca.entity.RcaCount)
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void delete(RcaCount persistentInstance) {
		log.debug("deleting RcaCount instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.rca.dao.RcaCountDAO#merge(com.rca.entity.RcaCount)
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public RcaCount merge(RcaCount detachedInstance) {
		log.debug("merging RcaCount instance");
		try {
			RcaCount result = (RcaCount) sessionFactory.getCurrentSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.rca.dao.RcaCountDAO#findById(java.lang.Integer)
	 */
	@Override
	public RcaCount findById(java.lang.Integer id) {
		log.debug("getting RcaCount instance with id: " + id);
		RcaCount instance = template.get(RcaCount.class, id);

		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.rca.dao.RcaCountDAO#findByExample(com.rca.entity.RcaCount)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<RcaCount> findByExample(RcaCount instance) {
		log.debug("finding RcaCount instance by example");
		try {
			List<RcaCount> results = (List<RcaCount>) sessionFactory
					.getCurrentSession().createCriteria("RcaCount")
					.add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	// This method return al RCA records from DB
	@SuppressWarnings("unchecked")
	@Override
	public List<RcaCount> getRCACounts() throws DataAccessException {

		return (List<RcaCount>) template.find("from RcaCount");
		// return
		// this.sessionFactory.getCurrentSession().createQuery("from RcaCount").list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RcaCount> findRCAByWeekPeriod(String week)
			throws DataAccessException {

		List<RcaCount> results = (List<RcaCount>) template
				.findByCriteria(DetachedCriteria.forClass(RcaCount.class).add(
						Restrictions.eq("week", week)));

		return results;
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public RcaCount findWeeklyRCAReportByProjectId(String week, int projectId) {

		/*DetachedCriteria criteria = DetachedCriteria.forClass(RcaCount.class);
		criteria.add(Restrictions.eq("week", week));
		criteria.add(Restrictions.eq("projectId", new Integer(projectId)));

		List<RcaCount> results = (List<RcaCount>) template
				.findByCriteria(criteria);
		return results.get(0);*/
		String query = "from RcaCount where week=? and project_id =?";
		Object[] queryParam = {week, projectId};
		List<RcaCount> results = (List<RcaCount>) template.find(query, queryParam);
		if(results.size()>0){
			return results.get(0);
		}
		return null;
	}

	

	@SuppressWarnings("unchecked")
	@Override
	public List<RcaCount> findRCAReportForMultipleWeek(List<String> weeks) {

				List<RcaCount> results = (List<RcaCount>) template
				.findByCriteria(DetachedCriteria.forClass(RcaCount.class).add(
						Restrictions.in("week", weeks)));
		return results;
	}
	/**
	 * This method return all week rca count for single project
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<RcaCount> findRCAReportForMultipleWeekForProject(int projectId) {

		String query = "from RcaCount where project_id =?";
		Object[] queryParam = {projectId};
		List<RcaCount> results = (List<RcaCount>) template.find(query, queryParam);
		if(results.size()>0){
			return (List<RcaCount>) results;
		}
		return null;
	}
	// This setter will be used by Spring context to inject the sessionFactory
		// instance
		public void setSessionFactory(SessionFactory sessionFactory) {
			template = new HibernateTemplate(sessionFactory);
		}


}
