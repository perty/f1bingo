package se.artcomputer.f1.bingo.exception;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionHandling {

    private static final Logger LOG = LoggerFactory.getLogger(ExceptionHandling.class);
    public static final String MUST_LOGIN = "Du måste logga in för att komma åt sidan";

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

    @ExceptionHandler({UnAuthorizedException.class})
    public ModelAndView unauthorized(Exception exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/login.html");
        modelAndView.addObject("error", exception.getMessage() + ". " + MUST_LOGIN);
        return modelAndView;
    }

    @ExceptionHandler(MissingRequestCookieException.class)
    public ModelAndView handleMissingRequestCookieException(MissingRequestCookieException exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/login.html");
        modelAndView.addObject("error", MUST_LOGIN);

        return modelAndView;
    }
}

