package jp.blue_dolphin.ibooks.admin;

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
     * 管理サイトハンドラーインターセプター
     *
     * @return 管理サイトハンドラーインターセプター
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
