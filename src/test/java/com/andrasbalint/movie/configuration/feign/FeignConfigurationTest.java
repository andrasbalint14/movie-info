package com.andrasbalint.movie.configuration.feign;

import feign.RequestTemplate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FeignConfigurationTest {

    @Captor
    private ArgumentCaptor<String> urlCaptor;

    private final FeignConfiguration feignConfiguration = new FeignConfiguration() {
        @Override
        public String getApiKey() {
            return "test-api-key";
        }

        @Override
        public String getApiKeyName() {
            return "api_key";
        }
    };

    @Test
    void extendUrlWithApiKey_whenUrlWithoutQueryParams_shouldAppendWithQuestionMark() {
        // Arrange
        RequestTemplate template = mock(RequestTemplate.class);
        when(template.url()).thenReturn("https://api.example.com/movies");

        // Act
        feignConfiguration.extendUrlWithApiKey(template);

        // Assert
        verify(template).uri(urlCaptor.capture());
        assertEquals("https://api.example.com/movies?api_key=test-api-key", urlCaptor.getValue());
    }

    @Test
    void extendUrlWithApiKey_whenUrlWithQueryParams_shouldAppendWithAmpersand() {
        // Arrange
        RequestTemplate template = mock(RequestTemplate.class);
        when(template.url()).thenReturn("https://api.example.com/movies?search=Inception");

        // Act
        feignConfiguration.extendUrlWithApiKey(template);

        // Assert
        verify(template).uri(urlCaptor.capture());
        assertEquals("https://api.example.com/movies?search=Inception&api_key=test-api-key", urlCaptor.getValue());
    }
}