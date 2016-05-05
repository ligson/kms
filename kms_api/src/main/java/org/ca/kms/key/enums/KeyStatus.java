package org.ca.kms.key.enums;

/**
 * Created by ligson on 2016/5/5.
 */
public enum KeyStatus {
    READY(1, "准备使用"), INUSE(3, "正在使用"), DESTROY(6, "销毁");
    //"ready", "apply", "inuse", "history", "archived", "destroy"
    private int code;
    private String msg;

    KeyStatus(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
