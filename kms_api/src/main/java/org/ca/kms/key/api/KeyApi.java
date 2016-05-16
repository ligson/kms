package org.ca.kms.key.api;

import org.ca.kms.key.dto.*;
import org.ligson.fw.core.facade.base.result.Result;

/**
 * Created by ligson on 2016/5/5.
 */
public interface KeyApi {
    Result<GenKeyResponseDto> genKey(GenKeyRequestDto requestDto);

    Result<KeyQueryResponseDto> queryKey(KeyQueryRequestDto requestDto);

    Result<DestoryKeyResponseDto> destoryKey(DestoryKeyRequestDto requestDto);

    Result<ModifyKeyResponseDto> modifyKey(ModifyKeyRequestDto requestDto);

    Result<QueryKeyPairResponseDto> queryKeyPair(QueryKeyPairRequestDto requestDto);

    Result<GenCsrResponseDto> genCsr(GenCsrRequestDto requestDto);
}
