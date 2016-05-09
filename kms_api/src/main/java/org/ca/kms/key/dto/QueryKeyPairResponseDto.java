package org.ca.kms.key.dto;

import org.ligson.fw.core.facade.base.dto.BaseResponseDto;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Created by ligson on 2016/5/9.
 */
public class QueryKeyPairResponseDto extends BaseResponseDto {
    private PublicKey publicKey;
    private PrivateKey privateKey;

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    @Override
    public String toString() {
        return "QueryKeyPairResponseDto{" +
                "publicKey=" + publicKey +
                ", privateKey=" + privateKey +
                '}';
    }
}
