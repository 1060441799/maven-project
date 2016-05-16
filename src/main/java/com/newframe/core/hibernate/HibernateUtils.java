package com.newframe.core.hibernate;

import org.hibernate.proxy.HibernateProxy;

/**
 * Created by xm on 2016/4/2.
 */
public class HibernateUtils {

    public static <T> T unproxy(T entity) {
        if (entity instanceof HibernateProxy) {
            entity = (T) ((HibernateProxy) entity).getHibernateLazyInitializer().getImplementation();
        }
        return entity;
    }
}
