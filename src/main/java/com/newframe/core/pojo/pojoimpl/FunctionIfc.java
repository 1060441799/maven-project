package com.newframe.core.pojo.pojoimpl;

import com.newframe.core.pojo.pojoimpl.impl.Function;
import com.newframe.core.pojo.pojoimpl.impl.Icon;

import java.util.List;

public interface FunctionIfc {
	public String getIconDeskId();

	public void setIconDeskId(String iconDeskId);

	public String getIconId();

	public void setIconId(String iconId);

	public Function getParentFunction();

	public void setParentFunction(Function parentfunction);

	public String getFunctionName();

	public void setFunctionName(String functionName);

	public Short getFunctionLevel();

	public void setFunctionLevel(Short functionLevel);

	public String getFunctionUrl();

	public void setFunctionUrl(String functionUrl);

	public String getFunctionOrder();

	public void setFunctionOrder(String functionOrder);

	public List<Function> getFunctions();

	public void setFunctions(List<Function> functions);

	public Short getFunctionIframe();

	public void setFunctionIframe(Short functionIframe);

}
