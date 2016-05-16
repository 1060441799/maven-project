package com.newframe.core.pojo.pojoimpl.impl;

import com.newframe.core.pojo.pojoimpl.DocumentIfc;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "core_document")
@PrimaryKeyJoinColumn(name = "id")
public class Document extends Attachment implements DocumentIfc {
	private String documentTitle;// 文档标题
	private byte[] pictureIndex;// 焦点图导航
	private Short documentState;// 状态：0未发布，1已发布
	private Short showHome;// 是否首页显示
	private String typeId;// 文档分类

	@Column(name = "typeid")
	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	@Column(name = "documenttitle", length = 100)
	public String getDocumentTitle() {
		return documentTitle;
	}

	public void setDocumentTitle(String documentTitle) {
		this.documentTitle = documentTitle;
	}

	@Column(name = "pictureindex", length = 3000)
	public byte[] getPictureIndex() {
		return pictureIndex;
	}

	public void setPictureIndex(byte[] pictureIndex) {
		this.pictureIndex = pictureIndex;
	}

	@Column(name = "documentstate")
	public Short getDocumentState() {
		return documentState;
	}

	public void setDocumentState(Short documentState) {
		this.documentState = documentState;
	}

	@Column(name = "showhome")
	public Short getShowHome() {
		return showHome;
	}

	public void setShowHome(Short showHome) {
		this.showHome = showHome;
	}
}
