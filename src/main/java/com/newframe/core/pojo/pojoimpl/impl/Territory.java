package com.newframe.core.pojo.pojoimpl.impl;

import com.newframe.core.pojo.basepojo.SortableAndManageableEntity;
import com.newframe.core.pojo.basepojo.SortableEntityIfc;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.newframe.core.pojo.pojoimpl.TerritoryIfc;

@Entity
@Table(name = "core_territory")
public class Territory extends SortableAndManageableEntity implements TerritoryIfc, SortableEntityIfc {
	private static final long serialVersionUID = 1L;
	private Territory parentTerritory;//父地域
	private String territoryName;//地域名称
	private Short territoryLevel;//等级
	private String territorySort;//同区域中的显示顺序
	private String territoryCode;//区域码
	private String territoryPinyin;//区域名称拼音
	private double xwgs84;//wgs84格式经度(mapabc 的坐标系)
	private double ywgs84;//wgs84格式纬度(mapabc 的坐标系)
	private List<Territory> territorys = new ArrayList<Territory>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "territoryparentid")
//	@ForeignKey(name="null")//取消hibernate的外键生成
	public Territory getParentTerritory() {
		return this.parentTerritory;
	}
	public void setParentTerritory(Territory parentTerritory) {
		this.parentTerritory = parentTerritory;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parentTerritory")
	public List<Territory> getTerritorys() {
		return territorys;
	}
	public void setTerritorys(List<Territory> territorys) {
		this.territorys = territorys;
	}
	@Column(name = "territoryname", nullable = false, length = 50)
	public String getTerritoryName() {
		return territoryName;
	}

	public void setTerritoryName(String territoryName) {
		this.territoryName = territoryName;
	}

	@Column(name = "territorysort",nullable = false,length = 3)
	public String getTerritorySort() {
		return territorySort;
	}

	public void setTerritorySort(String territorySort) {
		this.territorySort = territorySort;
	}
	@Column(name = "territorylevel",nullable = false,length = 1)
	public Short getTerritoryLevel() {
		return territoryLevel;
	}
	public void setTerritoryLevel(Short territoryLevel) {
		this.territoryLevel = territoryLevel;
	}
	@Column(name = "territorycode",nullable = false,length = 10)
	public String getTerritoryCode() {
		return territoryCode;
	}
	public void setTerritoryCode(String territoryCode) {
		this.territoryCode = territoryCode;
	}
	@Column(name = "territory_pinyin",nullable = true,length = 40)
	public String getTerritoryPinyin() {
		return territoryPinyin;
	}
	public void setTerritoryPinyin(String territoryPinyin) {
		this.territoryPinyin = territoryPinyin;
	}
	@Column(name = "x_wgs84",nullable = false,length = 40)
	public double getXwgs84() {
		return xwgs84;
	}
	public void setXwgs84(double xwgs84) {
		this.xwgs84 = xwgs84;
	}
	@Column(name = "y_wgs84",nullable = false,length = 40)
	public double getYwgs84() {
		return ywgs84;
	}
	public void setYwgs84(double ywgs84) {
		this.ywgs84 = ywgs84;
	}
}
