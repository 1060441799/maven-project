package com.newframe.web.service;

import com.newframe.core.pojo.pojoimpl.impl.Territory;
import com.newframe.core.pojo.pojoimpl.impl.User;
import com.newframe.core.service.CommonService;
import com.newframe.web.model.SignupUserFacade;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService extends CommonService {
    public User checkUserExits(User user);

    public String getUserRole(User user);

    public void pwdInit(User user, String newPwd);

    public int getUsersOfThisRole(String id);

    public User checkUserByName(User user);

    User saveUser(SignupUserFacade user,HttpServletRequest req) throws Exception;

    List<Territory> getTerritoriesByParentId(String id);
}
