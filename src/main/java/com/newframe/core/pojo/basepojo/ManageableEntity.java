package com.newframe.core.pojo.basepojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;

@MappedSuperclass
public abstract class ManageableEntity extends StatusEntity implements IdEntityIfc, StatusEntityIfc, ManageableEntityIfc {

	private String createdBy;
	private Date createdOn;
	private String modifiedBy;
	private Date modifiedOn;

	@Column(name = "created_by", length = 100, nullable = true)
	@Length(max = 100)
	public String getCreatedBy() {
		// TODO Auto-generated method stub
		return createdBy;
	}

	@Column(name = "created_on", nullable = true)
	@Type(type = "com.newframe.core.util.DbDateType")
	public Date getCreatedOn() {
		// TODO Auto-generated method stub
		return createdOn;
	}

	@Column(name = "modified_by", length = 100, nullable = true)
	@Length(max = 100)
	public String getModifiedBy() {
		// TODO Auto-generated method stub
		return modifiedBy;
	}

	@Column(name = "modified_on", nullable = true)
	@Type(type = "com.newframe.core.util.DbDateType")
	public Date getModifiedOn() {
		// TODO Auto-generated method stub
		return modifiedOn;
	}

	public void setCreatedBy(String createdBy) {
		// TODO Auto-generated method stub
		this.createdBy = createdBy;
	}

	public void setCreatedOn(Date createdOn) {
		// TODO Auto-generated method stub
		this.createdOn = createdOn;
	}

	public void setModifiedBy(String modifiedBy) {
		// TODO Auto-generated method stub
		this.modifiedBy = modifiedBy;
	}

	public void setModifiedOn(Date modifiedOn) {
		// TODO Auto-generated method stub
		this.modifiedOn = modifiedOn;
	}

}
