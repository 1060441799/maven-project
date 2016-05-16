package com.newframe.core.pojo.pojoimpl;

import com.newframe.core.pojo.pojoimpl.impl.User;

public interface LogIfc {

	public String getUserId();

	public void setUserId(String userId);

	public Short getLoglevel();

	public void setLoglevel(Short loglevel);

	public Short getOperatetype();

	public void setOperatetype(Short operatetype);

	public String getLogcontent();

	public void setLogcontent(String logcontent);

	public String getNote();

	public void setNote(String note);

	public String getBroswer();

	public void setBroswer(String broswer);

}
