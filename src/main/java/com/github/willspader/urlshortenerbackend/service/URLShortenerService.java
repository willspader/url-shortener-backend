package com.github.willspader.urlshortenerbackend.service;

import com.github.willspader.urlshortenerbackend.dto.URLShortenerDTO;
import com.github.willspader.urlshortenerbackend.entity.URLShortener;
import com.github.willspader.urlshortenerbackend.repository.URLShortenerRepository;
import com.github.willspader.urlshortenerbackend.util.StringUtil;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class URLShortenerService {
    
    private final URLShortenerRepository repository;

    public URLShortenerService(URLShortenerRepository repository) {
        this.repository = repository;
    }

    public Mono<URLShortenerDTO> saveURL(Mono<URLShortenerDTO> urlShortenerDTO) {
        return urlShortenerDTO
                .map(urlShortenerDTOMap -> {
                    if (urlShortenerDTOMap.getCustomURL().isEmpty()) {
                        urlShortenerDTOMap.setCustomURL(StringUtil.createRandomCode());
                    }
                    return urlShortenerDTOMap;
                })
                .flatMap(this::toEntity)
                .flatMap(repository::save)
                .flatMap(this::toDTO);
    }

    private Mono<URLShortener> toEntity(URLShortenerDTO urlShortenerDTO) {
        return Mono.just(new URLShortener(
                urlShortenerDTO.getOriginalURL(),
                urlShortenerDTO.getCustomURL().orElse(null)
        ));
    }

    private Mono<URLShortenerDTO> toDTO(URLShortener urlShortener) {
        return Mono.just(new URLShortenerDTO(
                urlShortener.getOriginalURL(),
                urlShortener.getCustomURL()
        ));
    }

}
