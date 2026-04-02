package com.learnify.lms.config;

import com.learnify.lms.service.interfaces.IStorageService;
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

  private final IStorageService cloudinaryIStorageService;
  private final IStorageService s3IStorageService;

  @Bean
  @Primary
  public IStorageService storageService() {
    if ("s3".equalsIgnoreCase(storageProvider)) {
      return s3IStorageService;
    }
    return cloudinaryIStorageService;
  }
}
