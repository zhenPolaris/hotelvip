package com.zhen.exception;

import com.zhen.enums.AppHttpCodeEnum;

/**
 * @author 甄子函
 * @date: 2022/9/22__20:55
 */
public class SystemException extends RuntimeException{

    private int code;

    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public SystemException(AppHttpCodeEnum httpCodeEnum) {
        super(httpCodeEnum.getMsg());
        this.code = httpCodeEnum.getCode();
        this.msg = httpCodeEnum.getMsg();
    }
}
