package com.shanzhu.market.common.sercurity.interceptor;


import com.alibaba.fastjson.JSONObject;
import com.shanzhu.market.common.constants.HttpStatus;
import com.shanzhu.market.common.redis.constants.RedisKeys;
import com.shanzhu.market.common.redis.service.RedisTemplateService;
import com.shanzhu.market.common.sercurity.annotation.HasPermisson;
import com.shanzhu.market.common.sercurity.annotation.NoRequireLogin;
import com.shanzhu.market.common.web.response.JsonResult;
import com.shanzhu.market.entity.domain.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EmpLoginInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisTemplateService redisTemplateService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            //optinos请求
            return true;
        }
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        //获取token
        String token = request.getHeader("token");
        //是否贴有不必登录的注解
        HandlerMethod handler1 = (HandlerMethod) handler;
        NoRequireLogin noRequireLogin = handler1.getMethodAnnotation(NoRequireLogin.class);
        if (noRequireLogin != null) {
            //贴有注解
            return true;

        } else {
            if (StringUtils.hasText(token)) {
                String value2 = redisTemplateService.getCacheObject(token);
                if (!StringUtils.hasText(value2)) {
                    JsonResult res = JsonResult.error(HttpStatus.CODE_BUSINESS_ERROR, "请先登录");
                    String result = JSONObject.toJSONString(res);
                    response.getWriter().println(result);
                    response.getWriter().flush();
                    return false;
                } else {
                    //缓存有值,延长存储时间
                    redisTemplateService.expire(token, RedisKeys.LOGIN_USER.getTimeout(), RedisKeys.LOGIN_USER.getTimeUnit());
                }
            } else {
                //没有token
                JsonResult res = JsonResult.error(HttpStatus.CODE_BUSINESS_ERROR, "请先登录");
                String result = JSONObject.toJSONString(res);
                response.getWriter().println(result);
                response.getWriter().flush();
                return false;
            }
        }
        //是否贴有HasPermisson注解
        HasPermisson hasPermisson = handler1.getMethodAnnotation(HasPermisson.class);
        if (hasPermisson != null) {
            if (!StringUtils.hasText(token)) {
                //没有token
                JsonResult res = JsonResult.error(HttpStatus.CODE_BUSINESS_ERROR, "请先登录");
                String result = JSONObject.toJSONString(res);
                response.getWriter().println(result);
                response.getWriter().flush();
                return false;
            }
            Employee employee = JSONObject.parseObject(redisTemplateService.getCacheObject(token), Employee.class);
            //判断是否是系统管理员
            if (employee.getIsAdmin()) {
                return true;
            }
            String value = hasPermisson.value();
            boolean contains = employee.getFlags().contains(value);
            if (!contains) {
                JsonResult res = JsonResult.error(HttpStatus.CODE_BUSINESS_ERROR, "您没有权限操作");
                String result = JSONObject.toJSONString(res);
                response.getWriter().println(result);
                response.getWriter().flush();
                return false;
            }
        }

        return true;
    }

}
