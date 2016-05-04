package org.ca.kms.key.dao.impl;

import org.ca.kms.key.dao.KeyBufferDao;
import org.ca.kms.key.domain.KeyBufferEntity;
import org.ligson.fw.core.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by ligson on 2016/5/4.
 */
@Repository("keyBufferDao")
public class KeyBufferDaoImpl extends BaseDaoImpl<KeyBufferEntity> implements KeyBufferDao {
}
