package com.csu.etrainingsystem.config;

import com.csu.etrainingsystem.security.SecurityInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

//    @Bean
//    public HandlerInterceptor getInterceptor() {
//        return new SecurityInterceptor();
//    }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        String[] pathPatterns = {
//                "/admin",
//                "/batch",
//                "/student"
//        };
//        String[] exludePathPatterns = {
//                "/swagger-ui.html",
//                "/webjars/springfox-swagger-ui/**",
//                "/swagger-resources/**",
//        };
//        registry.addInterceptor(getInterceptor()).addPathPatterns(pathPatterns)
//                .excludePathPatterns(exludePathPatterns);
//
//    }
}
