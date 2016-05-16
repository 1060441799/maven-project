package com.newframe.web.service;

import com.newframe.core.service.CommonService;

/** 
 * @Description 修复数据库Service
 * @ClassName: RepairService
 */ 
public interface RepairService  extends CommonService {

	/** 
	 * @Description  修复数据库
	 */
	public void repair();

	/** 
	 * @Description  先清空数据库，然后再修复数据库
	 */
	public void deleteAndRepair();

}
