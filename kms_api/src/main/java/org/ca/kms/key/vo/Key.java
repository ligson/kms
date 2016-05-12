package org.ca.kms.key.vo;

import org.ca.kms.key.enums.KeyStatus;
import org.ca.kms.key.enums.KeyType;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;

/**
 * Created by ligson on 2016/5/5.
 */
public class Key implements Serializable {
    private String id;
    private Integer keyType;
    private Integer keySize;
    private String userId;
    private String privateKey;
    private String publicKey;
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

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    @Override
    public String toString() {
        return "Key{" +
                "id=" + id +
                ", keyType=" + keyType +
                ", keySize=" + keySize +
                ", userId=" + userId +
                ", privateKey='" + privateKey + '\'' +
                ", publicKey='" + publicKey + '\'' +
                ", keyStatus=" + keyStatus +
                ", createTime=" + createTime +
                ", useTime=" + useTime +
                ", expiredTime=" + expiredTime +
                ", revokeTime=" + revokeTime +
                '}';
    }
}
