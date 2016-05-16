package com.newframe.core.pojo.pojoimpl.impl;

import com.newframe.core.pojo.basepojo.IdEntity;
import com.newframe.core.pojo.basepojo.IdEntityIfc;
import com.newframe.core.pojo.pojoimpl.RelRoleFunctionIfc;

import javax.persistence.*;

@Entity
@Table(name = "core_rel_role_function")
public class RelRoleFunction extends IdEntity implements RelRoleFunctionIfc, IdEntityIfc {
    private Function function;
    private Role role;
    private String operation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "functionid")
    public Function getFunction() {
        return this.function;
    }

    public void setFunction(Function function) {
        this.function = function;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roleid")
    public Role getRole() {
        return this.role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Column(name = "operation", length = 100)
    public String getOperation() {
        return this.operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

}
