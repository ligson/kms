package org.ca.kms.user.biz;

import org.ca.common.user.enums.LoginNameType;
import org.ca.common.user.enums.UserState;
import org.ca.kms.user.domain.UserEntity;
import org.ca.kms.user.dto.LoginRequestDto;
import org.ca.kms.user.dto.LoginResponseDto;
import org.ca.kms.user.enums.UserFailCodeEnum;
import org.ca.kms.user.service.UserService;
import org.ca.kms.user.vo.User;
import org.ligson.fw.core.common.biz.AbstractBiz;
import org.ligson.fw.core.common.paramcheck.CommonParamCheck;
import org.ligson.fw.core.facade.annotation.Api;
import org.ligson.fw.core.facade.enums.FailureCodeEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by ligson on 2016/4/26.
 */
@Api(name = "用户登录接口")
@Component(value = "loginBiz")
public class LoginBiz extends AbstractBiz<LoginRequestDto, LoginResponseDto> {

    @Resource
    private UserService userService;

    @Override
    public void before() {

    }

    @Override
    public Boolean paramCheck() {
        if (requestDto.getLoginNameType().equals(LoginNameType.EMAIL)) {
            if (!CommonParamCheck.isValidEmail(requestDto.getLoginName(), context, responseDto.getClass())) {
                return false;
            }
        }
        if (requestDto.getLoginNameType().equals(LoginNameType.MOBILE)) {
            if (!CommonParamCheck.isValidMobile(requestDto.getLoginName(), context, responseDto.getClass())) {
                return false;
            }
        }
        if (requestDto.getLoginNameType().equals(LoginNameType.NAME)) {
            if (!CommonParamCheck.isValidName(requestDto.getLoginName(), context, responseDto.getClass())) {
                return false;
            }
        }
        if (requestDto.getLoginNameType().equals(LoginNameType.CERT)) {
            //TODO 数字证书认证
        }
        return true;
    }

    @Override
    public Boolean bizCheck() {
        UserEntity entity = new UserEntity();
        if (requestDto.getLoginNameType().equals(LoginNameType.EMAIL)) {
            entity.setEmail(requestDto.getLoginName());
        }
        if (requestDto.getLoginNameType().equals(LoginNameType.MOBILE)) {
            entity.setMobile(requestDto.getLoginName());
        }
        if (requestDto.getLoginNameType().equals(LoginNameType.NAME)) {
            entity.setName(requestDto.getLoginName());
        }
        entity = userService.findByAnd(entity);
        if (entity == null) {
            setFailureResult(FailureCodeEnum.E_BIZ_20003);
            return false;
        }
        if (!requestDto.getPassword().equals(entity.getPassword())) {
            setFailureResult(FailureCodeEnum.E_BIZ_20004);
            return false;
        }
        if (UserState.DISABLED.equals(entity.getStatus())) {
            setFailureResult(UserFailCodeEnum.E_BIZ_20002);
            return false;
        }
        context.setAttr("entity",entity);
        return true;
    }

    @Override
    public Boolean txnPreProcessing() {
        UserEntity entity = (UserEntity) context.getAttr("entity");
        User user = new User();
        BeanUtils.copyProperties(entity, user);
        responseDto.setUser(user);
        responseDto.setSuccess(true);
        return true;
    }

    @Override
    public Boolean persistence() {
        return null;
    }

    @Override
    public void after() {

    }
}
