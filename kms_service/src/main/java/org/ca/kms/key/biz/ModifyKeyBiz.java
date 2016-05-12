package org.ca.kms.key.biz;

import org.ca.kms.key.domain.KeyBufferEntity;
import org.ca.kms.key.dto.ModifyKeyRequestDto;
import org.ca.kms.key.dto.ModifyKeyResponseDto;
import org.ca.kms.key.enums.KeyFailEnum;
import org.ca.kms.key.service.KeyBufferService;
import org.ligson.fw.core.common.biz.AbstractBiz;
import org.ligson.fw.core.common.utils.BeanCopy;
import org.ligson.fw.core.facade.annotation.Api;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by ligson on 2016/5/11.
 */
@Component("modifyKeyBiz")
@Api(name = "修改密钥对接口")
public class ModifyKeyBiz extends AbstractBiz<ModifyKeyRequestDto, ModifyKeyResponseDto> {
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
        KeyBufferEntity keyBufferEntity = keyBufferService.findBy("id", requestDto.getId());
        if (keyBufferEntity == null) {
            setFailureResult(KeyFailEnum.E_BIZ_20001);
            return false;
        }
        context.setAttr("entity", keyBufferEntity);
        return true;
    }

    @Override
    public Boolean txnPreProcessing() {
        KeyBufferEntity keyBufferEntity = (KeyBufferEntity) context.getAttr("entity");
        BeanCopy.copyPropertiesIgnoreNull(requestDto, keyBufferEntity);
        return true;
    }

    @Override
    public Boolean persistence() {
        KeyBufferEntity keyBufferEntity = (KeyBufferEntity) context.getAttr("entity");
        keyBufferService.update(keyBufferEntity);
        responseDto.setSuccess(true);
        setSuccessResult();
        return true;
    }

    @Override
    public void after() {

    }
}
