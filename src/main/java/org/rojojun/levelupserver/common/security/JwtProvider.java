package org.rojojun.levelupserver.common.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtProvider {

    @Value("${secret-key}")
    private String secretKey;
//
//    public String create(String userId, String role) {
//        Date expired = Date.from(Instant.now().plus(1, ChronoUnit.HOURS));
//        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
//
//        String token = Jwts.builder()
//                .signWith(key, SignatureAlgorithm.HS256)
//                .setSubject(userId)
//                .claim("role", role)
//                .setIssuedAt(new Date())
//                .setExpiration(expired)
//                .compact();
//
//        return token;
//    }
//
//    public String validate(String token) {
//        String subject = null;
//
//        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
//
//        try {
//            subject = Jwts.parser()
//                    .setSigningKey(key)
//                    .build()
//                    .parseClaimsJws(token)
//                    .getBody()
//                    .getSubject();
//
//        } catch (Exception exception) {
//            exception.printStackTrace();
//            return null;
//        }
//
//        System.out.println(subject);
//
//        return subject;
//    }

    @Value("${jwt.expiration-ms}")
    private long tokenValidityInMilliseconds;

    private Key key;

    @PostConstruct
    protected void init() {
        key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    // JWT 생성
    public String createToken(String subject, String role) {
        Claims claims = Jwts.claims().setSubject(subject).build(); // subject는 보통 email 사용
        claims.put("role", role); // 사용자 권한 정보 추가
        Date now = new Date();
        Date validity = new Date(now.getTime() + tokenValidityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // JWT 검증 및 정보 추출 (필요시 사용)
    public String validate(String token) {
        String subject = null;

        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        try {
            subject = Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();

        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }

        System.out.println(subject);

        return subject;
    }
}