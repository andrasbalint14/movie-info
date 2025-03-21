package com.andrasbalint.movie.configuration.feign;

import feign.RequestInterceptor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

/**
 * OMDB feign config
 *
 * @author abalint
 */
@Getter
public class OmdbFeignConfiguration implements FeignConfiguration {

    @Value("${omdb.api.key}")
    private String apiKey;

    @Bean(name = "OmdbFeignConfig")
    public RequestInterceptor requestInterceptor() {
        return this::extendUrlWithApiKey;
    }

    @Override
    public String getApiKeyName() {
        return "apikey";
    }
}
