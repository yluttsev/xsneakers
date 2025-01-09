package ru.luttsev.authservice.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class JwtService {

    @Value("${token.access.secret}")
    private String accessSecret;

    @Value("${token.access.expiration}")
    private Long accessExpiration;

    @Value("${token.refresh.secret}")
    private String refreshSecret;

    @Value("${token.refresh.expiration}")
    private Long refreshExpiration;

    public String generateAccessToken(String email, List<String> roles, long issuedAt) {
        return JWT.create()
                .withIssuedAt(Instant.ofEpochSecond(issuedAt))
                .withExpiresAt(Instant.ofEpochSecond(issuedAt).plusMillis(accessExpiration))
                .withSubject(email)
                .withClaim("roles", roles)
                .sign(Algorithm.HMAC256(accessSecret));
    }

    public String generateRefreshToken(String email, long issuedAt) {
        return JWT.create()
                .withIssuedAt(Instant.ofEpochSecond(issuedAt))
                .withExpiresAt(Instant.ofEpochSecond(issuedAt).plusMillis(refreshExpiration))
                .withSubject(email)
                .sign(Algorithm.HMAC256(refreshSecret));
    }

    public boolean validateAccessToken(String accessToken) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(accessSecret)).build();
        try {
            verifier.verify(accessToken);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean validateRefreshToken(String refreshToken) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(refreshSecret)).build();
        try {
            verifier.verify(refreshToken);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public long getTokenExpiration(String token) {
        return JWT.decode(token)
                .getExpiresAt()
                .getTime() / 1000;
    }

    public String getTokenSubject(String token) {
        return JWT.decode(token)
                .getSubject();
    }
}
