package com.shanzhu.market.common.sercurity.resolver;


import com.alibaba.fastjson.JSON;
import com.shanzhu.market.common.redis.service.RedisTemplateService;
import com.shanzhu.market.entity.domain.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * 自定义参数解析器
 * 作用：将请求映射方法(接口)中声明UserInfo类型的参数解析成当前登录用户对象
 */
public class UserInfoArgumentResolver implements HandlerMethodArgumentResolver {
    @Autowired
    private RedisTemplateService redisService;
    //当前解析器能解析类型--表示当前解析器要解析的参数类型-UserInfo
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return
                methodParameter.getParameterType() == Employee.class
                && methodParameter.hasParameterAnnotation(UserParam.class);
    }
    //怎么解析支持的参数--怎么讲UserInfo参数解析成当前登录用户对象
    //必须保证supportsParameter 返回true 才会执行
    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) throws Exception {
        //token--request--
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        String token = request.getHeader("token");
        String userStr = redisService.getCacheObject(token);
        if(!StringUtils.hasText(userStr)){
            return null;
        }
        return JSON.parseObject(userStr, Employee.class);
    }
}
