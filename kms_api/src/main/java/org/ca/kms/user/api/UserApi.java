package org.ca.kms.user.api;

import org.ca.kms.user.dto.*;
import org.ligson.fw.core.facade.base.result.Result;

/**
 * Created by ligson on 2016/4/25.
 */
public interface UserApi {

    Result<RegisterResponseDto> register(RegisterRequestDto requestDto);

    Result<QueryUserResponseDto> query(QueryUserRequestDto requestDto);

    Result<LoginResponseDto> login(LoginRequestDto requestDto);
}
