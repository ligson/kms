package org.ca.kms.key.biz;

import org.apache.commons.codec.binary.Base64;
import org.ca.common.utils.KeyPairUtils;
import org.ca.kms.key.domain.KeyBufferEntity;
import org.ca.kms.key.dto.QueryKeyPairRequestDto;
import org.ca.kms.key.dto.QueryKeyPairResponseDto;
import org.ca.kms.key.enums.KeyFailEnum;
import org.ca.kms.key.enums.KeyType;
import org.ca.kms.key.service.KeyBufferService;
import org.ligson.fw.core.common.biz.AbstractBiz;
import org.ligson.fw.core.facade.annotation.Api;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Created by ligson on 2016/5/9.
 */
@Component("queryKeyPairBiz")
@Api(name = "密钥对对象查询接口")
public class QueryKeyPairBiz extends AbstractBiz<QueryKeyPairRequestDto, QueryKeyPairResponseDto> {
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
        KeyBufferEntity entity = keyBufferService.findBy("id", requestDto.getKeyId());
        if (entity == null) {
            setFailureResult(KeyFailEnum.E_BIZ_20001);
            return false;
        }
        context.setAttr("entity", entity);
        return true;
    }

    @Override
    public Boolean txnPreProcessing() {
        KeyBufferEntity entity = (KeyBufferEntity) context.getAttr("entity");
        String pubKey = entity.getPublicKey();
        String priKey = entity.getPrivateKey();

        if (entity.getKeyType() == KeyType.SM2.getCode()) {
            //TODO 未实现
        } else if (entity.getKeyType() == KeyType.RSA.getCode()) {
            PrivateKey privateKey = KeyPairUtils.decodePrivateKey(priKey);
            PublicKey publicKey = KeyPairUtils.decodePublicKey(pubKey);
            responseDto.setPrivateKey(privateKey);
            responseDto.setPublicKey(publicKey);
            responseDto.setSuccess(true);
            setSuccessResult();
        } else {
            setFailureResult(KeyFailEnum.E_PARAM_11001);
            return false;
        }
        return null;
    }

    @Override
    public Boolean persistence() {
        return null;
    }

    @Override
    public void after() {

    }
}
