package org.ca.kms.user.biz;

import org.ca.kms.user.domain.UserEntity;
import org.ca.kms.user.dto.QueryUserRequestDto;
import org.ca.kms.user.dto.QueryUserResponseDto;
import org.ca.kms.user.service.UserService;
import org.ca.kms.user.vo.User;
import org.ligson.fw.core.common.biz.AbstractBiz;
import org.ligson.fw.core.entity.Pagination;
import org.ligson.fw.core.facade.annotation.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ligson on 2016/4/26.
 */
@Api(name = "用户查询接口")
@Component(value = "queryUserBiz")
public class QueryUserBiz extends AbstractBiz<QueryUserRequestDto, QueryUserResponseDto> {
    @Resource
    private UserService userService;

    @Override
    public void before() {

    }

    @Override
    public Boolean paramCheck() {
        return null;
    }

    @Override
    public Boolean bizCheck() {
        return null;
    }

    @Override
    public Boolean txnPreProcessing() {
        UserEntity entity = new UserEntity();
        BeanUtils.copyProperties(requestDto, entity);
        entity.setPageAble(requestDto.getPageAble());
        entity.setOffset((requestDto.getPageNum() - 1) * requestDto.getPageSize());
        entity.setMax(requestDto.getPageSize());

        Pagination<UserEntity> pagination = userService.findAllByEqAnd(entity);
        if (requestDto.getPageAble()) {
            List<UserEntity> entities = pagination.getDatas();
            List<User> users = new ArrayList<>();
            for (UserEntity userEntity : entities) {
                User user = new User();
                BeanUtils.copyProperties(userEntity, user);
                users.add(user);
            }
            responseDto.setUserList(users);
        } else {
            List<UserEntity> entities = pagination.getDatas();
            if (!CollectionUtils.isEmpty(entities)) {
                UserEntity userEntity = entities.get(0);
                User user = new User();
                BeanUtils.copyProperties(userEntity, user);
                responseDto.setUser(user);
            }
        }
        responseDto.setPageNum(requestDto.getPageNum());
        responseDto.setPageSize(requestDto.getPageSize());
        responseDto.setTotalCount(pagination.getTotalCount());
        responseDto.setSuccess(true);
        setSuccessResult();
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
