package com.kps.jpa.config;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ThreadPoolConfig {

	@Bean("auditExecutor")
	public Executor auditExecutor() {
		ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
		threadPool.setCorePoolSize(2);
		threadPool.setMaxPoolSize(4);
		threadPool.setQueueCapacity(100);
		threadPool.initialize();
		return threadPool;
	}
}
