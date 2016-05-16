package com.newframe.core.pojo.pojoimpl;

import com.newframe.core.pojo.pojoimpl.impl.Type;
import java.util.List;

public interface TypegroupIfc {

	public String getTypegroupname();

	public void setTypegroupname(String typegroupname);

	public String getTypegroupcode();

	public void setTypegroupcode(String typegroupcode);

	public List<Type> getTypes();

	public void setTypes(List<Type> types);

}
