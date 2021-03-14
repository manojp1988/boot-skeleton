package com.example.boot.config.security;

import com.example.boot.data.entity.UserData;
import com.example.boot.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private JWTUtil tokenProvider;
    private UserRepository userRepository;

    @Autowired
    public JwtAuthenticationFilter(JWTUtil tokenProvider,
                                   UserRepository userRepository) {
        this.tokenProvider = tokenProvider;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext()
                                                             .getAuthentication();
        if (authentication == null) {
            String jwt = getJwtFromRequest(httpServletRequest);

            if (isValidToken(jwt)) {
                final String username = tokenProvider.getUserIdFromJWT(jwt);
                final UserData userData = userRepository.findByEmail(username);

                if (userData == null) {
                    throw new IllegalArgumentException("User not found " + username);
                }

                UsernamePasswordAuthenticationToken unameAuthentication = new UsernamePasswordAuthenticationToken(
                        userData.getEmail(), "", userData.getGrantedAuthorities());

                unameAuthentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

                SecurityContextHolder.getContext()
                                     .setAuthentication(unameAuthentication);
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private boolean isValidToken(final String token) {
        try {
            return (StringUtils.hasText(token) && tokenProvider.validateToken(token));
        } catch (Exception e) {
            logger.error("Error occurred in validating token " + token, e);
            return false;
        }
    }
}
