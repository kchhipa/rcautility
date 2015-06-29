package com.rca.dao;
// default package
// Generated Mar 10, 2015 6:01:20 AM by Hibernate Tools 3.4.0.CR1

import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.rca.entity.LoginDetails;

import static org.hibernate.criterion.Example.create;

/**
 * Home object for domain model class LoginDetails.
 * @see .LoginDetails
 * @author Hibernate Tools
 */
public class LoginDetailsDAO {

	private static final Log log = LogFactory.getLog(LoginDetailsDAO.class);

	//Session factory injected by spring context
    private SessionFactory sessionFactory;

    @Transactional
	public void persist(LoginDetails transientInstance) {
		log.debug("persisting LoginDetails instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}
    @Transactional
	public void attachDirty(LoginDetails instance) throws HibernateException {
		log.debug("attaching dirty LoginDetails instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (HibernateException he) {
			log.error("attach failed", he);
			throw he;
		}
	}

	public void attachClean(LoginDetails instance) {
		log.debug("attaching clean LoginDetails instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(LoginDetails persistentInstance) {
		log.debug("deleting LoginDetails instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public LoginDetails merge(LoginDetails detachedInstance) {
		log.debug("merging LoginDetails instance");
		try {
			LoginDetails result = (LoginDetails) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	@Transactional
	public LoginDetails findById(java.lang.String id) {
		log.debug("getting LoginDetails instance with id: " + id);
		try {
			LoginDetails instance = (LoginDetails) sessionFactory
					.getCurrentSession().get(LoginDetails.class, id);
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

	public List<LoginDetails> findByExample(LoginDetails instance) {
		log.debug("finding LoginDetails instance by example");
		try {
			List<LoginDetails> results = (List<LoginDetails>) sessionFactory
					.getCurrentSession().createCriteria("LoginDetails")
					.add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<LoginDetails> getLoginDetailsDao() throws HibernateException {
		log.debug("finding LoginDetails instances");
		try {
				Session session = sessionFactory.openSession();			
				List<LoginDetails> loginDetails= session.createCriteria(LoginDetails.class).list();				
				session.close();
				return loginDetails;
				
		} catch (HibernateException he) {
			log.error("finding instances failed", he);
			throw he;
		}
	}
	
	//This setter will be used by Spring context to inject the sessionFactory instance
		public void setSessionFactory(SessionFactory sessionFactory) {
			this.sessionFactory = sessionFactory;
		}
}
