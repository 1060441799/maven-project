package com.newframe.web.model;

import com.newframe.core.pojo.pojoimpl.impl.Role;
import java.util.Date;

import com.newframe.core.pojo.basepojo.IdEntityIfc;
import com.newframe.core.pojo.basepojo.ManageableEntityIfc;
import com.newframe.core.pojo.pojoimpl.impl.Function;
import org.hibernate.Hibernate;

import com.newframe.core.pojo.pojoimpl.impl.RelRoleFunction;
import com.newframe.core.vo.Detachable;
import com.newframe.web.model.base.AbstractModelFacade;

/**
 * Created by xm on 2016/4/2.
 */
public class RelRoleFunctionFacade extends AbstractModelFacade implements IdEntityIfc, Detachable {
    private RelRoleFunction relRoleFunction;
    private boolean detached;
    
    public Role getRole(){
    	return isCollInitialized(relRoleFunction.getRole());
    }
    
    public Function getFunction() {
    	return isCollInitialized(relRoleFunction.getFunction());
	}

    public RelRoleFunctionFacade() {
        this.relRoleFunction = new RelRoleFunction();
    }

    public RelRoleFunctionFacade(RelRoleFunction relRoleFunction) {
        this.relRoleFunction = relRoleFunction;
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
    	relRoleFunction.setId(id);
    }

    public String getId() {
        return relRoleFunction.getId();
    }
}
