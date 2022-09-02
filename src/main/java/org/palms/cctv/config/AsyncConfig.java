package org.palms.cctv.config;

import java.util.concurrent.Executor;
import org.palms.cctv.config.properties.ThreadPoolProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Configuration for asynchronous services.
 */
@Configuration
@EnableAsync
@EnableConfigurationProperties(ThreadPoolProperties.class)
public class AsyncConfig {

    /**
     * Thread pool executor configuration
     *
     * @return Executor
     */
    @Bean("threadPoolExecutor")
    public Executor asyncExecutor(ThreadPoolProperties threadPoolProperties) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(threadPoolProperties.getCorePoolSize());
        executor.setMaxPoolSize(threadPoolProperties.getMaxPoolSize());
        executor.setQueueCapacity(threadPoolProperties.getQueueCapacity());
        executor.setThreadNamePrefix(threadPoolProperties.getThreadNamePrefix());
        executor.initialize();
        return executor;
    }
}
