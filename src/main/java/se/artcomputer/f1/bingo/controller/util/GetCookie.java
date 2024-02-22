package se.artcomputer.f1.bingo.controller.util;

import org.springframework.http.HttpHeaders;

import java.util.Arrays;

import static se.artcomputer.f1.bingo.domain.AdminService.AUTH_COOKIE;

public class GetCookie {

    public static String getCookie(HttpHeaders headers) {
        String cookiesHeader = headers.getFirst(HttpHeaders.COOKIE) == null ? "" : headers.getFirst(HttpHeaders.COOKIE);
        return Arrays.stream(cookiesHeader.split("; ")).filter(c -> c.startsWith(AUTH_COOKIE))
                .findFirst()
                .map(s -> s.split("=")[1])
                .orElse("");
    }
}
