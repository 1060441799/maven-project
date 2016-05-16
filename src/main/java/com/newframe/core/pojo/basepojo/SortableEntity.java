package com.newframe.core.pojo.basepojo;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

@MappedSuperclass
public abstract class SortableEntity extends ManageableEntity
		implements IdEntityIfc, StatusEntityIfc, ManageableEntityIfc, SortableEntityIfc {

	private Integer orderNum;

	@NotNull
	@Column(name = "order_num", nullable = true)
	public Integer getOrderNum() {
		// TODO Auto-generated method stub
		return orderNum;
	}
	public void setOrderNum(Integer orderNum) {
		// TODO Auto-generated method stub
		this.orderNum = orderNum;
	}

}
