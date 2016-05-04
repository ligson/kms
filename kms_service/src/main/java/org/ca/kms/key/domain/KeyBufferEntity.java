package org.ca.kms.key.domain;

import org.ligson.fw.core.entity.BasicEntity;

import java.math.BigInteger;
import java.util.Date;

/**
 * Created by ligson on 2016/5/4.
 */
public class KeyBufferEntity extends BasicEntity {
    private BigInteger id;
    private BigInteger userId;
    private String encPrivateKey;
    private String pkcs10;
    private String keyStatus;
    private int keySize;
    private String keyType;
    private Date createTime;
    private Date useTime;
    private Date expiredTime;
    private Date revokeTime;
}
