package com.newframe.core.extend.hqlsearch.parse;

import com.newframe.core.hibernate.qbc.CriteriaQuery;

/**
 * 解析拼装
 */
public interface IHqlParse {
	/**
	 * 单值组装
	 * 
	 * @param name
	 * @param value
	 */
	public void addCriteria(CriteriaQuery cq, String name, Object value);

	/**
	 * 范围组装
	 * 
	 * @param name
	 * @param value
	 * @param beginValue
	 * @param endValue
	 */
	public void addCriteria(CriteriaQuery cq, String name, Object value, String beginValue, String endValue);

}
