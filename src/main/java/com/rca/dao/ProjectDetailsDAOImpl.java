package com.rca.dao;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rca.entity.ProjectDetails;

@Repository
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class ProjectDetailsDAOImpl  implements ProjectDetailsDAO{



	private static final Log log = LogFactory.getLog(ProjectDetailsDAO.class);

	//Session factory injected by spring context
    private SessionFactory sessionFactory;
    
    protected HibernateTemplate template = null;
    
 // This setter will be used by Spring context to inject the sessionFactory
 		// instance
 		public void setSessionFactory(SessionFactory sessionFactory) {
 			template = new HibernateTemplate(sessionFactory);
 		}

	/* (non-Javadoc)
	 * @see com.rca.dao.ProjectDetailsDAO#persist(com.rca.entity.ProjectDetails)
	 */
	@Override
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

	/* (non-Javadoc)
	 * @see com.rca.dao.ProjectDetailsDAO#attachDirty(com.rca.entity.ProjectDetails)
	 */
	@Override
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

	/* (non-Javadoc)
	 * @see com.rca.dao.ProjectDetailsDAO#attachClean(com.rca.entity.ProjectDetails)
	 */
	@Override
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

	/* (non-Javadoc)
	 * @see com.rca.dao.ProjectDetailsDAO#delete(com.rca.entity.ProjectDetails)
	 */
	@Override
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

	/* (non-Javadoc)
	 * @see com.rca.dao.ProjectDetailsDAO#merge(com.rca.entity.ProjectDetails)
	 */
	@Override
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

	/* (non-Javadoc)
	 * @see com.rca.dao.ProjectDetailsDAO#findById(int)
	 */
	@Override
	public ProjectDetails findProjectDetailsByIdWithoutRcaCount(int id) {
		log.debug("getting ProjectDetails instance with id: " + id);
		
		ProjectDetails projDetails = template.get(ProjectDetails.class, id);
		
		return projDetails;
		
	}

	/* (non-Javadoc)
	 * @see com.rca.dao.ProjectDetailsDAO#findByExample(com.rca.entity.ProjectDetails)
	 */
	@Override
	@SuppressWarnings("unchecked")
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

	
	@Override
	public ProjectDetails findProjectDetailsByIdWithRcaCount(int id) {
		ProjectDetails pd = findProjectDetailsByIdWithoutRcaCount(id);
		template.initialize(pd.getRcaCounts());
		return pd;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectDetails> getAllActiveProjects() {

		String query = "from ProjectDetails where status=?";
		Object[] queryParam = {"active"};
		List<ProjectDetails> results = (List<ProjectDetails>) template.find(query, queryParam);
		log.debug("getAllActiveProjects, result size: "
				+ results.size());
		if(results.size()>0){
			return results;
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectDetails> getAllProjects() {

		String query = "from ProjectDetails";
		List<ProjectDetails> results = (List<ProjectDetails>) template.find(query);
		log.debug("getAllActiveProjects, result size: "
				+ results.size());
		if(results.size()>0){
			return results;
		}
		return null;
	}
	/*RND WORK-introducing voilation*/
	public void test_voilation(ProjectDetails instance) {
	  int test=0;
	  List<ProjectDetails> results = (List<ProjectDetails>) sessionFactory
          .getCurrentSession().createCriteria("ProjectDetails")
          .add(create(instance)).list();
      
  }
}
