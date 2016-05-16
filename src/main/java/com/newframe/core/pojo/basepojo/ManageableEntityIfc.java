package com.newframe.core.pojo.basepojo;

import java.util.Date;

public interface ManageableEntityIfc {
	public String getCreatedBy();

	public Date getCreatedOn();

	public String getModifiedBy();

	public Date getModifiedOn();

	public void setCreatedBy(String createdBy);

	public void setCreatedOn(Date createdOn);

	public void setModifiedBy(String modifiedBy);

	public void setModifiedOn(Date modifiedOn);
}
