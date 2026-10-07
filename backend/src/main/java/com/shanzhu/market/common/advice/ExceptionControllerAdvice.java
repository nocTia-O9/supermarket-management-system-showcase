package com.shanzhu.market.common.advice;


import com.shanzhu.market.common.constants.HttpStatus;
import com.shanzhu.market.common.exception.BusinessException;
import com.shanzhu.market.common.web.response.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 统一异常处理类的基类
 */
@RestControllerAdvice
public class ExceptionControllerAdvice {
    @ExceptionHandler(RuntimeException.class)
    public JsonResult<?> commonExceptionHandler(RuntimeException ex){
        ex.printStackTrace();
        return JsonResult.error(HttpStatus.CODE_BUSINESS_ERROR,ex.getMessage());
    }
    @ExceptionHandler(BusinessException.class)
    public JsonResult<?> businessHanler(BusinessException ex){
        return JsonResult.error(HttpStatus.CODE_BUSINESS_ERROR,ex.getMessage());
    }
}
