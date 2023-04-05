package com.example.click_up.security;

import com.example.click_up.enums.SystemRole;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

@Component
public class JwtProvider {
    private static final String secretKey = Base64.getEncoder().encodeToString("secret".getBytes());
    private static final int tokenLiveTime = 1000 * 3600 * 24 * 7;

    public String encode(String username) {
        return Jwts
                .builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + tokenLiveTime))
                .setIssuer("Click up")
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .setSubject(username)
                .compact();
    }

    public String decodeToken(String token) {

        try {

            return Jwts
                    .parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            return null;
        }


    }
}
