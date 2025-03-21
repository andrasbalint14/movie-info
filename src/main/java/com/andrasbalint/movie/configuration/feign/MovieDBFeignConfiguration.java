package com.andrasbalint.movie.configuration.feign;

import feign.RequestInterceptor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

/**
 * Movie db feign config
 *
 * @author abalint
 */
@Getter
public class MovieDBFeignConfiguration implements FeignConfiguration {

    @Value("${moviedb.api.key}")
    private String apiKey;

    @Bean(name = "MovieDbFeignConfig")
    public RequestInterceptor requestInterceptor() {
        return this::extendUrlWithApiKey;
    }

    @Override
    public String getApiKeyName() {
        return "api_key";
    }
}
