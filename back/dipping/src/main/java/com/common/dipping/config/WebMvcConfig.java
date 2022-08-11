package com.common.dipping.config;

//import com.common.dipping.filter.HeaderFilter;
//import com.common.dipping.interceptor.JwtTokenInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
            "classpath:/static/",
            "classpath:/public/",
            "classpath:/",
            "classpath:/resources/",
            "classpath:/META-INF/resources/",
            "classpath:/META-INF/resources/webjars/"
    };

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 정적 자원의 경로를 허용
        registry.addResourceHandler("/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
    }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(jwtTokenInterceptor()).excludePathPatterns("/api/login/*").excludePathPatterns("/api/signUp");
//    }

//    @Bean
//    public FilterRegistrationBean<HeaderFilter> getFilterRegistrationBean() {
//        FilterRegistrationBean<HeaderFilter> registrationBean = new FilterRegistrationBean<>(createHeaderFilter());
//        registrationBean.setOrder(Integer.MIN_VALUE);
//        registrationBean.addUrlPatterns("/**");
//        return registrationBean;
//    }
//
//    @Bean
//    public HeaderFilter createHeaderFilter() {
//        return new HeaderFilter();
//    }

//    @Bean
//    public HandlerInterceptor jwtTokenInterceptor() {
//        return new JwtTokenInterceptor();
//    }

}