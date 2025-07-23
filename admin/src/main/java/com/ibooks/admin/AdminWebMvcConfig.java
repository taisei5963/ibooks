package com.ibooks.admin;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 管理サイトWebMVC設定
 */
@Configuration
public class AdminWebMvcConfig implements WebMvcConfigurer {
    /**
     * 管理サイトハンドラーインタセプター
     *
     * @return 管理サイトハンドラーインタセプター
     */
    @Bean
    AdminHandlerInterceptor adminHandlerInterceptor() {
        return new AdminHandlerInterceptor();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminHandlerInterceptor());
    }
}
