package com.rca.dao;
// default package
// Generated Mar 10, 2015 6:01:20 AM by Hibernate Tools 3.4.0.CR1

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;

import com.rca.entity.ProjectDetails;

import static org.hibernate.criterion.Example.create;

/**
 * Home object for domain model class ProjectDetails.
 * @see .ProjectDetails
 * @author Hibernate Tools
 */
public class ProjectDetailsDAO {

	private static final Log log = LogFactory.getLog(ProjectDetailsDAO.class);

	//Session factory injected by spring context
    private SessionFactory sessionFactory;

	public void persist(ProjectDetails transientInstance) {
		log.debug("persisting ProjectDetails instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(ProjectDetails instance) {
		log.debug("attaching dirty ProjectDetails instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ProjectDetails instance) {
		log.debug("attaching clean ProjectDetails instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(ProjectDetails persistentInstance) {
		log.debug("deleting ProjectDetails instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ProjectDetails merge(ProjectDetails detachedInstance) {
		log.debug("merging ProjectDetails instance");
		try {
			ProjectDetails result = (ProjectDetails) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public ProjectDetails findById(int id) {
		log.debug("getting ProjectDetails instance with id: " + id);
		try {
			ProjectDetails instance = (ProjectDetails) sessionFactory
					.getCurrentSession().get("ProjectDetails", id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<ProjectDetails> findByExample(ProjectDetails instance) {
		log.debug("finding ProjectDetails instance by example");
		try {
			List<ProjectDetails> results = (List<ProjectDetails>) sessionFactory
					.getCurrentSession().createCriteria("ProjectDetails")
					.add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
	
	//This setter will be used by Spring context to inject the sessionFactory instance
		public void setSessionFactory(SessionFactory sessionFactory) {
			this.sessionFactory = sessionFactory;
		}
}
