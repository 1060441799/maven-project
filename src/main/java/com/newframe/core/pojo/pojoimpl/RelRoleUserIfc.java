package com.newframe.core.pojo.pojoimpl;

import com.newframe.core.pojo.pojoimpl.impl.Role;
import com.newframe.core.pojo.pojoimpl.impl.User;

public interface RelRoleUserIfc {
	public User getUser();

	public void setUser(User user);

	public Role getRole();

	public void setRole(Role role);

}
