package com.github.willspader.urlshortenerbackend.controller;

import com.github.willspader.urlshortenerbackend.dto.URLShortenerDTO;
import com.github.willspader.urlshortenerbackend.entity.URLShortener;
import com.github.willspader.urlshortenerbackend.service.URLShortenerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/url-shortener/")
public class URLShortenerController {

    private final URLShortenerService service;

    public URLShortenerController(URLShortenerService service) {
        this.service = service;
    }

    @PostMapping("save-url")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResponseEntity<URLShortenerDTO>> saveURL(@RequestBody Mono<URLShortenerDTO> urlShortenerDTO) {
        return service.saveURL(urlShortenerDTO)
                .map(urlShortener -> new ResponseEntity<>(urlShortener, HttpStatus.OK));
    }

    @GetMapping("{shortURL}")
    public Mono<ResponseEntity<URLShortenerDTO>> getURL(@PathVariable("shortURL") String shortURL) {
        return service.getByCustomURL(shortURL)
                .map(urlShortenerDTO ->  new ResponseEntity<>(urlShortenerDTO, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
