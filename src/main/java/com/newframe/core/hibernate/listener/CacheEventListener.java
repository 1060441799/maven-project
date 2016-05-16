//package com.newframe.core.hibernate.listener;
//
//import org.hibernate.event.spi.*;
//import org.hibernate.persister.entity.EntityPersister;
//
///**
// * Created by xm on 2016/5/8.
// */
//public class CacheEventListener implements PostUpdateEventListener,
//        PostInsertEventListener, PostDeleteEventListener {
//
//    private static final long serialVersionUID = 1L;
//
//    @Override
//    public void onPostDelete(PostDeleteEvent arg0) {
//        System.out.println("delete...................");
//    }
//
//    @Override
//    public void onPostInsert(PostInsertEvent arg0) {
//        System.out.println("insert...................");
//    }
//
//    @Override
//    public void onPostUpdate(PostUpdateEvent arg0) {
//        System.out.println("update...................");
//    }
//
//    @Override
//    public boolean requiresPostCommitHanding(EntityPersister arg0) {
//        System.out.println("here...................");
//        return true;
//    }
//}
