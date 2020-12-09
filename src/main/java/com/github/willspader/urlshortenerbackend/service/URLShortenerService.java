package com.github.willspader.urlshortenerbackend.service;

import com.github.willspader.urlshortenerbackend.domain.BusinessError;
import com.github.willspader.urlshortenerbackend.dto.URLShortenerDTO;
import com.github.willspader.urlshortenerbackend.entity.URLShortener;
import com.github.willspader.urlshortenerbackend.exception.BusinessException;
import com.github.willspader.urlshortenerbackend.repository.URLShortenerRepository;
import com.github.willspader.urlshortenerbackend.service.validator.URLShortenerValidator;
import com.github.willspader.urlshortenerbackend.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import com.github.willspader.urlshortenerbackend.service.validator.URLShortenerValidator.*;

@Service
public class URLShortenerService {

    private final static Logger LOGGER = LoggerFactory.getLogger(URLShortenerService.class);
    
    private final URLShortenerRepository repository;

    public URLShortenerService(URLShortenerRepository repository) {
        this.repository = repository;
    }

    public Mono<URLShortenerDTO> saveURL(Mono<URLShortenerDTO> urlShortenerDTO) {
        return urlShortenerDTO
                .flatMap(dto -> {
                    ValidationResult validation = URLShortenerValidator.isValidURL().apply(dto);
                    if (validation != ValidationResult.SUCCESS) {
                        throw new IllegalStateException(validation.name());
                    }

                    if (dto.getCustomURL().isEmpty()) {
                        dto.setCustomURL(StringUtil.createRandomCode());
                    }

                    return Mono.just(new URLShortener(dto.getOriginalURL(), dto.getCustomURL().get()));
                })
                .flatMap(entity ->
                        repository.save(entity)
                                .doOnNext(e -> LOGGER.debug("URLShortener entity was saved - _id: {}", e.getCustomURL()))
                                .onErrorResume(DuplicateKeyException.class, e -> Mono.error(new BusinessException(BusinessError.CUSTOM_URL_ALREADY_EXISTS)))
                )
                .flatMap(entity -> Mono.just(toDTO(entity)));
    }

    public Mono<URLShortenerDTO> getByCustomURL(String shortURL) {
        return repository.findById(shortURL)
                .flatMap(entity -> Mono.just(toDTO(entity)));
    }

    private URLShortenerDTO toDTO(URLShortener urlShortener) {
        return new URLShortenerDTO(urlShortener.getOriginalURL(), urlShortener.getCustomURL());
    }

}
