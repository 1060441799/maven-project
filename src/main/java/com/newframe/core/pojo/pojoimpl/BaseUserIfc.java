package com.newframe.core.pojo.pojoimpl;

import com.newframe.core.pojo.pojoimpl.impl.Depart;

public interface BaseUserIfc {

	public byte[] getSignature();

	public void setSignature(byte[] signature);

	public String getBrowser();

	public void setBrowser(String browser);

	public String getUserKey();

	public void setUserKey(String userKey);

	public Short getActivitiSync();

	public void setActivitiSync(Short activitiSync);

	public String getPassword();

	public void setPassword(String password);

	public String getDepartId();

	public void setDepartId(String departId);

	public String getUserName();

	public void setUserName(String userName);

	public String getRealName();

	public void setRealName(String realName);
}
