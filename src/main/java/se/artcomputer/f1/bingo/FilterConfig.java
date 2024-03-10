package se.artcomputer.f1.bingo;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<HttpsRedirectFilter> httpsRedirectFilterRegistration() {
        FilterRegistrationBean<HttpsRedirectFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new HttpsRedirectFilter());
        registration.addUrlPatterns("/*");
        registration.setOrder(1);
        return registration;
    }
}
