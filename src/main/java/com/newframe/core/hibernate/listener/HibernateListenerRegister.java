//package com.newframe.core.hibernate.listener;
//
//import org.hibernate.SessionFactory;
//import org.hibernate.event.service.spi.EventListenerRegistry;
//import org.hibernate.event.spi.EventType;
//import org.hibernate.internal.SessionFactoryImpl;
//
//import javax.annotation.PostConstruct;
//
///**
// * Created by xm on 2016/5/8.
// */
//public class HibernateListenerRegister {
//    private CacheEventListener cacheEventListener;
//
//    private SessionFactory sessionFactory;
//
//    public void setCacheEventListener(CacheEventListener cacheEventListener) {
//        this.cacheEventListener = cacheEventListener;
//    }
//
//    public void setSessionFactory(SessionFactory sessionFactory) {
//        this.sessionFactory = sessionFactory;
//    }
//
//    @PostConstruct
//    public void registerListeners() {
//        EventListenerRegistry registry = ((SessionFactoryImpl) sessionFactory).getServiceRegistry().getService(
//                EventListenerRegistry.class);
//        registry.getEventListenerGroup(EventType.POST_INSERT).appendListener(cacheEventListener);
//        registry.getEventListenerGroup(EventType.POST_UPDATE).appendListener(cacheEventListener);
//        registry.getEventListenerGroup(EventType.POST_DELETE).appendListener(cacheEventListener);
//    }
//}
