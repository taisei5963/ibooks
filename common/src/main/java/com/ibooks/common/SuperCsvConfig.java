package com.ibooks.common;

import com.github.mygreen.supercsv.builder.SpringBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SuperCsv用Beanファクトリのコンテナ設定
 */
@Configuration
public class SuperCsvConfig {

    /**
     * SuperCsv用Beanファクトリを返却する
     * @return SuperCsv用Beanファクトリ
     */
    @Bean
    public SpringBeanFactory springBeanFactory() {
        return new SpringBeanFactory();
    }
}
