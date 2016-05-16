package test;

import com.newframe.core.pojo.pojoimpl.impl.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.Test;

/**
 * Created by xm on 2016/5/13.
 */
public class TestHibernate {

    @Test
    public void testConfiguration(){
        Configuration configuration = new Configuration().configure();
        StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder();
        ServiceRegistry serviceRegistry = ssrb.applySettings(configuration.getProperties()).build();
        SessionFactory sf = configuration.buildSessionFactory(serviceRegistry);
        Session session = sf.openSession();
        User user  = (User)session.get(User.class,"4028d881436d514601436d5215ac0043");
        System.out.println("user : " + user.getEmail());
    }
}
