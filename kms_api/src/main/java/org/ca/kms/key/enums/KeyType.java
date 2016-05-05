package org.ca.kms.key.enums;

/**
 * Created by ligson on 2016/5/5.
 */
public enum KeyType {
    RSA(1, "RSA"), SM2(2, "SM2");
    private int code;
    private String msg;

    KeyType(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static KeyType getByCode(Integer keyType) {
        if (keyType == 1) {
            return RSA;
        } else {
            return SM2;
        }
    }
}
