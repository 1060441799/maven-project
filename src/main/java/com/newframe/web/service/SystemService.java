package com.newframe.web.service;

import com.newframe.core.pojo.pojoimpl.impl.Function;
import com.newframe.core.pojo.pojoimpl.impl.Typegroup;
import com.newframe.core.pojo.pojoimpl.impl.Type;
import com.newframe.core.service.CommonService;
import com.newframe.core.vo.DictEntity;
import java.util.List;
import java.util.Set;

import com.newframe.core.pojo.pojoimpl.impl.User;

public interface SystemService extends CommonService {
	/**
 	 * 方法描述:  查询数据字典
 	 * @param dicTable
 	 * @param dicCode
 	 * @param dicText
 	 * @return 
 	 * 返回类型： List<DictEntity>
 	 */
 	public List<DictEntity> queryDict(String dicTable, String dicCode, String dicText);
	
	/**
	 * 登陆用户检查
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public User checkUserExits(User user) throws Exception;
	/**
	 * 日志添加
	 * @param LogContent 内容
	 * @param loglevel 级别
	 * @param operatetype 类型
	 * @param TUser 操作人
	 */
	public void addLog(String LogContent, Short loglevel,Short operatetype);
	/**
	 * 根据类型编码和类型名称获取Type,如果为空则创建一个
	 * @param typecode
	 * @param typename
	 * @return
	 */
	public Type getType(String typecode, String typename, Typegroup tsTypegroup);
	/**
	 * 根据类型分组编码和名称获取TypeGroup,如果为空则创建一个
	 * @param typecode
	 * @param typename
	 * @return
	 */
	public Typegroup getTypeGroup(String typegroupcode,String typgroupename);
	/**
	 * 根据用户ID 和 菜单Id 获取 具有操作权限的按钮Codes
	 * @param roleId
	 * @param functionId
	 * @return
	 */
	public  Set<String> getOperationCodesByUserIdAndFunctionId(String userId,String functionId);
	/**
	 * 根据角色ID 和 菜单Id 获取 具有操作权限的按钮Codes
	 * @param roleId
	 * @param functionId
	 * @return
	 */
	public  Set<String> getOperationCodesByRoleIdAndFunctionId(String roleId,String functionId);
	/**
	 * 根据编码获取字典组
	 * 
	 * @param typegroupCode
	 * @return
	 */
	public Typegroup getTypeGroupByCode(String typegroupCode);
	/**
	 * 对数据字典进行缓存
	 */
	public void initAllTypeGroups();
	
	/**
	 * 刷新字典缓存
	 * @param type
	 */
	public void refleshTypesCach(Type type);
	/**
	 * 刷新字典分组缓存
	 */
	public void refleshTypeGroupCach();
	/**
	 * 刷新菜单
	 * 
	 * @param id
	 */
	public void flushRoleFunciton(String id, Function newFunciton);

	
}
