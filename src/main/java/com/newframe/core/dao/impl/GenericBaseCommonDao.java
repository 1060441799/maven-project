package com.newframe.core.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.util.Assert;

import com.newframe.core.annotation.EntityTitle;
import com.newframe.core.dao.IGenericBaseCommonDao;
import com.newframe.core.dao.jdbc.JdbcDao;
import com.newframe.core.exception.BusinessException;
import com.newframe.core.hibernate.qbc.CriteriaQuery;
import com.newframe.core.hibernate.qbc.HqlQuery;
import com.newframe.core.hibernate.qbc.PageList;
import com.newframe.core.hibernate.qbc.PagerUtil;
import com.newframe.core.util.LogUtil;
import com.newframe.core.util.MyBeanUtils;
import com.newframe.core.util.ToEntityUtil;
import com.newframe.core.util.oConvertUtils;
import com.newframe.core.web.model.common.DBTable;

@SuppressWarnings("hiding")
public abstract class GenericBaseCommonDao<T, PK extends Serializable> implements IGenericBaseCommonDao {
	private static final Logger logger = Logger.getLogger(GenericBaseCommonDao.class);
	/**
	 * 注入一个sessionFactory属性,并注入到父类(HibernateDaoSupport)
	 **/
	@Autowired
	@Qualifier("sessionFactory")
	public SessionFactory sessionFactory;

	@PersistenceContext(unitName = "entityManagerFactory")
	private EntityManager entityManager;

	public Session getCurrentSession() {
		// 事务必须是开启的，否则获取不到
		return sessionFactory.getCurrentSession();
		// return entityManager.unwrap(Session.class);
	}

	public EntityManager getEntityManager() {
		return this.entityManager;
	}

	/**
	 * 获得该类的属性和类型
	 * 
	 * @param entityName
	 *            注解的实体类
	 */
	private <T> void getProperty(Class entityName) {
		ClassMetadata cm = sessionFactory.getClassMetadata(entityName);
		// ClassMetadata cm =
		// getCurrentSession().getSessionFactory().getClassMetadata(entityName);
		String[] str = cm.getPropertyNames(); // 获得该类所有的属性名称

		for (int i = 0; i < str.length; i++) {
			String property = str[i];
			String type = cm.getPropertyType(property).getName(); // 获得该名称的类型
			LogUtil.info(property + "---&gt;" + type);
		}
	}

	public List<DBTable> getAllDbTableName() {
		List<DBTable> resultList = new ArrayList<DBTable>();
		SessionFactory factory = getCurrentSession().getSessionFactory();
		Map<String, ClassMetadata> metaMap = factory.getAllClassMetadata();
		for (String key : (Set<String>) metaMap.keySet()) {
			DBTable dbTable = new DBTable();
			AbstractEntityPersister classMetadata = (AbstractEntityPersister) metaMap.get(key);
			dbTable.setTableName(classMetadata.getTableName());
			dbTable.setEntityName(classMetadata.getEntityName());
			Class<?> c;
			try {
				c = Class.forName(key);
				EntityTitle t = c.getAnnotation(EntityTitle.class);
				dbTable.setTableTitle(t != null ? t.name() : "");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			resultList.add(dbTable);
		}
		return resultList;
	}

	public Integer getAllDbTableSize() {
		SessionFactory factory = getCurrentSession().getSessionFactory();
		Map<String, ClassMetadata> metaMap = factory.getAllClassMetadata();
		return metaMap.size();
	}

	public <T> T findUniqueByProperty(Class<T> entityClass, String propertyName, Object value) {
		Assert.hasText(propertyName);
		return (T) createCriteria(entityClass, Restrictions.eq(propertyName, value)).uniqueResult();
	}

	public <T> List<T> findByProperty(Class<T> entityClass, String propertyName, Object value) {
		Assert.hasText(propertyName);
		return (List<T>) createCriteria(entityClass, Restrictions.eq(propertyName, value)).list();
	}

	public <T> Serializable save(T entity) {
		try {
			Serializable id = getCurrentSession().save(entity);
			getCurrentSession().flush();
			if (logger.isDebugEnabled()) {
				logger.debug("保存实体成功," + entity.getClass().getName());
			}
			return id;
		} catch (RuntimeException e) {
			logger.error("保存实体异常", e);
			throw e;
		}

	}

	public <T> void batchSave(List<T> entitys) {
		for (int i = 0; i < entitys.size(); i++) {
			getCurrentSession().save(entitys.get(i));
			if (i % 20 == 0) {
				// 20个对象后才清理缓存，写入数据库
				getCurrentSession().flush();
				getCurrentSession().clear();
			}
		}
		// 最后清理一下----防止大于20小于40的不保存
		getCurrentSession().flush();
		getCurrentSession().clear();
	}

	public <T> void saveOrUpdate(T entity) {
		try {
			getCurrentSession().saveOrUpdate(entity);
			getCurrentSession().flush();
			if (logger.isDebugEnabled()) {
				logger.debug("添加或更新成功," + entity.getClass().getName());
			}
		} catch (RuntimeException e) {
			logger.error("添加或更新异常", e);
			throw e;
		}
	}

	public <T> void delete(T entity) {
		try {
			getCurrentSession().delete(entity);
			getCurrentSession().flush();
			if (logger.isDebugEnabled()) {
				logger.debug("删除成功," + entity.getClass().getName());
			}
		} catch (RuntimeException e) {
			logger.error("删除异常", e);
			throw e;
		}
	}

	public <T> void deleteEntityById(Class entityName, Serializable id) {
		delete(get(entityName, id));
		getCurrentSession().flush();
	}

	public <T> void deleteAllEntitie(Collection<T> entitys) {
		for (Object entity : entitys) {
			getCurrentSession().delete(entity);
			getCurrentSession().flush();
		}
	}

	public <T> T get(Class<T> entityClass, final Serializable id) {

		return (T) getCurrentSession().get(entityClass, id);

	}

	public <T> T getEntity(Class entityName, Serializable id) {

		T t = (T) getCurrentSession().get(entityName, id);
		if (t != null) {
			getCurrentSession().flush();
		}
		return t;
	}

	public <T> void updateEntitie(T pojo) {
		getCurrentSession().update(pojo);
		getCurrentSession().flush();
	}

	public <T> void updateEntitie(String className, Object id) {
		getCurrentSession().update(className, id);
		getCurrentSession().flush();
	}

	public <T> void updateEntityById(Class entityName, Serializable id) {
		updateEntitie(get(entityName, id));
	}

	public List<T> findByQueryString(final String query) {

		Query queryObject = getCurrentSession().createQuery(query);
		List<T> list = queryObject.list();
		if (list.size() > 0) {
			getCurrentSession().flush();
		}
		return list;

	}

	public <T> T singleResult(String hql) {
		T t = null;
		Query queryObject = getCurrentSession().createQuery(hql);
		List<T> list = queryObject.list();
		if (list.size() == 1) {
			getCurrentSession().flush();
			t = list.get(0);
		} else if (list.size() > 0) {
			throw new BusinessException("查询结果数:" + list.size() + "大于1");
		}
		return t;
	}

	public Map<Object, Object> getHashMapbyQuery(String hql) {

		Query query = getCurrentSession().createQuery(hql);
		List list = query.list();
		Map<Object, Object> map = new HashMap<Object, Object>();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Object[] tm = (Object[]) iterator.next();
			map.put(tm[0].toString(), tm[1].toString());
		}
		return map;

	}

	public int updateBySqlString(final String query) {

		Query querys = getCurrentSession().createSQLQuery(query);
		return querys.executeUpdate();
	}

	public List<T> findListbySql(final String sql) {
		Query querys = getCurrentSession().createSQLQuery(sql);
		return querys.list();
	}

	private <T> Criteria createCriteria(Class<T> entityClass, boolean isAsc, Criterion... criterions) {
		Criteria criteria = createCriteria(entityClass, criterions);
		if (isAsc) {
			criteria.addOrder(Order.asc("asc"));
		} else {
			criteria.addOrder(Order.desc("desc"));
		}
		return criteria;
	}

	private <T> Criteria createCriteria(Class<T> entityClass, Criterion... criterions) {
		Criteria criteria = getCurrentSession().createCriteria(entityClass);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}

	public <T> List<T> loadAll(final Class<T> entityClass) {
		Criteria criteria = createCriteria(entityClass);
		return criteria.list();
	}

	private <T> Criteria createCriteria(Class<T> entityClass) {
		Criteria criteria = getCurrentSession().createCriteria(entityClass);
		return criteria;
	}

	/**
	 * 根据属性名和属性值查询. 有排序
	 * 
	 * @param <T>
	 * @param entityClass
	 * @param propertyName
	 * @param value
	 * @param orderBy
	 * @param isAsc
	 * @return
	 */
	public <T> List<T> findByPropertyisOrder(Class<T> entityClass, String propertyName, Object value, boolean isAsc) {
		Assert.hasText(propertyName);
		return createCriteria(entityClass, isAsc, Restrictions.eq(propertyName, value)).list();
	}

	/**
	 * 根据属性名和属性值 查询 且要求对象唯一.
	 * 
	 * @return 符合条件的唯一对象.
	 */
	public <T> T findUniqueBy(Class<T> entityClass, String propertyName, Object value) {
		Assert.hasText(propertyName);
		return (T) createCriteria(entityClass, Restrictions.eq(propertyName, value)).uniqueResult();
	}

	public Query createQuery(Session session, String hql, Object... objects) {
		Query query = session.createQuery(hql);
		if (objects != null) {
			for (int i = 0; i < objects.length; i++) {
				query.setParameter(i, objects[i]);
			}
		}
		return query;
	}

	public <T> int batchInsertsEntitie(List<T> entityList) {
		int num = 0;
		for (int i = 0; i < entityList.size(); i++) {
			save(entityList.get(i));
			num++;
		}
		return num;
	}

	/**
	 * 使用占位符的方式填充值 请注意：like对应的值格式："%"+username+"%" Hibernate Query
	 * 
	 * @param hibernateTemplate
	 * @param hql
	 * @param valus
	 *            占位符号?对应的值，顺序必须一一对应 可以为空对象数组，但是不能为null
	 */
	public List<T> executeQuery(final String hql, final Object[] values) {
		Query query = getCurrentSession().createQuery(hql);
		// query.setCacheable(true);
		for (int i = 0; values != null && i < values.length; i++) {
			query.setParameter(i, values[i]);
		}

		return query.list();

	}

	public List findByExample(final String entityName, final Object exampleEntity) {
		Assert.notNull(exampleEntity, "Example pojoimpl must not be null");
		Criteria executableCriteria = (entityName != null ? getCurrentSession().createCriteria(entityName)
				: getCurrentSession().createCriteria(exampleEntity.getClass()));
		executableCriteria.add(Example.create(exampleEntity));
		return executableCriteria.list();
	}

	// 使用指定的检索标准获取满足标准的记录数
	public Integer getRowCount(DetachedCriteria criteria) {
		return oConvertUtils.getInt(((Criteria) criteria.setProjection(Projections.rowCount())).uniqueResult(), 0);
	}

	public void callableStatementByName(String proc) {
	}

	public int getCount(Class<T> clazz) {

		int count = DataAccessUtils
				.intResult(getCurrentSession().createQuery("select count(*) from " + clazz.getName()).list());
		return count;
	}

	public PageList getPageList(final CriteriaQuery cq, final boolean isOffset) {

		Criteria criteria = cq.getDetachedCriteria().getExecutableCriteria(getCurrentSession());
		CriteriaImpl impl = (CriteriaImpl) criteria;
		// 先把Projection和OrderBy条件取出来,清空两者来执行Count操作
		Projection projection = impl.getProjection();
		final int allCounts = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
		criteria.setProjection(projection);
		if (projection == null) {
			criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		}

		// 判断是否有排序字段
		if (cq.getOrdermap() != null) {
			// cq.setOrder(cq.getOrdermap());
		}
		int pageSize = cq.getPageSize();// 每页显示数
		int curPageNO = PagerUtil.getcurPageNo(allCounts, cq.getCurPage(), pageSize);// 当前页
		int offset = PagerUtil.getOffset(allCounts, curPageNO, pageSize);
		String toolBar = "";
		if (isOffset) {// 是否分页
			criteria.setFirstResult(offset);
			criteria.setMaxResults(cq.getPageSize());
			if (cq.getIsUseimage() == 1) {
				toolBar = PagerUtil.getBar(cq.getMyAction(), cq.getMyForm(), allCounts, curPageNO, pageSize,
						cq.getMap());
			} else {
				toolBar = PagerUtil.getBar(cq.getMyAction(), allCounts, curPageNO, pageSize, cq.getMap());
			}
		} else {
			pageSize = allCounts;
		}
		return new PageList(criteria.list(), toolBar, offset, curPageNO, allCounts);
	}

	@SuppressWarnings("unchecked")
	public PageList getPageListBySql(final HqlQuery hqlQuery, final boolean isToEntity) {

		Query query = getCurrentSession().createSQLQuery(hqlQuery.getQueryString());

		// query.setParameters(hqlQuery.getParam(), (Type[])
		// hqlQuery.getTypes());
		int allCounts = query.list().size();
		int curPageNO = hqlQuery.getCurPage();
		int offset = PagerUtil.getOffset(allCounts, curPageNO, hqlQuery.getPageSize());
		query.setFirstResult(offset);
		query.setMaxResults(hqlQuery.getPageSize());
		List list = null;
		if (isToEntity) {
			list = ToEntityUtil.toEntityList(query.list(), hqlQuery.getClass1(),
					hqlQuery.getDataGrid().getField().split(","));
		} else {
			list = query.list();
		}
		return new PageList(hqlQuery, list, offset, curPageNO, allCounts);
	}

	@SuppressWarnings("unchecked")
	public PageList getPageList(final HqlQuery hqlQuery, final boolean needParameter) {

		Query query = getCurrentSession().createQuery(hqlQuery.getQueryString());
		if (needParameter) {
			query.setParameters(hqlQuery.getParam(), (Type[]) hqlQuery.getTypes());
		}
		int allCounts = query.list().size();
		int curPageNO = hqlQuery.getCurPage();
		int offset = PagerUtil.getOffset(allCounts, curPageNO, hqlQuery.getPageSize());
		String toolBar = PagerUtil.getBar(hqlQuery.getMyaction(), allCounts, curPageNO, hqlQuery.getPageSize(),
				hqlQuery.getMap());
		query.setFirstResult(offset);
		query.setMaxResults(hqlQuery.getPageSize());
		return new PageList(query.list(), toolBar, offset, curPageNO, allCounts);
	}

	@SuppressWarnings("unchecked")
	public List<T> getListByCriteriaQuery(final CriteriaQuery cq, Boolean ispage) {
		Criteria criteria = cq.getDetachedCriteria().getExecutableCriteria(getCurrentSession());
		// 判断是否有排序字段
		if (cq.getOrdermap() != null) {
			// cq.setOrder(cq.getOrdermap());
		}
		if (ispage)
			criteria.setMaxResults(cq.getPageSize());
		return criteria.list();

	}

	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier("namedParameterJdbcTemplate")
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public List<Map<String, Object>> findForJdbc(String sql, int page, int rows) {
		// 封装分页SQL
		sql = JdbcDao.jeecgCreatePageSql(sql, page, rows);
		return this.jdbcTemplate.queryForList(sql);
	}

	public <T> List<T> findObjForJdbc(String sql, int page, int rows, Class<T> clazz) {
		List<T> rsList = new ArrayList<T>();
		// 封装分页SQL
		sql = JdbcDao.jeecgCreatePageSql(sql, page, rows);
		List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql);

		T po = null;
		for (Map<String, Object> m : mapList) {
			try {
				po = clazz.newInstance();
				MyBeanUtils.copyMap2Bean_Nobig(po, m);
				rsList.add(po);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return rsList;
	}

	public List<Map<String, Object>> findForJdbcParam(String sql, int page, int rows, Object... objs) {
		// 封装分页SQL
		sql = JdbcDao.jeecgCreatePageSql(sql, page, rows);
		return this.jdbcTemplate.queryForList(sql, objs);
	}

	public Long getCountForJdbc(String sql) {
		// return this.jdbcTemplate.queryForLong(sql);
		return null;
	}

	public Long getCountForJdbcParam(String sql, Object[] objs) {
		// return this.jdbcTemplate.queryForLong(sql, objs);
		return null;
	}

	public List<Map<String, Object>> findForJdbc(String sql, Object... objs) {
		return this.jdbcTemplate.queryForList(sql, objs);
	}

	public Integer executeSql(String sql, List<Object> param) {
		return this.jdbcTemplate.update(sql, param);
	}

	public Integer executeSql(String sql, Object... param) {
		return this.jdbcTemplate.update(sql, param);
	}

	public Integer executeSql(String sql, Map<String, Object> param) {
		return this.namedParameterJdbcTemplate.update(sql, param);
	}

	public Object executeSqlReturnKey(final String sql, Map<String, Object> param) {
		Object keyValue = null;
		KeyHolder keyHolder = new GeneratedKeyHolder();
		SqlParameterSource sqlp = new MapSqlParameterSource(param);
		this.namedParameterJdbcTemplate.update(sql, sqlp, keyHolder);
		if (oConvertUtils.isNotEmpty(keyHolder.getKey())) {
			keyValue = keyHolder.getKey().longValue();
		}
		return keyValue;
	}

	public Integer countByJdbc(String sql, Object... param) {
		// return this.jdbcTemplate.queryForInt(sql, param);
		return null;
	}

	public Map<String, Object> findOneForJdbc(String sql, Object... objs) {
		try {
			return this.jdbcTemplate.queryForMap(sql, objs);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public <T> List<T> findHql(String hql, Object... param) {
		Query q = getCurrentSession().createQuery(hql);
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				q.setParameter(i, param[i]);
			}
		}
		return q.list();
	}

	public Integer executeHql(String hql) {
		Query q = getCurrentSession().createQuery(hql);
		return q.executeUpdate();
	}

	public <T> List<T> pageList(DetachedCriteria dc, int firstResult, int maxResult) {
		Criteria criteria = dc.getExecutableCriteria(getCurrentSession());
		criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		criteria.setFirstResult(firstResult);
		criteria.setMaxResults(maxResult);
		return criteria.list();
	}

	/**
	 * 离线查询
	 */
	public <T> List<T> findByDetached(DetachedCriteria dc) {
		return dc.getExecutableCriteria(getCurrentSession()).list();
	}
}
