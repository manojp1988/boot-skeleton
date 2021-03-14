package com.example.boot.config.security;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CORSFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String origin = "http://localhost:4200";

        response.addHeader("Access-Control-Allow-Origin", origin);
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "PUT, POST, GET, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Origin, Accept,X-Auth-Token, X-Refresh-Token, X-Requested-With, Authorization, Cache-control, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, X-Preview-Url");
        response.addHeader("Access-Control-Max-Age", "3600");
        response.addHeader("Access-Control-Expose-Headers", "Authorization, filename, X-Refresh-Token");

        filterChain.doFilter(request, response);

    }
}
