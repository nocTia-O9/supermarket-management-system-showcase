package com.shanzhu.market.common.exception;

import com.shanzhu.market.common.constants.HttpStatus;

public class SysException extends RuntimeException {
    private Integer code;

    public SysException(String message, Integer code) {
        super(message);
        this.code = code;
    }
    public SysException(String msg){
        super(msg);
        this.code= HttpStatus.CODE_ERROR;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
