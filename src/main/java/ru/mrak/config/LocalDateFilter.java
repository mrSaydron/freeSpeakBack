package ru.mrak.config;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.ZonedDateTime;

@Component
@RequiredArgsConstructor
public class LocalDateFilter implements Filter {

    private final UserContext userContext;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String localtime = req.getHeader("localtime");
        if (localtime != null) {
            userContext.setLocalTime(ZonedDateTime.parse(localtime));
        }

        chain.doFilter(request, response);
    }
}
