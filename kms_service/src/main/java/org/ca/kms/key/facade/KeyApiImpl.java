package org.ca.kms.key.facade;

import org.ca.kms.key.api.KeyApi;
import org.ca.kms.key.biz.GenKeyBiz;
import org.ca.kms.key.biz.QueryKeyBiz;
import org.ca.kms.key.dto.*;
import org.ligson.fw.core.facade.base.result.Result;

import javax.annotation.Resource;

/**
 * Created by ligson on 2016/5/5.
 */
public class KeyApiImpl implements KeyApi {

    @Resource
    private GenKeyBiz genKeyBiz;
    @Resource
    private QueryKeyBiz queryKeyBiz;

    @Override
    public Result<GenKeyResponseDto> genKey(GenKeyRequestDto requestDto) {
        return genKeyBiz.operation(requestDto);
    }

    @Override
    public Result<KeyQueryResponseDto> queryKey(KeyQueryRequestDto requestDto) {
        return queryKeyBiz.operation(requestDto);
    }

    @Override
    public Result<DestoryKeyResponseDto> destoryKey(DestoryKeyRequestDto requestDto) {
        return null;
    }

    @Override
    public Result<ModifyKeyResponseDto> modifyKey(ModifyKeyRequestDto requestDto) {
        return null;
    }
}
