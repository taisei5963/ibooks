package com.techcof.ITive.general;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executor;

/**
 * 一般サイトアプリケーション
 */
@EnableAsync
@SpringBootApplication(scanBasePackages = {"com.techcof.ITive.common", "com.techcof.ITive.general"})
public class GeneralApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(GeneralApplication.class, args);
    }

    /**
     * タスクエグゼキューター
     *
     * @return タスクエグゼキューター
     */
    @Bean
    public Executor taskExecutor() {
        SimpleAsyncTaskExecutor executor = new SimpleAsyncTaskExecutor();
        executor.setVirtualThreads(true);
        return executor;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(GeneralApplication.class);
    }
}