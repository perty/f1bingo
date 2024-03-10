package se.artcomputer.f1.bingo;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


import java.io.IOException;

@Component
public class HttpsRedirectFilter implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(HttpsRedirectFilter.class);
    public static final String X_FORWARDED_PROTO = "X-Forwarded-Proto";

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String header = request.getHeader(X_FORWARDED_PROTO);
        if (header != null && !header.startsWith("https")) {
            StringBuffer requestURL = request.getRequestURL();
            String httpsUrl = "https:" + requestURL.substring(requestURL.indexOf(":") + 1);
            if (request.getQueryString() != null) {
                httpsUrl += "?" + request.getQueryString();
            }
            LOG.info("Redirecting to HTTPS {}", httpsUrl);
            response.sendRedirect(httpsUrl);
            return;
        }

        chain.doFilter(request, response);
    }
}
