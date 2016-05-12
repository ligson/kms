package org.ca.kms.key.dto;

import org.ligson.fw.core.facade.annotation.Param;
import org.ligson.fw.core.facade.base.dto.BaseRequestDto;

import java.math.BigInteger;

/**
 * Created by ligson on 2016/5/5.
 */
public class GenKeyRequestDto extends BaseRequestDto {
    @Param(name = "用户id", required = true)
    private String userId;
    @Param(name = "密钥对类型", required = true)
    private Integer keyType;
    @Param(name = "密钥长度", required = true)
    private Integer keySize;
    @Param(name = "密钥个数", required = true, min = 1)
    private Integer count;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getKeyType() {
        return keyType;
    }

    public void setKeyType(Integer keyType) {
        this.keyType = keyType;
    }

    public Integer getKeySize() {
        return keySize;
    }

    public void setKeySize(Integer keySize) {
        this.keySize = keySize;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "GenKeyRequestDto{" +
                "userId=" + userId +
                ", keyType=" + keyType +
                ", keySize=" + keySize +
                ", count=" + count +
                '}';
    }
}
