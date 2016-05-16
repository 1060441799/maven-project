package com.newframe.core.pojo.pojoimpl.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.newframe.core.pojo.basepojo.SortableAndManageableEntity;
import com.newframe.core.pojo.basepojo.SortableEntityIfc;
import com.newframe.core.pojo.pojoimpl.DepartIfc;

@Entity
@Table(name = "core_depart")
public class Depart extends SortableAndManageableEntity implements DepartIfc, SortableEntityIfc {
	private Depart parentDepart;// 上级部门
	private String departname;// 部门名称
	private String description;// 部门描述
	private List<Depart> departs = new ArrayList<Depart>();// 下属部门

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parentdepartid")
	public Depart getParentDepart() {
		return this.parentDepart;
	}

	public void setParentDepart(Depart parentDepart) {
		this.parentDepart = parentDepart;
	}

	@Column(name = "departname", nullable = false, length = 100)
	public String getDepartname() {
		return this.departname;
	}

	public void setDepartname(String departname) {
		this.departname = departname;
	}

	@Column(name = "description", length = 500)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parentDepart")
	public List<Depart> getDeparts() {
		return departs;
	}

	public void setDeparts(List<Depart> departs) {
		this.departs = departs;
	}
}
