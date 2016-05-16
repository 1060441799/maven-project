package com.newframe.core.pojo.pojoimpl;

import com.newframe.core.pojo.pojoimpl.impl.User;

public interface AttachmentIfc {

	public String getExtend();

	public void setExtend(String extend);

	public String getUserId();

	public void setUserId(String userId);

	public String getBusinessKey();

	public void setBusinessKey(String businessKey);

	public String getAttachmenttitle();

	public void setAttachmenttitle(String attachmenttitle);

	public byte[] getAttachmentcontent();

	public void setAttachmentcontent(byte[] attachmentcontent);

	public String getRealpath();

	public void setRealpath(String realpath);

	public String getNote();

	public void setNote(String note);

	public String getSwfpath();

	public void setSwfpath(String swfpath);

	public String getSubclassname();

	public void setSubclassname(String subclassname);

}
