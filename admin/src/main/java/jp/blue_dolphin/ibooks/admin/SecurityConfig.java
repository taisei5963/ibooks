package jp.blue_dolphin.ibooks.admin;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * セキュリティ設定
 */
@EnableWebSecurity
@Configuration
public class SecurityConfig {
    /**
     * セキュリティフィルターチェーンを設定する
     *
     * @param http Spring SecurityのHttpセキュリティー設定を行うためのオブジェクト
     * @return 設定されたSecurityFilterChainのインスタンス
     * @throws Exception 設定中に例外が発生した場合
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        return http.build();
    }
}
