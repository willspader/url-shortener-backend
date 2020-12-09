package com.github.willspader.urlshortenerbackend.repository;

import com.github.willspader.urlshortenerbackend.entity.URLShortener;
import org.bson.types.ObjectId;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface URLShortenerRepository extends ReactiveCrudRepository<URLShortener, String> {

}
