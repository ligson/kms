package org.ca.kms.key.dto;

import org.ca.kms.key.vo.Key;
import org.ligson.fw.core.facade.base.dto.BaseQueryPageResponseDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ligson on 2016/5/5.
 */
public class KeyQueryResponseDto extends BaseQueryPageResponseDto {
    private List<Key> keyList = new ArrayList<>();
    private Key key;

    public List<Key> getKeyList() {
        return keyList;
    }

    public void setKeyList(List<Key> keyList) {
        this.keyList = keyList;
    }

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "KeyQueryResponseDto{" +
                "keyList=" + keyList +
                ", key=" + key +
                '}';
    }
}
