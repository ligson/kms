package org.ca.kms.user.dao.impl;

import org.ca.kms.user.dao.UserDao;
import org.ca.kms.user.domain.UserEntity;
import org.ligson.fw.core.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by ligson on 2016/5/4.
 */
@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<UserEntity> implements UserDao {
}
