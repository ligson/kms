package org.ca.kms.cert.domain;

import org.ca.common.cert.enums.CertStatus;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;

/**
 * Created by ligson on 2016/5/5.
 * 签名证书
 */
@Entity
@Table(name = "sign_cert")
public class SignCertEntity {
    private String id;
    private String userId;
    private String serialNum;
    private String issuer;
    private String subject;
    /***
     * @see CertStatus#getCode()
     */
    private Integer status;
    private String buffer;
    private Date notBefore;
    private Date notAfter;
    private Date createTime;

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

    @Column(name = "serial_num")
    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    @Column(name = "issuer")
    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    @Column(name = "subject")
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Column
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column
    public String getBuffer() {
        return buffer;
    }

    public void setBuffer(String buffer) {
        this.buffer = buffer;
    }

    @Column(name = "not_before")

    public Date getNotBefore() {
        return notBefore;
    }

    public void setNotBefore(Date notBefore) {
        this.notBefore = notBefore;
    }

    @Column(name = "not_after")
    public Date getNotAfter() {
        return notAfter;
    }

    public void setNotAfter(Date notAfter) {
        this.notAfter = notAfter;
    }

    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
