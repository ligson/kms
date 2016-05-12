package org.ca.kms.key.dto;

import org.ligson.fw.core.facade.annotation.Param;
import org.ligson.fw.core.facade.base.dto.BaseRequestDto;

import java.math.BigInteger;

/**
 * Created by ligson on 2016/5/9.
 */
public class QueryKeyPairRequestDto extends BaseRequestDto {
    @Param(name = "keyId", required = true)
    private String keyId;

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }
}
