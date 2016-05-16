package com.newframe.core.pojo.pojoimpl;

import com.newframe.core.pojo.pojoimpl.impl.Function;
import com.newframe.core.pojo.pojoimpl.impl.Role;

public interface RelRoleFunctionIfc {
	public Function getFunction();

	public void setFunction(Function function);

	public Role getRole();

	public void setRole(Role role);

	public String getOperation();

	public void setOperation(String operation);

}
