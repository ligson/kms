package org.ca.kms.user.dto;

import org.ligson.fw.core.facade.base.dto.BaseResponseDto;

/**
 * Created by ligson on 2016/4/25.
 */
public class ModifyUserResponseDto extends BaseResponseDto {
    private Boolean success;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "ModifyUserResponseDto{" +
                "success=" + success +
                '}';
    }
}
