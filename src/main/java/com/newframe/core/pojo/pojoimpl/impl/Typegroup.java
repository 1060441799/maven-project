package com.newframe.core.pojo.pojoimpl.impl;

import com.newframe.core.pojo.basepojo.SortableAndManageableEntity;
import com.newframe.core.pojo.pojoimpl.TypegroupIfc;
import com.newframe.core.pojo.basepojo.SortableEntityIfc;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "core_typegroup")
public class Typegroup extends SortableAndManageableEntity implements TypegroupIfc, SortableEntityIfc {
	public static Map<String, Typegroup> allTypeGroups = new HashMap<String, Typegroup>();
	public static Map<String, List<Type>> allTypes = new HashMap<String, List<Type>>();
	private String typegroupname;
	private String typegroupcode;
	private List<Type> types = new ArrayList<Type>();

	@Column(length = 50)
	public String getTypegroupname() {
		return this.typegroupname;
	}

	public void setTypegroupname(String typegroupname) {
		this.typegroupname = typegroupname;
	}

	@Column(length = 50)
	public String getTypegroupcode() {
		return this.typegroupcode;
	}

	public void setTypegroupcode(String typegroupcode) {
		this.typegroupcode = typegroupcode;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "typegroup")
	public List<Type> getTypes() {
		return this.types;
	}

	public void setTypes(List<Type> types) {
		this.types = types;
	}
}
