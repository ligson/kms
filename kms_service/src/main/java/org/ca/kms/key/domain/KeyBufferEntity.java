package org.ca.kms.key.domain;

import org.ca.kms.key.enums.KeyStatus;
import org.ca.kms.key.enums.KeyType;
import org.hibernate.annotations.GenericGenerator;
import org.ligson.fw.core.entity.BasicEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by ligson on 2016/5/4.
 */
@Table(name = "key_buffer")
@Entity
public class KeyBufferEntity extends BasicEntity {
    private String id;
    private String userId;
    private String privateKey;
    private String publicKey;
    /***
     * @see KeyStatus#getCode()
     */
    private Integer keyStatus;
    private Integer keySize;
    /***
     * @see KeyType#getCode()
     */
    private Integer keyType;
    private Date createTime;
    private Date useTime;
    private Date expiredTime;
    private Date revokeTime;

    @Id
    @GeneratedValue(generator = "dr.id")
    @GenericGenerator(name = "dr.id", strategy = "org.ligson.fw.core.common.idgenerator.DateRandomGenerator")
    @Column(length = 32)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(length = 32, name = "user_id")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Column(length = 2048, name = "private_key")
    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    @Column(length = 2048, name = "public_key")
    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    @Column(name = "key_status")
    public Integer getKeyStatus() {
        return keyStatus;
    }

    public void setKeyStatus(Integer keyStatus) {
        this.keyStatus = keyStatus;
    }

    @Column(name = "key_size")
    public Integer getKeySize() {
        return keySize;
    }

    public void setKeySize(Integer keySize) {
        this.keySize = keySize;
    }

    @Column(name = "key_type")
    public Integer getKeyType() {
        return keyType;
    }

    public void setKeyType(Integer keyType) {
        this.keyType = keyType;
    }

    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "use_time")
    public Date getUseTime() {
        return useTime;
    }

    public void setUseTime(Date useTime) {
        this.useTime = useTime;
    }

    @Column(name = "expired_time")
    public Date getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Date expiredTime) {
        this.expiredTime = expiredTime;
    }

    @Column(name = "revoke_time")
    public Date getRevokeTime() {
        return revokeTime;
    }

    public void setRevokeTime(Date revokeTime) {
        this.revokeTime = revokeTime;
    }
}
