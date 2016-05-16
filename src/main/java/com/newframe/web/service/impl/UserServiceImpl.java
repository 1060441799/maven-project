package com.newframe.web.service.impl;

import com.newframe.core.pojo.pojoimpl.impl.Depart;
import com.newframe.core.pojo.pojoimpl.impl.RelRoleUser;
import com.newframe.core.pojo.pojoimpl.impl.Territory;
import com.newframe.core.pojo.pojoimpl.impl.User;
import com.newframe.core.service.impl.CommonServiceImpl;
import com.newframe.core.util.BrowserUtils;
import com.newframe.core.util.PasswordUtil;
import com.newframe.core.util.UUIDGenerator;
import com.newframe.web.model.SignupUserFacade;
import com.newframe.web.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service("userService")
@Transactional
public class UserServiceImpl extends CommonServiceImpl implements UserService {

    public User checkUserExits(User user) {
        return this.commonDao.getUserByUserIdAndUserNameExits(user);
    }

    public String getUserRole(User user) {
        return this.commonDao.getUserRole(user);
    }

    public void pwdInit(User user, String newPwd) {
        this.commonDao.pwdInit(user, newPwd);
    }

    @Override
    public List<Territory> getTerritoriesByParentId(String id) {
        Criteria criteria = getCurrentSession().createCriteria(Territory.class);
        criteria.add(Restrictions.eq("parentTerritory.id", id));
        return criteria.list();
    }

    public int getUsersOfThisRole(String id) {
        Criteria criteria = getCurrentSession().createCriteria(RelRoleUser.class);
        criteria.add(Restrictions.eq("TSRole.id", id));
        int allCounts = ((Long) criteria.setProjection(
                Projections.rowCount()).uniqueResult()).intValue();
        return allCounts;
    }

    public User checkUserByName(User user) {
        return this.commonDao.getUserByUserName(user);
    }

    @Override
    public User saveUser(SignupUserFacade user, HttpServletRequest req) throws Exception {
        String departId = user.getDepartmentId();
        String password = PasswordUtil.encrypt(user.getUserName(), user.getPassword(), PasswordUtil.getStaticSalt());
        User persist = new User();

        persist.setPassword(password);
        persist.setId(UUIDGenerator.generate());
        persist.setCreatedOn(new Date());
        persist.setActivitiSync((short) 1);
        persist.setStatus("0");
        persist.setCreatedBy(user.getUserName());
        persist.setRealName(user.getRealName());
        persist.setUserName(user.getUserName());
        persist.setSignatureFile(user.getSignatureFile());
        persist.setOfficePhone(user.getOfficePhone());
        persist.setMobilePhone(user.getMobilePhone());
        persist.setEmail(user.getUserEmail());
        persist.setType("1");
        persist.setBrowser(BrowserUtils.checkBrowse(req));
        persist.setModifiedOn(new Date());
        persist.setCity(user.getCity());
        persist.setProvince(user.getProvince());
        persist.setModifiedBy(user.getUserName());

        if (StringUtils.isEmpty(user.getSignatureFile())) {
            user.setSignatureFile("pages/newframework/static/img/a0.jpg");
        }

        Criteria criteria = getCurrentSession().createCriteria(Depart.class);
        criteria.add(Restrictions.eq("id", departId));
        List<Depart> departs = criteria.list();

//        String query = "from Depart d where d.id = :departId";
//        Query queryObject = getCurrentSession().createQuery(query);
//        queryObject.setParameter("id", departId);
//        List<Depart> departs = queryObject.list();
        if (departs != null && departs.size() > 0) {
            persist.setDepartId(departs.get(0).getId());
            String query = "select max(u.orderNum) from User u";
            Query queryObject = getCurrentSession().createQuery(query);
            Integer fromDB = (Integer) queryObject.uniqueResult();
            if (fromDB != null) {
                persist.setOrderNum(fromDB + 1);
            } else {
                persist.setOrderNum(1);
            }
            this.save(persist);
            return persist;
        } else {
            throw new Exception("No this department");
        }
    }
}
