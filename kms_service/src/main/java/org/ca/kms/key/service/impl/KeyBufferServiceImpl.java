package org.ca.kms.key.service.impl;

import org.ca.kms.key.dao.KeyBufferDao;
import org.ca.kms.key.domain.KeyBufferEntity;
import org.ca.kms.key.service.KeyBufferService;
import org.ligson.fw.core.common.idgenerator.Comment;
import org.ligson.fw.core.service.impl.BaseServiceImpl;

import javax.annotation.Resource;

/**
 * Created by ligson on 2016/5/4.
 */
@Comment("keyBufferService")
public class KeyBufferServiceImpl extends BaseServiceImpl<KeyBufferEntity> implements KeyBufferService {

    @Resource
    private KeyBufferDao keyBufferDao;

    @Override
    public void initBaseDao() {
        baseDao = keyBufferDao;
    }
}
