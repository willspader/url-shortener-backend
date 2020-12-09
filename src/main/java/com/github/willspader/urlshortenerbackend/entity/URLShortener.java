package com.github.willspader.urlshortenerbackend.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document("url_shortener")
public class URLShortener {

    @Id
    private final String customURL;

    private final String originalURL;

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
                ", originalURL='" + originalURL + '\'' +
                ", customURL='" + customURL + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        URLShortener that = (URLShortener) o;
        return customURL.equals(that.customURL);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customURL);
    }
}
