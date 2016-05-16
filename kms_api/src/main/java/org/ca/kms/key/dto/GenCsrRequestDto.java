package org.ca.kms.key.dto;

import org.ligson.fw.core.facade.annotation.Param;
import org.ligson.fw.core.facade.base.dto.BaseRequestDto;

/**
 * Created by ligson on 2016/5/16.
 */
public class GenCsrRequestDto extends BaseRequestDto {
    @Param(name = "keyId", required = true)
    private String keyId;
    @Param(name = "subjectDn", required = true)
    private String subjectDn;

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public String getSubjectDn() {
        return subjectDn;
    }

    public void setSubjectDn(String subjectDn) {
        this.subjectDn = subjectDn;
    }
}
