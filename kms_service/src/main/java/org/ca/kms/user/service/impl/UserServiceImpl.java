package org.ca.kms.user.service.impl;

import org.ca.kms.user.dao.UserDao;
import org.ca.kms.user.domain.UserEntity;
import org.ca.kms.user.service.UserService;
import org.ligson.fw.core.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by ligson on 2016/5/4.
 */
@Component("userService")
public class UserServiceImpl extends BaseServiceImpl<UserEntity> implements UserService {
    @Resource
    private UserDao userDao;

    @Override
    public void initBaseDao() {
        baseDao = userDao;
    }
}
