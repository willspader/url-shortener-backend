package com.github.willspader.urlshortenerbackend.dto;

import java.util.Optional;

public class URLShortenerDTO {

    private final String originalURL;
    private String customURL;

    public URLShortenerDTO(String originalURL, String customURL) {
        this.originalURL = originalURL;
        this.customURL = customURL;
    }

    public String getOriginalURL() {
        return originalURL;
    }

    public void setCustomURL(String customURL) {
        this.customURL = customURL;
    }

    public Optional<String> getCustomURL() {
        return Optional.ofNullable(customURL);
    }

    @Override
    public String toString() {
        return "URLShortener{" +
                "originalURL='" + originalURL + '\'' +
                ", customURL='" + customURL + '\'' +
                '}';
    }

}
