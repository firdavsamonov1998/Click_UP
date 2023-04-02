package com.example.click_up.security;

import com.example.click_up.enums.SystemRole;
import io.jsonwebtoken.*;

import java.util.Date;

public class JwtProvider {
    private static final String secretKey = "mazgiTopSecret123!";
    private static final int tokenLiveTime = 1000 * 3600 * 24 * 7;

    public static String encode(String username, SystemRole role) {
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setIssuedAt(new Date());
        jwtBuilder.signWith(SignatureAlgorithm.HS512, secretKey);

        jwtBuilder.claim("username", username);

        jwtBuilder.claim("role", role);

        jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + (tokenLiveTime)));
        jwtBuilder.setIssuer("DoniyorShifo klinika");
        return jwtBuilder.compact();
    }

    public static String decodeToken(String token) {

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
