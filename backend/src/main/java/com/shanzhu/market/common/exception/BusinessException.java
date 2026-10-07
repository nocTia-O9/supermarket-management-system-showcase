package com.shanzhu.market.common.exception;

import com.shanzhu.market.common.constants.HttpStatus;

public class BusinessException extends SysException {
    public BusinessException(String message, Integer code) {
        super(message, code);
    }

    public BusinessException(String msg) {
        super(msg, HttpStatus.CODE_BUSINESS_ERROR);
    }
}
