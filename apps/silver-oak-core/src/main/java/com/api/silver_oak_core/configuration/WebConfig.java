package com.api.silver_oak_core.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
public class WebConfig {
  @Bean
  public CorsFilter corsFilter() {
    CorsConfiguration config = new CorsConfiguration();

    config.setAllowCredentials(true);
    config.addAllowedOrigin("http://localhost:4200");

    config.setAllowedHeaders(Arrays.asList(
      "Authorization", // Authorization → for tokens (JWT, OAuth, etc.)
      "Cache-Control", // Cache-Control → browser cache behavior
      "Content-Type", // Content-Type → like application/json
      "X-Requested-With", // X-Requested-With → for Ajax requests (especially legacy)
      "Accept", // Accept → what response formats are acceptable (application/json, etc.)
      "Origin" // Origin → original domain of request (used internally)
    ));

    config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "HEAD"));

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);

    return new CorsFilter(source);
  }
}
