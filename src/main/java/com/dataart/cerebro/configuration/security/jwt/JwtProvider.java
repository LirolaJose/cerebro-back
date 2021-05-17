package com.dataart.cerebro.configuration.security.jwt;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JwtProvider {
    private final Map<String, String> emailsAndTokensMap = new HashMap<>();

    @Value("$(jwt.secret)")
    private String jwtSecret;

    public String generateToken(String email) {
        Date date = Date.from(LocalDate.now().plusDays(15).atStartOfDay(ZoneId.systemDefault()).toInstant());

        String token = Jwts.builder()
                .setSubject(email)
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();

        emailsAndTokensMap.put(email, token);
        return token;
    }

    public boolean validateToken(String token) {
        try {
            if (ObjectUtils.isEmpty(token)) {
                return false;
            }
            String userLogin = getLoginFromToken(token);
            String value = emailsAndTokensMap.get(userLogin);
            if (ObjectUtils.isEmpty(value) || !value.equals(token)) {
                log.error("Invalid token");
                return false;
            }
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            log.error("Token expired");
        } catch (UnsupportedJwtException unsEx) {
            log.error("Unsupported jwt");
        } catch (MalformedJwtException mjEx) {
            log.error("Malformed jwt");
        } catch (SignatureException sEx) {
            log.error("Invalid signature");
        } catch (Exception e) {
            log.error("Unexpected token");
        }
        return false;
    }

    public String getLoginFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public void logout(String email) {
        emailsAndTokensMap.remove(email);
    }
}