package info.reinput.global.util;


import info.reinput.global.domain.TokenType;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Date;

@Slf4j
@Component
public class TokenProvider {
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.accessToken.expiration}")
    private Long accessTokenExpiration;

    @Value("${jwt.refreshToken.expiration}")
    private Long refreshTokenExpiration;

    public String generateAccessToken(Long memberId) {
        byte[] signingKey = jwtSecret.getBytes(StandardCharsets.UTF_8);

        return Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(accessTokenExpiration).toInstant()))
                .setSubject(memberId.toString())
                .claim("type", TokenType.ACCESS)
                .compact();

    }

    public Long getMemberIdFromToken(String token) {
        byte[] signingKey = jwtSecret.getBytes(StandardCharsets.UTF_8);

        return Long.parseLong(Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject());
    }

    public String generateRefreshToken(Long memberId) {
        byte[] signingKey = jwtSecret.getBytes(StandardCharsets.UTF_8);

        return Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
                .setExpiration(Date.from(ZonedDateTime.now().plusDays(refreshTokenExpiration).toInstant()))
                .setSubject(memberId.toString())
                .claim("type", TokenType.REFRESH)
                .compact();
    }

    public TokenType getTokenTypeByToken(String token) {
        byte[] signingKey = jwtSecret.getBytes(StandardCharsets.UTF_8);

        return TokenType.valueOf(Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("type", String.class));
    }


    public boolean validateToken(String token) {
        byte[] signingKey = jwtSecret.getBytes(StandardCharsets.UTF_8);

        try {
            Jwts.parserBuilder()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        }
        return false;
    }


}
