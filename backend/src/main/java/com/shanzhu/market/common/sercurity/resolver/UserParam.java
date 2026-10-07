package com.shanzhu.market.common.sercurity.resolver;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 定义userInfo 参数注入注解
 * 贴有该注解UserInfo类型的参数使用自定义参数数解析
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserParam {
}
