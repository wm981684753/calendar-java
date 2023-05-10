package com.calendar.utils;

import com.calendar.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtils {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expire}")
    private long expire;

    public String generateToken(User user) {
        Claims claims = Jwts.claims();
        claims.put("userId", user.getId());
        claims.put("open_id", user.getOpenId());

        Date now = new Date();
        Date expiration = new Date(now.getTime() + expire);

        return  Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public String refreshToken(User user) {
        Claims claims = Jwts.claims();
        claims.put("userId", user.getId());
        claims.put("open_id", user.getOpenId());

        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public User getUserFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        User user = new User();
        user.setId((Integer) claims.get("id"));
        user.setOpenId((String) claims.get("open_id"));
        user.setNickName((String) claims.get("nick_name"));
        user.setPhoto((String) claims.get("photo"));

        return user;
    }
}

