package com.kfm.config;

import com.kfm.interceptor.AuthHandlerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
     @Autowired
    private AuthHandlerInterceptor authHandlerInterceptor;

    /**
     * 添加跨域支持
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 允许跨域访问的路径  '/**'表示应用的所有方法
        registry.addMapping("/**")
            // 允许跨域访问的来源 '*'表示所有域名来源
            .allowedOriginPatterns("*")
            // .allowedOrigins("*") // 允许跨域访问的来源 SpringBoot2.4.0之前的版本
            // 允许跨域请求的方法  '*'表示所有
            .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
            // 是否允许发送cookie true-允许 false-不允许 默认false。对服务器有特殊要求的请求，比如请求方法是PUT或DELETE，或者Content-Type字段的类型是application/json，这个值只能设为true
            .allowCredentials(true)
            // 预检间隔时间1小时，单位为秒。指定本次预检请求的有效期，在有效期间，不用发出另一条预检请求。
            // 浏览器发出CORS简单请求，只需要在头信息之中增加一个Origin字段
            // 浏览器发出CORS非简单请求，会在正式通信之前，增加一次OPTIONS查询请求，称为"预检"请求（preflight）。浏览器先询问服务器，当前网页所在的域名是否在服务器的许可名单之中，以及可以使用哪些HTTP动词和头信息字段。只有得到肯定答复，浏览器才会发出正式的XMLHttpRequest请求，否则就报错。
            .maxAge(3600)
            // 允许跨域请求可携带的header，'*'表所有header头。CORS请求时，XMLHttpRequest对象的getResponseHeader()方法只能拿到6个基本字段：Cache-Control、Content-Language、Content-Type、Expires、Last-Modified、Pragma。如果想拿到其他字段，就必须在Access-Control-Expose-Headers里面指定
            .allowedHeaders("*");
    }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(authHandlerInterceptor)
//                .addPathPatterns("/**")
//                .excludePathPatterns("/**/redis/**")
//                .excludePathPatterns("/**/login/**")
//                .excludePathPatterns("/**/error/**")
//                .excludePathPatterns("/**/swagger-ui/**")
//                .excludePathPatterns("/**/swagger-resources/**")
//                .excludePathPatterns("/**/v3/**");
//    }
}