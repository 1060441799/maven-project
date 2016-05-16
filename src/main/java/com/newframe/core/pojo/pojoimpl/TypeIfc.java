package com.newframe.core.pojo.pojoimpl;

import com.newframe.core.pojo.pojoimpl.impl.Typegroup;

import java.util.List;

import com.newframe.core.pojo.pojoimpl.impl.Type;

public interface TypeIfc {

	public Typegroup getTypegroup();

	public void setTypegroup(Typegroup typegroup);

	public Type getParentType();

	public void setParentType(Type parentType);

	public String getTypename();

	public void setTypename(String typename);

	public String getTypecode();

	public void setTypecode(String typecode);

	public List<Type> getTypes();

	public void setTypes(List<Type> types);

}
