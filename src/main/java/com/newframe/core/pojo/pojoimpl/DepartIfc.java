package com.newframe.core.pojo.pojoimpl;

import java.util.List;

import com.newframe.core.pojo.pojoimpl.impl.Depart;

public interface DepartIfc {

	public Depart getParentDepart();

	public void setParentDepart(Depart depart);

	public String getDepartname();

	public void setDepartname(String departname);

	public String getDescription();

	public void setDescription(String description);

	public List<Depart> getDeparts();

	public void setDeparts(List<Depart> departs);
}
