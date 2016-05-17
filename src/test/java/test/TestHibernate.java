package test;

import com.newframe.core.pojo.pojoimpl.impl.Role;
import com.newframe.core.pojo.pojoimpl.impl.User;
import com.newframe.core.util.UUIDGenerator;
import org.hibernate.*;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.jdbc.Work;
import org.hibernate.mapping.Map;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Created by xm on 2016/5/13.
 */
public class TestHibernate {

    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    @Before
    public void init() {
        Configuration configuration = new Configuration().configure();
        StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder();
        ServiceRegistry serviceRegistry = ssrb.applySettings(configuration.getProperties()).build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
    }

    @After
    public void resolveResources() {
        transaction.commit();
        session.close();
        sessionFactory.close();
    }

    @Test
    public void testGet() {
        User user = (User) session.get(User.class, "4028d881436d514601436d5215ac0043");
        System.out.println("user : " + user.getEmail());
    }

    @Test
    public void testSave() {
        Role role = new Role();
        role.setId(UUIDGenerator.generate());
        role.setRoleName("Tester");
        role.setRoleCode("123");
        role.setCreatedBy("arrym");
        role.setCreatedOn(new Date());
        role.setModifiedBy("arrym");
        role.setModifiedOn(new Date());
        role.setStatus("1");

        Query query = session.createQuery("from Role");
        role.setOrderNum(query.list().size() + 1);
        session.save(role);
        session.flush();
    }

    @Test
    /**
     * 1、创建Configuration对象，Configuration对象读取hibernate.cfg.xml配置文件。
     * 2、使用Configuration对象来构建SessionFactory对象，SessionFactory对象读取orm映射文件。
     * 3、使用SessionFactory对象来构建Session对象。
     * 4、使用Session对象来开启事务。hibernate默认非自动提交事务（就是说必须开启事务并提交事务才能对数据进行持久化），而jdbc默认自动提交事务
     * 5、使用Session对象来进行数据库数据操作，比如update、save、saveOrUpdate、persist、delete、get、createQuery。
     * 6、使用Session对象来commit或者rollBack事务。
     * 7、关闭Session对象。
     * 8、关闭SessionFactory对象。
     */
    public void testSession() {
        Role role = new Role();
        role.setId(UUIDGenerator.generate());
        role.setRoleName("Tester");
        role.setRoleCode("123");
        role.setCreatedBy("arrym");
        role.setCreatedOn(new Date());
        role.setModifiedBy("arrym");
        role.setModifiedOn(new Date());
        role.setStatus("1");

        Query query = session.createQuery("from Role");
        role.setOrderNum(query.list().size() + 1);
        /**
         * 通过doWork方法可以强制将hibernate设为自动提交事务，最后还必须得flush()一下。
         */
        session.doWork(new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                connection.setAutoCommit(true);
            }
        });
        session.save(role);
        session.flush();
    }

    @Test
    public void testOpenSession() {
        //获得配置对象
        Configuration configuration = new Configuration().configure();
        StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder();
        //获得服务注册对象
        ServiceRegistry serviceRegistry = ssrb.applySettings(configuration.getProperties()).build();
        //获得sessionFactory对象
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        //获得session对象
        session = sessionFactory.openSession();
        Session session1 = sessionFactory.openSession();
        if (session != null) {
            System.out.println("session创建成功！");
        }
        if (session1 != session) {
            System.out.println("session不是同一个！");
        }
    }

    /**
     * openSession与getCurrentSession的区别：
     * 1、getCurrentSession在事务提交或回滚会自动关闭资源、释放数据库连接对象(session.close())，而openSession不会，会造成数据库连接池溢出。
     * 2、getCurrentSession获取的Session对象是单利的，而openSession每次调用都会返回新的Session对象。
     */
    @Test
    public void testGetCurrentSession() {
        //获得配置对象
        Configuration configuration = new Configuration().configure();
        StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder();
        //获得服务注册对象
        ServiceRegistry serviceRegistry = ssrb.applySettings(configuration.getProperties()).build();
        //获得sessionFactory对象
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        //获得session对象
        session = sessionFactory.getCurrentSession();
        Session session1 = sessionFactory.getCurrentSession();
        if (session != null) {
            System.out.println("session创建成功！");
        }
        if (session1 == session) {
            System.out.println("session是同一个！");
        }
    }

    @Test
    /**
     * hibernate数据类型：
     * 1、基本类型：int、char、long、date、timestap、
     * 2、对象类型：java.sql.Clob(Text)、java.lang.String(Text)、java.sql.Blob(Blob)
     * 3、组件属性：实体类中的某个属性是用户自定义类的对象。
     */
    public void testDataType() {

    }

    @Test
    /**
     * get和load的区别：
     * 1、get不考虑缓存，会在调用后立即向数据库发出sql语句，并返回持久化对象（真正的持久化类对象）。
     * load方法会在调用后返回一个代理对象（并非真正的持久化类对象），代理对象只保留了持久化对象的id，
     * 直到当我们使用该持久化对象的非主键属性的时候hibernate才会真正向数据库发出sql语句。
     * 2、当查询的数据不存在于数据库中时，get返回null，load会抛出org.hibernate.ObjectNotFoundException。
     *
     */
    public void testLoad() {

    }

    /**
     * hibernate基本开发步骤：
     * 1、编写hibernate.cfg.xml
     * 2、编写实体类
     * 3、生成实体类对象的映射文件并添加到和hibernate.cfg.xml配置文件中。
     * 4、调用hibernate api进行数据库操作。
     * 什么是session？
     * session对象相当于jdbc的connection对，个人觉得他是connection对象、statement对象、resultset对象的结合。
     */

    /**
     * HQL查询（使用Query对象）：
     * 1、from子句
     * 2、select子句
     * 3、where子句
     * 4、order by子句
     *
     * HQL定义：
     * 1、面向对象的查询语言。HQL面向映射配置的持久化类的属性，sql面向数据表的字段。
     * 2、Hibernate Query language.
     *
     * HQL语句形式：
     * 1、select  from  where  group by  having  order by
     * 2、最简单的HQL是只有from子句，其它子句可以省略。
     * 3、HQL对java类与属性大小写敏感，与sql不一样。
     * 4、HQL对关键字大小写不区分。
     */
    /**
     * Query接口：
     * 1、定义执行查询的方法。
     * 2、支持方法链。
     * 3、Session的createQuery方法创建Query对象。
     * <p/>
     * 常用方法：
     * 1、list()
     * 2、uniqueResult()
     * 3、executeUpdate()
     */
    @Test
    public void testHQL() {
        Query query = session.createQuery("from Role");
        List<Role> roles = query.list();
        System.out.println("roles.size=" + roles.size());
    }

    /**
     * HQL使用别名：
     * 1、hibernate默认懒加载，不加载对象关联的对象属性的值，只有要用到该对象属性时hibernate才会去加载该对象。
     * 2、from Role role, User user, ...
     */
    @Test
    public void testHQL2() {
        Query query = session.createQuery("from Role role where role.id = :id");
//        Query  query = session.createQuery("from com.newframe.core.pojo.pojoimpl.impl.Role role where role.id = :id");
        query.setParameter("id", "402881e44648134a014648163a6d0001");
        Role role = (Role) query.uniqueResult();
        System.out.println("role's name is " + role.getRoleName());
    }

    /**
     * HQL查询结果（使用select子句指定查询属性）：
     * 1、返回List<Object[]>查询结果。当查询一个属性时返回List<Object>查询结果。
     * 2、返回List<List>查询结果。
     * 3、返回List<Map>查询结果。
     * 4、返回自定义类型，先为返回实体类定义指定构造器，比如Role(String roleName, String roleCode)。
     * 5、使用distinct关键字去除重复结果。
     */
    @Test
    public void testHQLDistinct() {
        String hql = "select distinct role.roleName from Role role";
        Query query = session.createQuery(hql);
        List<Object> list = query.list();
        for (Object role : list) {
            System.out.println("role's name is " + role);
        }
    }

    @Test
    public void testHQLResult4() {
        //使用指定构造器
        String hql = "select new Role(role.roleName,role.roleCode) from Role role";
        Query query = session.createQuery(hql);
        List<Role> list = query.list();
        for (Role role : list) {
            System.out.println("role's name is " + role.getRoleName());
            System.out.println("role's code is " + role.getRoleCode());
        }
    }

    @Test
    public void testHQLResult3() {
        //使用new map()
        //        使用序号来获取结果集中的属性信息
        //        String hql = "select new map(role.roleName,role.roleCode) from Role role";
        //        为属性设置别名
        String hql = "select new map(role.roleName as roleName,role.roleCode as roleCode) from Role role";
        Query query = session.createQuery(hql);
        List<java.util.Map> list = query.list();
        //        使用序号来获取结果集中的属性信息
        //        for (java.util.Map map : list) {
        //            System.out.println("role's name is " + map.get("0"));
        //            System.out.println("role's code is " + map.get("1"));
        //        }
        //        使用属性别名来获取结果集中的属性信息
        for (java.util.Map map : list) {
            System.out.println("role's name is " + map.get("roleName"));
            System.out.println("role's code is " + map.get("roleCode"));
        }
    }

    @Test
    public void testHQLResult() {
        //使用默认，返回List<Object[]>。当查询一个属性时返回List<Object>
        String hql = "select role.roleName,role.roleCode from Role role";
        Query query = session.createQuery(hql);
        List<Object[]> list = query.list();
        for (Object[] objects : list) {
            System.out.println("role's name is " + objects[0]);
            System.out.println("role's code is " + objects[1]);
        }
    }

    @Test
    public void testHQLResult2() {
        //使用new list()，返回List<List>
        String hql = "select new list(role.roleName,role.roleCode) from Role role";
        Query query = session.createQuery(hql);
        List<List> list = query.list();
        for (List l : list) {
            System.out.println("role's name is " + l.get(0));
            System.out.println("role's code is " + l.get(1));
        }
    }

    /**
     * HQL比较运算：
     * 1、=、<>、<、>
     */
    @Test
    public void testWhere1() {
        String hql = "from Role role where role.orderNum > 4";
        Query query = session.createQuery(hql);
        List<Role> roles = query.list();
        System.out.println("Role's size=" + roles.size());
    }

    /**
     * 在HQL中is null和 = null一样，因为hibernate会将 =null 转换为is null
     */
    @Test
    public void testWhere2() {
        String hql = "from Role role where role.orderNum is null";
        Query query = session.createQuery(hql);
        List<Role> roles = query.list();
        System.out.println("Role's size=" + roles.size());
    }

    @Test
    public void testWhere3() {
        String hql = "from Role role where role.orderNum = null";
        Query query = session.createQuery(hql);
        List<Role> roles = query.list();
        System.out.println("Role's size=" + roles.size());
    }

    /**
     * 在HQL中is not null和 != null一样，因为hibernate会将 !=null 转换为is not null
     */
    @Test
    public void testWhere4() {
        String hql = "from Role role where role.orderNum != null";
        Query query = session.createQuery(hql);
        List<Role> roles = query.list();
        System.out.println("Role's size=" + roles.size());
    }

    @Test
    public void testWhere5() {
        String hql = "from Role role where role.orderNum is not null";
        Query query = session.createQuery(hql);
        List<Role> roles = query.list();
        System.out.println("Role's size=" + roles.size());
    }

    @Test
    public void testWhere6() {
        String hql = "from Role role where role.orderNum in (3,6)";
        Query query = session.createQuery(hql);
        List<Role> roles = query.list();
        System.out.println("Role's size=" + roles.size());
    }

    @Test
    public void testWhere7() {
        String hql = "from Role role where role.orderNum not in (3,6)";
        Query query = session.createQuery(hql);
        List<Role> roles = query.list();
        System.out.println("Role's size=" + roles.size());
    }

    @Test
    public void testWhere8() {
        String hql = "from Role role where role.orderNum between 3 and 6";
        Query query = session.createQuery(hql);
        List<Role> roles = query.list();
        System.out.println("Role's size=" + roles.size());
    }

    @Test
    public void testWhere9() {
        String hql = "from Role role where role.roleName like '%est%'";
        Query query = session.createQuery(hql);
        List<Role> roles = query.list();
        System.out.println("Role's size=" + roles.size());
    }

    @Test
    public void testWhere10() {
        String hql = "from Role role where role.roleName like '%est%' order by role.orderNum";
        Query query = session.createQuery(hql);
        List<Role> roles = query.list();
        for(Role role : roles){
            System.out.println("Role's role code=" + role.getOrderNum());
        }
    }
}
