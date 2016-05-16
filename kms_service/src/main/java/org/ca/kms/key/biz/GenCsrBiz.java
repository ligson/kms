package org.ca.kms.key.biz;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.jce.PKCS10CertificationRequest;
import org.ca.common.utils.X500NameUtils;
import org.ca.kms.common.biz.KeyContainerBiz;
import org.ca.kms.common.model.KeyPairContainer;
import org.ca.kms.key.domain.KeyBufferEntity;
import org.ca.kms.key.dto.GenCsrRequestDto;
import org.ca.kms.key.dto.GenCsrResponseDto;
import org.ca.kms.key.enums.KeyFailEnum;
import org.ca.kms.key.service.KeyBufferService;
import org.ligson.fw.core.common.biz.AbstractBiz;
import org.ligson.fw.core.facade.annotation.Api;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.security.auth.x500.X500Principal;
import java.io.IOException;
import java.util.Date;

/**
 * Created by ligson on 2016/5/16.
 */
@Component("genCsrBiz")
@Api(name = "生成用户csr接口")
public class GenCsrBiz extends AbstractBiz<GenCsrRequestDto, GenCsrResponseDto> {
    @Resource
    private KeyBufferService keyBufferService;
    @Resource
    private KeyContainerBiz keyContainerBiz;

    @Override
    public void before() {

    }

    @Override
    public Boolean paramCheck() {
        return null;
    }

    @Override
    public Boolean bizCheck() {
        KeyBufferEntity entity = keyBufferService.get(requestDto.getKeyId());
        if (entity == null) {
            setFailureResult(KeyFailEnum.E_BIZ_20001);
            return false;
        }
        context.setAttr("entity", entity);
        KeyPairContainer keyPairContainer = keyContainerBiz.getKeyPair(entity.getId());
        if (keyPairContainer == null) {
            setFailureResult(KeyFailEnum.E_BIZ_20001);
            return false;
        }
        context.setAttr("keyPairContainer", keyPairContainer);
        return true;
    }

    @Override
    public Boolean txnPreProcessing() {
        KeyBufferEntity keyBufferEntity = (KeyBufferEntity) context.getAttr("entity");
        KeyPairContainer keyPairContainer = (KeyPairContainer) context.getAttr("keyPairContainer");
        //生成csr
        X500Name x500Name = X500NameUtils.subjectToX500Name(requestDto.getSubjectDn());
        X500Principal principal = null;
        try {
            principal = new X500Principal(x500Name.getEncoded());
        } catch (IOException e) {
            e.printStackTrace();
            setFailureResult(KeyFailEnum.E_BIZ_20001);
            return false;
        }

        PKCS10CertificationRequest request = null;
        try {
            request = new PKCS10CertificationRequest("SHA1withRSA", principal, keyPairContainer.getPublicKey(), null, keyPairContainer.getPrivateKey());
        } catch (Exception e) {
            e.printStackTrace();
            setFailureResult(KeyFailEnum.E_BIZ_20001);
            return false;
        }
        String code = "-----BEGIN CERTIFICATE REQUEST-----\n";
        String csr = Base64.encodeBase64String(request.getEncoded());
        code += csr;
        code += "\n-----END CERTIFICATE REQUEST-----\n";

        keyBufferEntity.setUseTime(new Date());
        keyBufferService.update(keyBufferEntity);
        responseDto.setCsr(code);
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
