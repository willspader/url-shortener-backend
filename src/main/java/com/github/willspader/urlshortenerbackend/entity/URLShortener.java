package com.github.willspader.urlshortenerbackend.entity;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("URL_SHORTENER")
public class URLShortener {

    private final String originalURL;
    @Indexed(unique = true)
    private final String customURL;

    public URLShortener(String originalURL, String customURL) {
        this.originalURL = originalURL;
        this.customURL = customURL;
    }

    public String getOriginalURL() {
        return originalURL;
    }

    public String getCustomURL() {
        return customURL;
    }

    @Override
    public String toString() {
        return "URLShortener{" +
                "originalURL='" + originalURL + '\'' +
                ", customURL='" + customURL + '\'' +
                '}';
    }
}
