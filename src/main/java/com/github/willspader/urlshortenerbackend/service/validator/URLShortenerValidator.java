package com.github.willspader.urlshortenerbackend.service.validator;

import com.github.willspader.urlshortenerbackend.dto.URLShortenerDTO;

import java.util.function.Function;
import java.util.regex.Pattern;

import static com.github.willspader.urlshortenerbackend.service.validator.URLShortenerValidator.ValidationResult.*;

public interface URLShortenerValidator extends Function<URLShortenerDTO, URLShortenerValidator.ValidationResult> {

    Pattern regexValidUrl = Pattern.compile("[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)");

    static URLShortenerValidator isValidURL() {
        return urlShortenerDTO -> regexValidUrl.matcher(urlShortenerDTO.getOriginalURL()).matches() ? SUCCESS : ERROR;
    }

    default URLShortenerValidator and (URLShortenerValidator other) {
        return urlShortenerDTO -> {
            ValidationResult result = this.apply(urlShortenerDTO);
            return result.equals(SUCCESS) ? other.apply(urlShortenerDTO) : result;
        };
    }

    enum ValidationResult {
        SUCCESS,
        ERROR
    }
}
