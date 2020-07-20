package com.github.willspader.urlshortenerbackend.util;

import java.security.SecureRandom;
import java.util.stream.Collectors;

public class StringUtil {

    private static final String CHARS_ALLOWED = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int CODE_LENGTH = 8;

    public static String createRandomCode() {
        return new SecureRandom()
                .ints(CODE_LENGTH, 0, CHARS_ALLOWED.length())
                .mapToObj(CHARS_ALLOWED::charAt)
                .map(Object::toString)
                .collect(Collectors.joining());
    }

}
