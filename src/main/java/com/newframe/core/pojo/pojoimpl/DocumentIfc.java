package com.newframe.core.pojo.pojoimpl;

import com.newframe.core.pojo.pojoimpl.impl.Type;

public interface DocumentIfc {
	public String getTypeId();

	public void setTypeId(String typeId);

	public String getDocumentTitle();

	public void setDocumentTitle(String documentTitle);

	public byte[] getPictureIndex();

	public void setPictureIndex(byte[] pictureIndex);

	public Short getDocumentState();

	public void setDocumentState(Short documentState);

	public Short getShowHome();

	public void setShowHome(Short showHome);
}
