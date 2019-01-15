package com.csu.etrainingsystem.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


@Configuration
public class CorsConfig {
    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration RedisConfig = new CorsConfiguration();
        RedisConfig.setAllowCredentials(true);
        // 设置你要允许的网站域名，如果全允许则设为 *
        RedisConfig.addAllowedOrigin("*");
        // 如果要限制 HEADER 或 METHOD 请自行更改
        RedisConfig.addAllowedHeader("*");
        RedisConfig.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", RedisConfig);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        // 这个顺序很重要哦，为避免麻烦请设置在最前
        bean.setOrder(0);
        return bean;
    }

}
