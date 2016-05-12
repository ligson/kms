package org.ca.kms.key.dto;

import org.ligson.fw.core.facade.base.dto.BaseRequestDto;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ligson on 2016/5/5.
 */
public class DestoryKeyRequestDto extends BaseRequestDto {
    private List<String> idList = new ArrayList<>();

    public List<String> getIdList() {
        return idList;
    }

    public void setIdList(List<String> idList) {
        this.idList = idList;
    }
}
