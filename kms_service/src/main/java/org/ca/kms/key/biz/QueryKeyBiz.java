package org.ca.kms.key.biz;

import org.ca.kms.key.domain.KeyBufferEntity;
import org.ca.kms.key.dto.KeyQueryRequestDto;
import org.ca.kms.key.dto.KeyQueryResponseDto;
import org.ca.kms.key.service.KeyBufferService;
import org.ca.kms.key.vo.Key;
import org.ligson.fw.core.common.biz.AbstractBiz;
import org.ligson.fw.core.entity.Pagination;
import org.ligson.fw.core.facade.annotation.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ligson on 2016/5/5.
 */
@Component("queryKeyBiz")
@Api(name = "密钥对查询接口")
public class QueryKeyBiz extends AbstractBiz<KeyQueryRequestDto, KeyQueryResponseDto> {
    @Resource
    private KeyBufferService keyBufferService;

    @Override
    public void before() {

    }

    @Override
    public Boolean paramCheck() {
        return null;
    }

    @Override
    public Boolean bizCheck() {
        return null;
    }

    @Override
    public Boolean txnPreProcessing() {
        KeyBufferEntity entity = new KeyBufferEntity();
        BeanUtils.copyProperties(requestDto, entity);
        entity.setPageAble(requestDto.getPageAble());
        entity.setOffset((requestDto.getPageNum() - 1) * requestDto.getPageSize());
        entity.setMax(requestDto.getPageSize());

        Pagination<KeyBufferEntity> page = keyBufferService.findAllByEqAnd(entity);
        responseDto.setSuccess(true);
        responseDto.setPageSize(entity.getMax());
        responseDto.setTotalCount(page.getTotalCount());
        if (entity.getPageAble()) {
            List<Key> keyList = new ArrayList<>();
            for (KeyBufferEntity bufferEntity : page.getDatas()) {
                Key key = new Key();
                BeanUtils.copyProperties(bufferEntity, key);
                keyList.add(key);
            }
            responseDto.setKeyList(keyList);
        } else {
            if (!CollectionUtils.isEmpty(page.getDatas())) {
                Key key = new Key();
                BeanUtils.copyProperties(page.getDatas().get(0), key);
                responseDto.setKey(key);
            }
        }
        setSuccessResult();
        return true;
    }

    @Override
    public Boolean persistence() {
        return null;
    }

    @Override
    public void after() {

    }
}
