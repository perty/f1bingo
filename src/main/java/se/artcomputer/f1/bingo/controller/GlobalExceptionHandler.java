package se.artcomputer.f1.bingo.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MissingRequestCookieException.class)
    public ModelAndView handleMissingRequestCookieException(HttpServletRequest request, MissingRequestCookieException ex) {
        // Logga exception eller utför ytterligare åtgärder om så önskas

        // Namnet på den saknade kakan
        String missingCookieName = ex.getCookieName();

        // Skapa och konfigurera ModelAndView för att omdirigera till inloggningssidan
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/login.html");
        modelAndView.addObject("error", "Saknar nödvändig cookie: " + missingCookieName);

        return modelAndView;
    }
}
