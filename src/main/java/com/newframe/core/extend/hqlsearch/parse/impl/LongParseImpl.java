package com.newframe.core.extend.hqlsearch.parse.impl;

import com.newframe.core.extend.hqlsearch.HqlGenerateUtil;
import com.newframe.core.extend.hqlsearch.parse.IHqlParse;
import com.newframe.core.hibernate.qbc.CriteriaQuery;

public class LongParseImpl implements IHqlParse {

	
	public void addCriteria(CriteriaQuery cq, String name, Object value) {
		if (HqlGenerateUtil.isNotEmpty(value))
			cq.eq(name, value);		
	}

	
	public void addCriteria(CriteriaQuery cq, String name, Object value,
			String beginValue, String endValue) {
		if (HqlGenerateUtil.isNotEmpty(beginValue)) {
			cq.ge(name, Long.parseLong(beginValue));
		}
		if (HqlGenerateUtil.isNotEmpty(endValue)) {
			cq.le(name, Long.parseLong(endValue));
		}
		if (HqlGenerateUtil.isNotEmpty(value)) {
			cq.eq(name, value);
		}		
	}

}
