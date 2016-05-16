package com.newframe.core.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;

import com.newframe.core.hibernate.qbc.CriteriaQuery;
import com.newframe.core.hibernate.qbc.HqlQuery;
import com.newframe.core.hibernate.qbc.PageList;
import com.newframe.core.web.model.common.DBTable;
import com.newframe.core.web.model.json.DataGridReturn;

public interface IGenericBaseCommonDao {
	public List<DBTable> getAllDbTableName();

	public Integer getAllDbTableSize();

	public <T> Serializable save(T entity);

	public <T> void batchSave(List<T> entitys);

	public <T> void saveOrUpdate(T entity);

	public <T> void delete(T entitie);

	public <T> T get(Class<T> entityName, Serializable id);

	public <T> T findUniqueByProperty(Class<T> entityClass, String propertyName, Object value);

	public <T> List<T> findByProperty(Class<T> entityClass, String propertyName, Object value);

	public <T> List<T> loadAll(final Class<T> entityClass);

	public <T> T getEntity(Class entityName, Serializable id);

	public <T> void deleteEntityById(Class entityName, Serializable id);

	public <T> void deleteAllEntitie(Collection<T> entities);

	public <T> void updateEntitie(T pojo);

	public <T> void updateEntityById(Class entityName, Serializable id);

	public <T> List<T> findByQueryString(String hql);

	public <T> T singleResult(String hql);

	public int updateBySqlString(String sql);

	public <T> List<T> findListbySql(String query);

	public <T> List<T> findByPropertyisOrder(Class<T> entityClass, String propertyName, Object value, boolean isAsc);

	public PageList getPageList(final CriteriaQuery cq, final boolean isOffset);

	public <T> List<T> getListByCriteriaQuery(final CriteriaQuery cq, Boolean ispage);

	public PageList getPageList(final HqlQuery hqlQuery, final boolean needParameter);

	public PageList getPageListBySql(final HqlQuery hqlQuery, final boolean needParameter);

	public Session getCurrentSession();

	public List findByExample(final String entityName, final Object exampleEntity);

	public Map<Object, Object> getHashMapbyQuery(String query);

	public DataGridReturn getDataGridReturn(final CriteriaQuery cq, final boolean isOffset);

	public Integer executeSql(String sql, List<Object> param);

	public Integer executeSql(String sql, Object... param);

	public Integer executeSql(String sql, Map<String, Object> param);

	public Object executeSqlReturnKey(String sql, Map<String, Object> param);

	public List<Map<String, Object>> findForJdbc(String sql, Object... objs);

	public Map<String, Object> findOneForJdbc(String sql, Object... objs);

	public List<Map<String, Object>> findForJdbc(String sql, int page, int rows);

	public <T> List<T> findObjForJdbc(String sql, int page, int rows, Class<T> clazz);

	public List<Map<String, Object>> findForJdbcParam(String sql, int page, int rows, Object... objs);

	public Long getCountForJdbc(String sql);

	public Long getCountForJdbcParam(String sql, Object[] objs);

	public <T> List<T> findHql(String hql, Object... param);

	public Integer executeHql(String hql);

	public <T> List<T> pageList(DetachedCriteria dc, int firstResult, int maxResult);

	public <T> List<T> findByDetached(DetachedCriteria dc);
}
