package com.rca.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class RcaDaoImpl implements RcaDAO  
{
	//Session factory injected by spring context
    private SessionFactory sessionFactory;
	
   

	//This setter will be used by Spring context to inject the sessionFactory instance
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
