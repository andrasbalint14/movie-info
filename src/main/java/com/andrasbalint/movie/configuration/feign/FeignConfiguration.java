package com.andrasbalint.movie.configuration.feign;

import feign.RequestTemplate;

/**
 * Base FeignConfiguration interface
 *
 * @author abalint
 */
public interface FeignConfiguration {

    /**
     * Get api key name which should be query param key
     *
     * @return ap key name
     */
    String getApiKeyName();

    /**
     * Get api key
     *
     * @return the api key
     */
    String getApiKey();

    /**
     * Extend url with api key
     *
     * @param template the template
     */
    default void extendUrlWithApiKey(RequestTemplate template) {
        template.uri(template.url()
                .concat("%s%s=%s".formatted(getMarker(template.url()), getApiKeyName(), getApiKey())));
    }

    private String getMarker(String url) {
        return url.contains("?") ? "&" : "?";
    }
}
