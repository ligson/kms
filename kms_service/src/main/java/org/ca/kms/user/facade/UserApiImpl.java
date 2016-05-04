package org.ca.kms.user.facade;

import org.ca.kms.user.api.UserApi;
import org.ca.kms.user.biz.QueryUserBiz;
import org.ca.kms.user.biz.RegisterBiz;
import org.ca.kms.user.biz.LoginBiz;
import org.ca.kms.user.dto.*;
import org.ligson.fw.core.facade.base.result.Result;

import javax.annotation.Resource;

/**
 * Created by ligson on 2016/4/26.
 */
public class UserApiImpl implements UserApi {

    @Resource
    private LoginBiz loginBiz;
    @Resource
    private RegisterBiz registerBiz;
    @Resource
    private QueryUserBiz queryUserBiz;

    @Override
    public Result<RegisterResponseDto> register(RegisterRequestDto requestDto) {
        return registerBiz.operation(requestDto);
    }

    @Override
    public Result<QueryUserResponseDto> query(QueryUserRequestDto requestDto) {
        return queryUserBiz.operation(requestDto);
    }

    @Override
    public Result<LoginResponseDto> login(LoginRequestDto requestDto) {
        return loginBiz.operation(requestDto);
    }
}
