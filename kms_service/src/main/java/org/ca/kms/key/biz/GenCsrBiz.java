package org.ca.kms.key.biz;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.jce.PKCS10CertificationRequest;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.pkcs.PKCS10CertificationRequestBuilder;
import org.ca.common.utils.X500NameUtils;
import org.ca.ext.security.x509.AlgorithmId;
import org.ca.kms.common.biz.KeyContainerBiz;
import org.ca.kms.common.init.BootStrap;
import org.ca.kms.common.main.Startup;
import org.ca.kms.common.model.KeyPairContainer;
import org.ca.kms.key.domain.KeyBufferEntity;
import org.ca.kms.key.dto.GenCsrRequestDto;
import org.ca.kms.key.dto.GenCsrResponseDto;
import org.ca.kms.key.enums.KeyFailEnum;
import org.ca.kms.key.enums.KeyStatus;
import org.ca.kms.key.service.KeyBufferService;
import org.ligson.fw.core.common.biz.AbstractBiz;
import org.ligson.fw.core.facade.annotation.Api;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.security.auth.x500.X500Principal;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.*;
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

    public String genCsr(X500Name subject, PublicKey publicKey, PrivateKey privateKey) {
        if (publicKey.getAlgorithm().equals("RSA")) {
            X500Principal principal = null;
            try {
                principal = new X500Principal(subject.getEncoded());
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            try {
                PKCS10CertificationRequest request = new PKCS10CertificationRequest("SHA1withRSA", principal, publicKey, null, privateKey);
                return Base64.encodeBase64String(request.getEncoded());
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        SubjectPublicKeyInfo publicKeyInfo = null;
        try {
            publicKeyInfo = new SubjectPublicKeyInfo(new AlgorithmIdentifier(AlgorithmId.get(publicKey.getAlgorithm()).getOID().toString()), publicKey.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PKCS10CertificationRequestBuilder builder = new PKCS10CertificationRequestBuilder(subject, publicKeyInfo);
        ContentSigner contentSigner = new ContentSigner() {
            @Override
            public AlgorithmIdentifier getAlgorithmIdentifier() {
                try {
                    return new AlgorithmIdentifier(AlgorithmId.get(publicKey.getAlgorithm()).getOID().toString());
                } catch (Exception e) {
                    return null;
                }
            }

            @Override
            public OutputStream getOutputStream() {
                return bos;
            }

            @Override
            public byte[] getSignature() {
                String alg = publicKey.getAlgorithm();
                String signAlg = "SHA1withRSA";
                if (alg.equals("SM2")) {
                    signAlg = "SM3withSM2";
                }
                try {
                    Signature signature = Signature.getInstance(signAlg);
                    signature.initSign(privateKey);
                    signature.update(bos.toByteArray());
                    return signature.sign();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return new byte[0];
            }
        };
        try {
            return Base64.encodeBase64String(builder.build(contentSigner).getEncoded());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Boolean txnPreProcessing() {
        KeyBufferEntity keyBufferEntity = (KeyBufferEntity) context.getAttr("entity");
        KeyPairContainer keyPairContainer = (KeyPairContainer) context.getAttr("keyPairContainer");
        //生成csr
        X500Name x500Name = X500NameUtils.subjectToX500Name(requestDto.getSubjectDn());

        String csr = genCsr(x500Name, keyPairContainer.getPublicKey(), keyPairContainer.getPrivateKey());
        if (csr == null) {
            setFailureResult(KeyFailEnum.E_BIZ_21002);
            return false;
        }
        String code = "-----BEGIN CERTIFICATE REQUEST-----\n";
        code += csr;
        code += "\n-----END CERTIFICATE REQUEST-----\n";

        keyBufferEntity.setKeyStatus(KeyStatus.INUSE.getCode());
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
