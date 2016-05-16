package com.newframe.core.pojo.basepojo;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class StatusEntity extends IdEntity implements StatusEntityIfc, IdEntityIfc {

	private String status;

	public void setStatus(String status) {
		// TODO Auto-generated method stub
		this.status = status;
	}

	@Column(name = "status", length = 50, nullable = true)
	public String getStatus() {
		// TODO Auto-generated method stub
		return this.status;
	}

}
