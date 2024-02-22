package se.artcomputer.f1.bingo.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.util.UriComponentsBuilder;

@ControllerAdvice
public class ExceptionHandling {

    private static final Logger LOG = LoggerFactory.getLogger(ExceptionHandling.class);
    public static final String REDIRECT_URL = "redirectUrl";

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<String> allExceptions(Exception exception) {
        LOG.error("Bad request {}", exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<String> notFound(Exception exception) {
        LOG.info(exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ConflictException.class})
    public ResponseEntity<String> conflict(Exception exception) {
        LOG.info(exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler({UnAuthorizedException.class})
    public String unauthorized(Exception exception) {
        String loginUrl = UriComponentsBuilder.fromUriString("/login.html")
                .queryParam(REDIRECT_URL, exception.getMessage())
                .build().toUriString();
        return "redirect:" + loginUrl;
    }

    @ExceptionHandler(MissingRequestCookieException.class)
    public String handleMissingRequestCookieException(HttpServletRequest request, MissingRequestCookieException exception) {
        String originalUrl = request.getRequestURL().toString();
        String loginUrl = UriComponentsBuilder.fromUriString("/login.html")
                .queryParam(REDIRECT_URL, originalUrl)
                .build().toUriString();

        return "redirect:" + loginUrl;
    }
}

