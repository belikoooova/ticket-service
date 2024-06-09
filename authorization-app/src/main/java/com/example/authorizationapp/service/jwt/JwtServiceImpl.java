package com.example.authorizationapp.service.jwt;

import com.example.authorizationapp.config.JwtConfiguration;
import com.example.authorizationapp.entity.Session;
import com.example.authorizationapp.entity.User;
import com.example.authorizationapp.repository.SessionDao;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
    private static final String ID_CLAIM = "id";
    private static final String EMAIL_CLAIM = "email";
    private static final SignatureAlgorithm ALGORITHM = SignatureAlgorithm.HS256;

    private final SessionDao sessionDao;
    private final JwtConfiguration config;

    @Override
    public String extractUserName(String token) {
        return Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        if (userDetails instanceof User customUserDetails) {
            claims.put(ID_CLAIM, customUserDetails.getId());
            claims.put(EMAIL_CLAIM, customUserDetails.getEmail());
        }
        return generateToken(claims, userDetails);
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    @Transactional
    public boolean isTokenExpired(String token) {
        Session session = sessionDao.findByToken(token);
        return session.getExpires().isBefore(LocalDateTime.now());
    }

    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername()).issuedAt(new Date(System.currentTimeMillis()))
                .expiration(Date.from(
                        LocalDateTime.now()
                                .plus(config.getTokenExpiration())
                                .atZone(ZoneId.systemDefault())
                                .toInstant()
                ))
                .signWith(getSigningKey(), ALGORITHM).compact();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(config.getSecret());
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
