package org.ca.kms.user.dto;

import org.ca.kms.user.vo.User;
import org.ligson.fw.core.facade.base.dto.BaseQueryPageResponseDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ligson on 2016/4/25.
 *
 * @author ligson
 */
public class QueryUserResponseDto extends BaseQueryPageResponseDto {
    private User user;
    private List<User> userList = new ArrayList<>();

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public String toString() {
        return "QueryUserResponseDto{" +
                "user=" + user +
                ", userList=" + userList +
                '}';
    }
}
