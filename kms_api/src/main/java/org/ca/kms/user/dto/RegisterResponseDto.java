package org.ca.kms.user.dto;

import org.ligson.fw.core.facade.base.dto.BaseResponseDto;

import java.math.BigInteger;

/**
 * Created by ligson on 2016/4/25.
 *
 * @author ligson
 */
public class RegisterResponseDto extends BaseResponseDto {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "RegisterResponseDto{" +
                "id=" + id +
                '}';
    }
}
