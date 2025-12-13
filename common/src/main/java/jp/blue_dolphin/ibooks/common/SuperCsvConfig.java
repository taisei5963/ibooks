package jp.blue_dolphin.ibooks.common;

import com.github.mygreen.supercsv.builder.SpringBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SuperCsv用BeanFactoryのコンテナ設定
 */
@Configuration
public class SuperCsvConfig {
    /**
     * SuperCsv用BeanFactoryを返却する
     *
     * @return SuperCsv用BeanFactory
     */
    @Bean
    public SpringBeanFactory springBeanFactory() {
        return new SpringBeanFactory();
    }
}
