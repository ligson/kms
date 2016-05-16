package org.ca.kms.key.biz;

import org.apache.commons.codec.binary.Base64;
import org.ca.kms.common.biz.KeyContainerBiz;
import org.ca.kms.common.model.KeyPairContainer;
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

    @Resource
    private KeyContainerBiz keyContainerBiz;

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
        List<KeyPairContainer> containers = new ArrayList<>();
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
            KeyPairContainer container = new KeyPairContainer();
            container.setPublicKey(keyPair.getPublic());
            container.setPrivateKey(keyPair.getPrivate());
            container.setKeySize(requestDto.getKeySize());
            container.setType(KeyType.getByCode(requestDto.getKeyType()).getMsg());
            containers.add(container);
        }
        context.setAttr("entities", entities);
        context.setAttr("containers", containers);
        return true;
    }

    @Override
    public Boolean persistence() {
        List<KeyBufferEntity> entities = (List<KeyBufferEntity>) context.getAttr("entities");
        List<KeyPairContainer> containers = (List<KeyPairContainer>) context.getAttr("containers");
        for (int i = 0; i < entities.size(); i++) {
            KeyBufferEntity entity = entities.get(i);
            KeyPairContainer container = containers.get(i);
            keyBufferService.add(entity);
            try {
                keyContainerBiz.storeKey(entity.getId(), container.getPublicKey(), container.getPrivateKey());
            } catch (Exception e) {
                e.printStackTrace();
                keyBufferService.delete(entity);
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
