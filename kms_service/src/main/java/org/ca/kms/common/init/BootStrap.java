package org.ca.kms.common.init;

import org.ca.kms.key.api.KeyApi;
import org.ca.kms.user.api.UserApi;
import org.ca.kms.user.dto.QueryUserRequestDto;
import org.ca.kms.user.dto.QueryUserResponseDto;
import org.ca.kms.user.dto.RegisterRequestDto;
import org.ca.kms.user.dto.RegisterResponseDto;
import org.ca.kms.user.vo.User;
import org.ligson.fw.core.common.idgenerator.Comment;
import org.ligson.fw.core.facade.base.result.Result;
import org.ligson.fw.string.encode.HashHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by ligson on 2016/5/5.
 */
@Comment
public class BootStrap implements InitializingBean {
    private static Logger logger = LoggerFactory.getLogger(BootStrap.class);
    @Resource
    private KeyApi keyApi;
    @Resource
    private UserApi userApi;

    @Override
    public void afterPropertiesSet() throws Exception {
        QueryUserRequestDto requestDto = new QueryUserRequestDto();
        requestDto.setName("master");
        requestDto.setPageAble(false);
        Result<QueryUserResponseDto> result = userApi.query(requestDto);
        if (result.isSuccess()) {
            User master = result.getData().getUser();
            if (master == null) {
                RegisterRequestDto registerRequestDto = new RegisterRequestDto();
                registerRequestDto.setName("master");
                registerRequestDto.setSex(false);
                registerRequestDto.setPassword(HashHelper.md5("password"));
                registerRequestDto.setBirth(new Date());
                Result<RegisterResponseDto> registerResult = userApi.register(registerRequestDto);
                if (registerResult.isSuccess()) {
                    logger.info("系统成功初始化,管理员用户名:master,密码:password");
                } else {
                    logger.error("系统初始化失败!code:{},errorMsg:{}", registerResult.getFailureCode(), registerResult.getFailureMessage());
                }
            } else {
                logger.debug("系统已经初始化");
            }
        } else {
            logger.error("系统启动查询失败!code:{},errorMsg:{}", result.getFailureCode(), result.getFailureMessage());
        }
    }
}
