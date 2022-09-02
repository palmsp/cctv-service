package org.palms.cctv.config.properties;

import javax.validation.constraints.Max;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * Thread Pool properties.
 */
@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "async.thread.pool")
public class ThreadPoolProperties {

    @Max(50)
    private Integer corePoolSize = 10;

    @Max(100)
    private Integer maxPoolSize = 50;

    @Max(50)
    private Integer queueCapacity = 50;

    private String threadNamePrefix = "AsyncThread::";
}
