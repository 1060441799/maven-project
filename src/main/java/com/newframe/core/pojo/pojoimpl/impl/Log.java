package com.newframe.core.pojo.pojoimpl.impl;

import com.newframe.core.pojo.basepojo.SortableAndManageableEntity;
import com.newframe.core.pojo.basepojo.SortableEntityIfc;
import com.newframe.core.pojo.pojoimpl.LogIfc;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "core_log")
public class Log extends SortableAndManageableEntity implements LogIfc, SortableEntityIfc {
	private String userId;
	private Short loglevel;
	private Short operatetype;
	private String logcontent;
	private String broswer;// 用户浏览器类型
	private String note;

	@Column(name = "userid")
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "loglevel")
	public Short getLoglevel() {
		return this.loglevel;
	}

	public void setLoglevel(Short loglevel) {
		this.loglevel = loglevel;
	}

	@Column(name = "operatetype")
	public Short getOperatetype() {
		return this.operatetype;
	}

	public void setOperatetype(Short operatetype) {
		this.operatetype = operatetype;
	}

	@Column(name = "logcontent", nullable = false, length = 2000)
	public String getLogcontent() {
		return this.logcontent;
	}

	public void setLogcontent(String logcontent) {
		this.logcontent = logcontent;
	}

	@Column(name = "note", length = 300)
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Column(name = "broswer", length = 100)
	public String getBroswer() {
		return broswer;
	}

	public void setBroswer(String broswer) {
		this.broswer = broswer;
	}
}
