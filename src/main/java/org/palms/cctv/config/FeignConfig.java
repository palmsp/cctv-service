package org.palms.cctv.config;

import org.palms.cctv.client.CameraClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for feign clients.
 */
@Configuration
@EnableFeignClients(clients = CameraClient.class)
public class FeignConfig {

}
