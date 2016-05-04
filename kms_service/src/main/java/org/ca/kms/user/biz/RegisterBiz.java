package org.ca.kms.user.biz;

import org.ca.common.user.enums.UserState;
import org.ca.kms.user.domain.UserEntity;
import org.ca.kms.user.dto.RegisterRequestDto;
import org.ca.kms.user.dto.RegisterResponseDto;
import org.ca.kms.user.enums.UserFailCodeEnum;
import org.ca.kms.user.service.UserService;
import org.ligson.fw.core.common.biz.AbstractBiz;
import org.ligson.fw.core.common.paramcheck.CommonParamCheck;
import org.ligson.fw.core.facade.annotation.Api;
import org.ligson.fw.core.facade.enums.FailureCodeEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * Created by ligson on 2016/4/26.
 */
@Api(name = "注册接口")
@Component("registerBiz")
public class RegisterBiz extends AbstractBiz<RegisterRequestDto, RegisterResponseDto> {

    @Resource
    private UserService userService;

    @Override
    public void before() {

    }

    @Override
    public Boolean paramCheck() {
        if (StringUtils.isEmpty(requestDto.getName()) && StringUtils.isEmpty(requestDto.getEmail()) && StringUtils.isEmpty(requestDto.getMobile())) {
            setFailureResult(UserFailCodeEnum.E_PARAM_11001);
            return false;
        }
        if (!StringUtils.isEmpty(requestDto.getName())) {
            if (!CommonParamCheck.isValidName(requestDto.getName(), context, responseDto.getClass())) {
                return false;
            }
        }
        if (!StringUtils.isEmpty(requestDto.getEmail())) {
            if (!CommonParamCheck.isValidEmail(requestDto.getEmail(), context, responseDto.getClass())) {
                return false;
            }
        }
        if (!StringUtils.isEmpty(requestDto.getMobile())) {
            if (!CommonParamCheck.isValidMobile(requestDto.getMobile(), context, responseDto.getClass())) {
                return false;
            }
        }
        return CommonParamCheck.isValidPwd(requestDto.getPassword(), context, responseDto.getClass());
    }

    @Override
    public Boolean bizCheck() {
        if (!StringUtils.isEmpty(requestDto.getName())) {
            if (userService.countBy("name", requestDto.getName()) > 0) {
                setFailureResult(FailureCodeEnum.E_BIZ_20001);
                return false;
            }
        }
        if (!StringUtils.isEmpty(requestDto.getEmail())) {
            if (userService.countBy("email", requestDto.getEmail()) > 0) {
                setFailureResult(FailureCodeEnum.E_BIZ_20005);
                return false;
            }
        }
        if (!StringUtils.isEmpty(requestDto.getMobile())) {
            if (userService.countBy("mobile", requestDto.getMobile()) > 0) {
                setFailureResult(FailureCodeEnum.E_BIZ_20006);
                return false;
            }
        }
        return true;
    }

    @Override
    public Boolean txnPreProcessing() {
        UserEntity entity = new UserEntity();
        BeanUtils.copyProperties(requestDto, entity);
        entity.setStatus(UserState.VALID.getCode());
        context.setAttr("entity", entity);
        return true;
    }

    @Override
    public Boolean persistence() {
        UserEntity entity = (UserEntity) context.getAttr("entity");
        userService.add(entity);
        responseDto.setId(entity.getId());
        setSuccessResult();
        return true;
    }

    @Override
    public void after() {

    }
}
