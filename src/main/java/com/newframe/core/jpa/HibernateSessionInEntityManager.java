package com.newframe.core.jpa;

import java.util.Map;

import javax.persistence.EntityManager;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.context.spi.CurrentSessionContext;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.jpa.internal.EntityManagerImpl;
import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class HibernateSessionInEntityManager implements CurrentSessionContext {

	public HibernateSessionInEntityManager() {
	}

	public HibernateSessionInEntityManager(SessionFactory sessionFactory) {
	}

	public HibernateSessionInEntityManager(SessionFactoryImplementor sessionFactory) {
	}

	public Session currentSession() throws HibernateException {
		Map<Object, Object> resourceMap = TransactionSynchronizationManager.getResourceMap();
		for (Object v : resourceMap.values()) {
			if (v instanceof EntityManagerHolder) {
				return getSessionFromEM(((EntityManagerHolder) v).getEntityManager());
			}
		}
		return null;
	}

	private static Session getSessionFromEM(final EntityManager entityManager) {
		final Object emDelegate = entityManager.getDelegate();
		if (emDelegate instanceof EntityManagerImpl) {
			return ((EntityManagerImpl) emDelegate).getSession();
		} else if (emDelegate instanceof Session) {
			return (Session) emDelegate;
		}
		throw new HibernateException("No Session found");
	}
}
