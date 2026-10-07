package com.shanzhu.market.common.config;

import com.shanzhu.market.common.sercurity.interceptor.EmpLoginInterceptor;
import com.shanzhu.market.common.util.PathUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 解决：1.跨域问题
 */
@Configuration
public class BaseWebConfig implements WebMvcConfigurer{
    @Bean
    public EmpLoginInterceptor empLoginInterceptor(){
        return new EmpLoginInterceptor();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(empLoginInterceptor())
//                .addPathPatterns("/**")
//                .excludePathPatterns("/","/login")
//                .excludePathPatterns("/files/**")
//                .excludePathPatterns("/static/**");
    }

    //跨域访问配置
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            //重写父类提供的跨域请求处理的接口
            public void addCorsMappings(CorsRegistry registry) {
                //添加映射路径
                registry.addMapping("/**")
                        //放行哪些原始域
                        .allowedOrigins("*")
                        //是否发送Cookie信息
                        .allowCredentials(true)
                        //放行哪些原始域(请求方式)
                        .allowedMethods("GET", "POST", "PUT", "DELETE","OPTIONS")
                        //放行哪些原始域(头部信息)
                        .allowedHeaders("*")
                        //暴露哪些头部信息（因为跨域访问默认不能获取全部头部信息）
                        .exposedHeaders("Header1", "Header2");
            }
        };
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String winPath = PathUtils.getClassLoadRootPath() + "/src/main/resources/static/files/";

        //第一个方法设置访问路径前缀，第二个方法设置资源路径
        registry.addResourceHandler("/files/**").
                addResourceLocations("file:" + winPath);
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }
}
