package com.shanzhu.market.common.constants;

public interface HttpStatus {
    /**
     * 操作成功
     */
    Integer CODE_SUCCESS = 200;
    /**
     * 请先登录
     */
    Integer CODE_NOLOGIN = 401;
    /**
     * 系统异常，请联系管理员
     */
    Integer CODE_ERROR = 500;
    /**
     * 参数异常
     */
    Integer CODE_ERROR_PARAM = 400;

    /**业务异常*/
    Integer CODE_BUSINESS_ERROR=50000;

}
