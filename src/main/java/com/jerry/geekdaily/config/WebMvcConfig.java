package com.jerry.geekdaily.config;

import com.jerry.geekdaily.config.jwt.JwtFilter;
import com.jerry.geekdaily.interceptor.LoginInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

/**
* @Description:    java类作用描述
* @Author:         liulei
* @CreateDate:     2018/7/7 11:30
* 注意：WebMvcConfigurerAdapter（老版本  已过时）必须要用WebMvcConfigurationSupport替换
*/
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Autowired
    LoginInterceptor loginInterceptor;

    @Autowired
    JwtFilter jwtFilter;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //将所有/static/** 访问都映射到classpath:/static/ 目录下
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
//        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/js/");
//        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/css/");
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        super.addResourceHandlers(registry);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 日志拦截器
        //registry.addInterceptor(logInterceptor).addPathPatterns("/**");
        //token验证拦截
        registry.addInterceptor(jwtFilter).addPathPatterns("/**");
        // 登录拦截器
        registry.addInterceptor(loginInterceptor)
//                .addPathPatterns("/**")
                .addPathPatterns("/")
                .addPathPatterns("/index")
                .addPathPatterns("/webArticle")
                .addPathPatterns("/webAddArticle")
                .addPathPatterns("/webUpdateArticle");
                // 排除路径
//                .excludePathPatterns("/login")
//                .excludePathPatterns("/register")
//                // 排除资源请求
//                .excludePathPatterns("/static/**")
//                .excludePathPatterns("/**/images/**")
//                .excludePathPatterns("/**/*.css")
//                .excludePathPatterns("/**/fonts/**")
//                .excludePathPatterns("/**/*.js");
    }

//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        //以前写SpringMVC的时候，如果需要访问一个页面，必须要写Controller类，
//        // 然后再写一个方法跳转到页面，感觉好麻烦，其实重写WebMvcConfigurerAdapter中的addViewControllers方法即可达到效果了
//        registry.addViewController("toLogin").setViewName("login");
//        logger.info("addViewControllers++++");
//        super.addViewControllers(registry);
//    }

//    // 设置跨域访问
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**").allowedHeaders("*")
//                .allowedMethods("*")
//                .allowedOrigins("*")
//                .allowCredentials(true);
//    }

}
