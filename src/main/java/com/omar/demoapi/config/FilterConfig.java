package com.omar.demoapi.config;

import com.omar.demoapi.security.JwtAuthenticationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<JwtAuthenticationFilter> jwtFilter(
            JwtAuthenticationFilter jwtAuthenticationFilter
    ) {
        FilterRegistrationBean<JwtAuthenticationFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(jwtAuthenticationFilter);

        // Apply filter to API routes only
        registrationBean.addUrlPatterns("/api/*");

        // Optional: order (lower = earlier)
        registrationBean.setOrder(1);

        return registrationBean;
    }
}
