package com.newframe.web.service.impl;

import com.newframe.core.pojo.pojoimpl.impl.Function;
import com.newframe.web.dao.FunctionDao;
import com.newframe.web.model.FunctionFacade;
import com.newframe.web.service.FunctionService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by xm on 2016/4/2.
 */
public class FunctionServiceImpl implements FunctionService {

    @Autowired
    private FunctionDao functionDao;

    public FunctionFacade findById(String id) {
        Function function = functionDao.findById(id);
        if (function == null) {
            return null;
        }

        FunctionFacade facade = new FunctionFacade(function);
        return facade;
    }
}
