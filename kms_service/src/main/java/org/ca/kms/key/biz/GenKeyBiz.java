package org.ca.kms.key.biz;

import org.apache.commons.codec.binary.Base64;
import org.ca.kms.key.domain.KeyBufferEntity;
import org.ca.kms.key.dto.GenKeyRequestDto;
import org.ca.kms.key.dto.GenKeyResponseDto;
import org.ca.kms.key.enums.KeyFailEnum;
import org.ca.kms.key.enums.KeyStatus;
import org.ca.kms.key.enums.KeyType;
import org.ca.kms.key.service.KeyBufferService;
import org.ligson.fw.core.common.biz.AbstractBiz;
import org.ligson.fw.core.facade.annotation.Api;
import org.ligson.fw.core.facade.enums.FailureCodeEnum;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ligson on 2016/5/5.
 */
@Component("genKeyBiz")
@Api(name = "生成密钥对接口")
public class GenKeyBiz extends AbstractBiz<GenKeyRequestDto, GenKeyResponseDto> {

    @Resource
    private KeyBufferService keyBufferService;

    @Override
    public void before() {

    }

    @Override
    public Boolean paramCheck() {
        if (requestDto.getKeyType() == KeyType.RSA.getCode()) {
            if (requestDto.getKeySize() != 512 && requestDto.getKeySize() != 1024 && requestDto.getKeySize() != 2048) {
                setFailureResult(KeyFailEnum.E_PARAM_11002);
                return false;
            }
        } else if (requestDto.getKeyType() == KeyType.SM2.getCode()) {
            if (requestDto.getKeySize() != 512 && requestDto.getKeySize() != 256) {
                setFailureResult(KeyFailEnum.E_PARAM_11002);
                return false;
            }
        }
        return true;
    }

    @Override
    public Boolean bizCheck() {
        KeyType keyType = KeyType.getByCode(requestDto.getKeyType());
        KeyPairGenerator generator;
        try {
            generator = KeyPairGenerator.getInstance(keyType.name());
            generator.initialize(requestDto.getKeySize());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            setFailureResult(KeyFailEnum.E_PARAM_11001);
            return false;
        }

        context.setAttr("generator", generator);
        return true;
    }

    @Override
    public Boolean txnPreProcessing() {
        KeyPairGenerator generator = (KeyPairGenerator) context.getAttr("generator");
        List<KeyBufferEntity> entities = new ArrayList<>();
        for (int i = 0; i < requestDto.getCount(); i++) {
            KeyPair keyPair = generator.generateKeyPair();
            KeyBufferEntity entity = new KeyBufferEntity();
            entity.setUserId(requestDto.getUserId());
            entity.setCreateTime(new Date());
            entity.setKeySize(requestDto.getKeySize());
            entity.setKeyType(requestDto.getKeyType());
            entity.setKeyStatus(KeyStatus.READY.getCode());
            entity.setPublicKey(Base64.encodeBase64String(keyPair.getPublic().getEncoded()));
            entity.setPrivateKey(Base64.encodeBase64String(keyPair.getPrivate().getEncoded()));
            entities.add(entity);
        }
        context.setAttr("entities", entities);
        return true;
    }

    @Override
    public Boolean persistence() {
        List<KeyBufferEntity> entities = (List<KeyBufferEntity>) context.getAttr("entities");
        for (KeyBufferEntity entity : entities) {
            try {
                keyBufferService.add(entity);
            } catch (Exception e) {
                e.printStackTrace();
                setFailureResult(FailureCodeEnum.E_PERSIST_40001);
                return false;
            }
        }
        responseDto.setSuccess(true);
        setSuccessResult();
        return true;
    }

    @Override
    public void after() {

    }
}
