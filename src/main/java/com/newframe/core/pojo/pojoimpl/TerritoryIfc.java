package com.newframe.core.pojo.pojoimpl;

import com.newframe.core.pojo.pojoimpl.impl.Territory;

import java.util.List;

public interface TerritoryIfc {

	public Territory getParentTerritory();

	public void setParentTerritory(Territory perentTerritory);

	public List<Territory> getTerritorys();

	public void setTerritorys(List<Territory> territorys);

	public String getTerritoryName();

	public void setTerritoryName(String territoryName);

	public String getTerritorySort();

	public void setTerritorySort(String territorySort);

	public Short getTerritoryLevel();

	public void setTerritoryLevel(Short territoryLevel);

	public String getTerritoryCode();

	public void setTerritoryCode(String territoryCode);

	public String getTerritoryPinyin();

	public void setTerritoryPinyin(String territoryPinyin);

	public double getXwgs84();

	public void setXwgs84(double xwgs84);

	public double getYwgs84();

	public void setYwgs84(double ywgs84);

}
