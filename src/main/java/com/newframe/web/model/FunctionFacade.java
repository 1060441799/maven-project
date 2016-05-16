package com.newframe.web.model;

import com.newframe.core.vo.Detachable;
import com.newframe.core.pojo.basepojo.IdEntityIfc;
import com.newframe.core.pojo.basepojo.ManageableEntityIfc;
import com.newframe.core.pojo.pojoimpl.impl.Function;
import com.newframe.web.model.base.AbstractModelFacade;

import org.hibernate.Hibernate;

import java.util.Date;

/**
 * Created by xm on 2016/4/2.
 */
public class FunctionFacade extends AbstractModelFacade implements IdEntityIfc, Detachable, ManageableEntityIfc {
    private Function function;
    private String functionName;// 菜单名称
	private Short functionLevel;// 菜单等级
	private String functionUrl;// 菜单地址
	private Short functionIframe;// 菜单地址打开方式
	private String functionOrder;// 菜单排序
	private String iconClas;// 菜单图标
	private String iconClasDesk;// 云桌面菜单图标
    private boolean detached;

    public FunctionFacade() {
        this.function = new Function();
    }

    public FunctionFacade(Function function) {
        this.function = function;
    }

    private <T> T isCollInitialized(T coll) {
        if (Hibernate.isInitialized(coll) || !detached) {
            return coll;
        }
        return null;
    }

    public void detach() {
        this.detached = true;
    }

    public void setId(String id) {
        function.setId(id);
    }

    public String getId() {
        return function.getId();
    }

    public String getCreatedBy() {
        return function.getCreatedBy();
    }

    public Date getCreatedOn() {
        return function.getCreatedOn();
    }

    public String getModifiedBy() {
        return function.getModifiedBy();
    }

    public Date getModifiedOn() {
        return function.getModifiedOn();
    }

    public void setCreatedBy(String createdBy) {
        function.setCreatedBy(createdBy);
    }

    public void setCreatedOn(Date createdOn) {
        function.setCreatedOn(createdOn);
    }

    public void setModifiedBy(String modifiedBy) {
        function.setModifiedBy(modifiedBy);
    }

    public void setModifiedOn(Date modifiedOn) {
        function.setModifiedOn(modifiedOn);
    }
}
