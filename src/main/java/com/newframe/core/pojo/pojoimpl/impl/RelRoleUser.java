package com.newframe.core.pojo.pojoimpl.impl;

import com.newframe.core.pojo.basepojo.IdEntity;
import com.newframe.core.pojo.basepojo.IdEntityIfc;
import com.newframe.core.pojo.pojoimpl.RelRoleUserIfc;

import javax.persistence.*;

@Entity
@Table(name = "core_rel_role_user")
public class RelRoleUser extends IdEntity implements RelRoleUserIfc, IdEntityIfc {
    private User user;
    private Role role;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userid")
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "roleid")
    public Role getRole() {
        return this.role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
