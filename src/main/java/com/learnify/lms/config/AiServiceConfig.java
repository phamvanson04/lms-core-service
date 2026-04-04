package com.learnify.lms.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Data
@Configuration
@ConfigurationProperties(prefix = "ai-service")
public class AiServiceConfig {
  private String baseUrl;
  private String internalKey;

  @Bean
  public WebClient aiServiceWebClient(WebClient.Builder builder) {
    ExchangeStrategies strategies =
        ExchangeStrategies.builder()
            .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(50 * 1024 * 1024))
            .build();
    WebClient.Builder clientBuilder = builder.baseUrl(baseUrl);
    if (StringUtils.hasText(internalKey)) {
      clientBuilder.defaultHeader("X-Internal-Key", internalKey);
    }
    return clientBuilder.exchangeStrategies(strategies).build();
  }
}
