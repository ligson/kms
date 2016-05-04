package org.ca.kms.user.enums;

import org.ligson.fw.core.facade.enums.FailureCodeEnum;

/**
 * Created by ligson on 2016/4/26.
 */
public class UserFailCodeEnum extends FailureCodeEnum {

    /***
     * 用户参数错误代码110开头
     */
    public static final UserFailCodeEnum E_PARAM_11001 = new UserFailCodeEnum("E_PARAM_11001", "用户名、邮箱或者手机号至少存在一项!");


    /**
     * 默认构造
     *
     * @param code 错误代码
     * @param msg  错误信息
     */
    protected UserFailCodeEnum(String code, String msg) {
        super(code, msg);
    }
}
