package com.github.willspader.urlshortenerbackend.service;

import com.github.willspader.urlshortenerbackend.dto.URLShortenerDTO;
import com.github.willspader.urlshortenerbackend.entity.URLShortener;
import com.github.willspader.urlshortenerbackend.repository.URLShortenerRepository;
import com.github.willspader.urlshortenerbackend.service.validator.URLShortenerValidator;
import com.github.willspader.urlshortenerbackend.util.StringUtil;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import com.github.willspader.urlshortenerbackend.service.validator.URLShortenerValidator.*;

@Service
public class URLShortenerService {
    
    private final URLShortenerRepository repository;

    public URLShortenerService(URLShortenerRepository repository) {
        this.repository = repository;
    }

    public Mono<URLShortenerDTO> saveURL(Mono<URLShortenerDTO> urlShortenerDTO) {
        return urlShortenerDTO
                .flatMap(urlShortenerDTOMap -> {
                    ValidationResult validation = URLShortenerValidator.isValidURL().apply(urlShortenerDTOMap);
                    if (validation != ValidationResult.SUCCESS) {
                        throw new IllegalStateException(validation.name());
                    }

                    if (urlShortenerDTOMap.getCustomURL().isEmpty()) {
                        urlShortenerDTOMap.setCustomURL(StringUtil.createRandomCode());
                    }

                    Mono<URLShortener> urlShortener = Mono.just(new URLShortener(urlShortenerDTOMap.getOriginalURL(), urlShortenerDTOMap.getCustomURL().get()));

                    // TODO: REFATORAR METODO ADICIONANDO VALIDACAO SE customURL JA EXISTE

                    return urlShortener;
                })
                .flatMap(repository::save)
                .flatMap(this::toDTO);
    }

    public Mono<URLShortenerDTO> getByCustomURL(String shortURL) {
        return repository.getByCustomURL(shortURL)
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
