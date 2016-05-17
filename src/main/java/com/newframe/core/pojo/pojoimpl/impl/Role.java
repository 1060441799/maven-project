package com.newframe.core.pojo.pojoimpl.impl;

import com.newframe.core.pojo.basepojo.SortableAndManageableEntity;
import com.newframe.core.pojo.basepojo.SortableEntityIfc;
import com.newframe.core.pojo.pojoimpl.RoleIfc;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "core_role")
public class Role extends SortableAndManageableEntity implements RoleIfc, SortableEntityIfc {
    private String roleName;//角色名称
    private String roleCode;//角色编码

    @Column(name = "rolename", nullable = false, length = 100)
    public String getRoleName() {
        return this.roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Column(name = "rolecode", length = 10)
    public String getRoleCode() {
        return this.roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public Role() {
    }

    public Role(String roleName, String roleCode) {
        this.roleCode = roleCode;
        this.roleName = roleName;
    }
}
