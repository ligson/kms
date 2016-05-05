package org.ca.kms.key.service.impl;

import org.ca.kms.key.dao.KeyBufferDao;
import org.ca.kms.key.domain.KeyBufferEntity;
import org.ca.kms.key.service.KeyBufferService;
import org.ligson.fw.core.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * Created by ligson on 2016/5/4.
 */
@Component("keyBufferService")
public class KeyBufferServiceImpl extends BaseServiceImpl<KeyBufferEntity> implements KeyBufferService {

    @Resource
    private KeyBufferDao keyBufferDao;

    @PostConstruct
    @Override
    public void initBaseDao() {
        baseDao = keyBufferDao;
    }
}
