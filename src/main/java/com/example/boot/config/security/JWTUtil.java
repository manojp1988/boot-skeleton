package com.example.boot.config.security;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtil {

    private static final Logger logger = LoggerFactory.getLogger(JWTUtil.class);
    private static final String jwtSecret = "JWTSuperSecretKey123";

    public String generateToken(String userPrincipal, Integer idleTimeOut) {
        idleTimeOut = idleTimeOut * 60 * 1000;

        final Date currentTime = new Date();
        Date expiryDate = new Date(currentTime.getTime() + idleTimeOut);

        return Jwts.builder()
                   .setSubject(userPrincipal)
                   .setIssuedAt(currentTime)
                   .setExpiration(expiryDate)
                   .signWith(SignatureAlgorithm.HS512, jwtSecret)
                   .compact();
    }

    public String getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                            .setSigningKey(jwtSecret)
                            .parseClaimsJws(token)
                            .getBody();

        return claims.getSubject();
    }


    public boolean validateToken(String authToken) {
        try {
            Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        return false;
    }
}
