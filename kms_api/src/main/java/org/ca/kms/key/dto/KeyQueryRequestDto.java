package org.ca.kms.key.dto;

import org.ca.kms.key.enums.KeyStatus;
import org.ca.kms.key.enums.KeyType;
import org.ligson.fw.core.facade.base.dto.BaseQueryPageRequestDto;

import java.math.BigInteger;
import java.util.Date;

/**
 * Created by ligson on 2016/5/5.
 */
public class KeyQueryRequestDto extends BaseQueryPageRequestDto {
    private String id;
    private Integer keyType;
    private Integer keySize;
    private String userId;
    //private PrivateKey privateKey;
    //private PublicKey publicKey;
    /***
     * @see KeyStatus#getCode()
     */
    private Integer keyStatus;
    /***
     * @see KeyType#getCode()
     */
    private Date createTime;
    private Date useTime;
    private Date expiredTime;
    private Date revokeTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getKeyStatus() {
        return keyStatus;
    }

    public void setKeyStatus(Integer keyStatus) {
        this.keyStatus = keyStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUseTime() {
        return useTime;
    }

    public void setUseTime(Date useTime) {
        this.useTime = useTime;
    }

    public Date getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Date expiredTime) {
        this.expiredTime = expiredTime;
    }

    public Date getRevokeTime() {
        return revokeTime;
    }

    public void setRevokeTime(Date revokeTime) {
        this.revokeTime = revokeTime;
    }

    @Override
    public String toString() {
        return "Key{" +
                "id=" + id +
                ", keyType=" + keyType +
                ", keySize=" + keySize +
                ", userId=" + userId +
                ", keyStatus=" + keyStatus +
                ", createTime=" + createTime +
                ", useTime=" + useTime +
                ", expiredTime=" + expiredTime +
                ", revokeTime=" + revokeTime +
                '}';
    }
}
