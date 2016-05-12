package org.ca.kms.key.dto;

import org.ca.kms.key.enums.KeyStatus;
import org.ligson.fw.core.facade.annotation.Param;
import org.ligson.fw.core.facade.base.dto.BaseRequestDto;

import java.math.BigInteger;
import java.util.Date;

/**
 * Created by ligson on 2016/5/5.
 */
public class ModifyKeyRequestDto extends BaseRequestDto {
    @Param(name = "id", required = true)
    private String id;
    /***
     * @see KeyStatus#getCode()
     */
    private Integer keyStatus;
    private Date useTime;
    private Date expiredTime;
    private Date revokeTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getKeyStatus() {
        return keyStatus;
    }

    public void setKeyStatus(Integer keyStatus) {
        this.keyStatus = keyStatus;
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
        return "ModifyKeyRequestDto{" +
                "id=" + id +
                ", keyStatus=" + keyStatus +
                ", useTime=" + useTime +
                ", expiredTime=" + expiredTime +
                ", revokeTime=" + revokeTime +
                '}';
    }
}
