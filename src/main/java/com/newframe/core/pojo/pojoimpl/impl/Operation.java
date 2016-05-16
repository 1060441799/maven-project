package com.newframe.core.pojo.pojoimpl.impl;

import com.newframe.core.pojo.basepojo.SortableAndManageableEntity;
import com.newframe.core.pojo.basepojo.SortableEntityIfc;
import com.newframe.core.pojo.pojoimpl.OperationIfc;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "core_operation")
public class Operation extends SortableAndManageableEntity implements OperationIfc, SortableEntityIfc {
    private String operationname;
    private String operationcode;
    private String operationicon;
    private String iconId;
    private String functionId;

    @Column(name = "operationname", length = 50)
    public String getOperationname() {
        return this.operationname;
    }

    public void setOperationname(String operationname) {
        this.operationname = operationname;
    }

    @Column(name = "operationcode", length = 50)
    public String getOperationcode() {
        return this.operationcode;
    }

    public void setOperationcode(String operationcode) {
        this.operationcode = operationcode;
    }

    @Column(name = "operationicon", length = 100)
    public String getOperationicon() {
        return this.operationicon;
    }

    public void setOperationicon(String operationicon) {
        this.operationicon = operationicon;
    }

    @Column(name = "iconid")
    public String getIconId() {
        return iconId;
    }

    public void setIconId(String iconId) {
        this.iconId = iconId;
    }

    @Column(name = "functionid")
    public String getFunctionId() {
        return functionId;
    }

    public void setFunctionId(String functionId) {
        this.functionId = functionId;
    }
}
