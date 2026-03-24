package com.learnify.lms.infrastructure.config;

import com.learnify.lms.application.port.output.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@RequiredArgsConstructor
public class StorageConfig {
  @Value("${storage.provider}")
  private String storageProvider;

  private final StorageService cloudinaryStorageService;
  private final StorageService s3StorageService;

  @Bean
  @Primary
  public StorageService storageService() {
    if ("s3".equalsIgnoreCase(storageProvider)) {
      return s3StorageService;
    }
    return cloudinaryStorageService;
  }
}
