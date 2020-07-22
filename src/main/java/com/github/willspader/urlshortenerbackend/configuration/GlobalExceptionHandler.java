package com.github.willspader.urlshortenerbackend.configuration;

import com.github.willspader.urlshortenerbackend.exception.BusinessException;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Optional;

@Component
@Order(-2)
public class GlobalExceptionHandler extends AbstractErrorWebExceptionHandler {

    private static final String UNKNOWN_ERROR = "UNKNOWN";

    public GlobalExceptionHandler(ErrorAttributes errorAttributes,
                                  ResourceProperties resourceProperties,
                                  ApplicationContext applicationContext,
                                  ServerCodecConfigurer serverCodecConfigurer) {
        super(errorAttributes, resourceProperties, applicationContext);
        this.setMessageWriters(serverCodecConfigurer.getWriters());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::formatErrorResponse);
    }

    private Mono<ServerResponse> formatErrorResponse(ServerRequest request) {
        Map<String, Object> errorAttributes = getErrorAttributes(request, false);

        Throwable error = super.getError(request);
        String businessCodeError = determineBusinessCodeError(error);

        if (isBusinessError(error)) {
            errorAttributes.put("code", businessCodeError);
        }

        int status = (int) Optional.ofNullable(errorAttributes.get("status")).orElse(500);
        return ServerResponse.status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(errorAttributes));
    }

    private boolean isBusinessError(Throwable error) {
        return error instanceof BusinessException;
    }

    private String determineBusinessCodeError(Throwable error) {
        if (error instanceof BusinessException) {
            return ((BusinessException) error).getCode();
        } else {
            return UNKNOWN_ERROR;
        }
    }
}
