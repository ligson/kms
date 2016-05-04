package org.ca.kms.user.dto;

import org.ca.common.user.enums.LoginNameType;
import org.ligson.fw.core.facade.annotation.Param;
import org.ligson.fw.core.facade.base.dto.BaseRequestDto;

/**
 * Created by ligson on 2016/4/26.
 */
public class LoginRequestDto extends BaseRequestDto {
    @Param(name = "登陆名", required = true)
    private String loginName;
    @Param(name = "登陆名类型", required = true)
    private LoginNameType loginNameType;
    @Param(name = "登陆密码", required = true)
    private String password;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public LoginNameType getLoginNameType() {
        return loginNameType;
    }

    public void setLoginNameType(LoginNameType loginNameType) {
        this.loginNameType = loginNameType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginRequestDto{" +
                "loginName='" + loginName + '\'' +
                ", loginNameType=" + loginNameType +
                ", password='" + password + '\'' +
                '}';
    }
}
