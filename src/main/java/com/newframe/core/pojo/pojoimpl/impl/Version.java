package com.newframe.core.pojo.pojoimpl.impl;

import com.newframe.core.pojo.basepojo.SortableAndManageableEntity;
import com.newframe.core.pojo.basepojo.SortableEntityIfc;
import com.newframe.core.pojo.pojoimpl.VersionIfc;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "core_version")
public class Version extends SortableAndManageableEntity implements VersionIfc, SortableEntityIfc {
	private String versionName;// 版本名称
	private String versionCode;// 版本编码
	private String loginPage;// 登陆入口页面
	private String versionNum;// 版本号

	@Column(length = 30)
	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	@Column(length = 50)
	public String getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}

	@Column(length = 100)
	public String getLoginPage() {
		return loginPage;
	}

	public void setLoginPage(String loginPage) {
		this.loginPage = loginPage;
	}

	@Column(length = 20)
	public String getVersionNum() {
		return versionNum;
	}

	public void setVersionNum(String versionNum) {
		this.versionNum = versionNum;
	}
}
