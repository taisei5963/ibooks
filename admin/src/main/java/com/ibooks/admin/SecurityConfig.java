package com.ibooks.admin;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * セキュリティコンフィグ
 */
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    /**
     * アプリケーションのセキュリティフィルターチェーンを定義する
     *
     * @param httpSecurity HTTPセキュリティオブジェクト
     * @return HTTPセキュリティビルドオブジェクト
     * @throws Exception 設定中にエラーが発生した場合
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        return httpSecurity.build();
    }
}
