package org.ca.kms.key.enums;

import org.ligson.fw.core.facade.enums.FailureCodeEnum;

/**
 * Created by ligson on 2016/5/5.
 */
public class KeyFailEnum extends FailureCodeEnum {

    public static final KeyFailEnum E_PARAM_11001 = new KeyFailEnum("E_PARAM_11001", "密钥类型错误");
    public static final KeyFailEnum E_PARAM_11002 = new KeyFailEnum("E_PARAM_11002", "密钥长度不匹配");

    /**
     * 默认构造
     *
     * @param code 错误代码
     * @param msg  错误信息
     */
    protected KeyFailEnum(String code, String msg) {
        super(code, msg);
    }
}
