package com.workstudy.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @Author: 刘其悦
 * @Date: 2020/11/15 11:42
 */
@Configuration
public class WorkStudyMSCorsConfiguration {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        //是否允许请求带有验证信息
        corsConfiguration.setAllowCredentials(true);
        //允许访问的客户端域名
        corsConfiguration.addAllowedOrigin("*");
        //允许服务端访问的客户端请求头
        corsConfiguration.addAllowedHeader("*");
        //允许访问的方法名,GET POST等
        corsConfiguration.addAllowedMethod("*");
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }

}
