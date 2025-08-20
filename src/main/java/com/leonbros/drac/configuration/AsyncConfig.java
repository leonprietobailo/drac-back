package com.leonbros.drac.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class AsyncConfig {

  @Bean(name = "webhookExecutor")
  public ThreadPoolTaskExecutor webhookExecutor() {
    ThreadPoolTaskExecutor exec = new ThreadPoolTaskExecutor();
    exec.setCorePoolSize(4);
    exec.setMaxPoolSize(8);
    exec.setQueueCapacity(500);
    exec.setThreadNamePrefix("webhook-");
    exec.initialize();
    return exec;
  }

  @Bean(name = "mailExecutor")
  public ThreadPoolTaskExecutor mailExecutor() {
    ThreadPoolTaskExecutor exec = new ThreadPoolTaskExecutor();
    exec.setCorePoolSize(4);
    exec.setMaxPoolSize(8);
    exec.setQueueCapacity(500);
    exec.setThreadNamePrefix("mail-");
    exec.initialize();
    return exec;
  }



}
