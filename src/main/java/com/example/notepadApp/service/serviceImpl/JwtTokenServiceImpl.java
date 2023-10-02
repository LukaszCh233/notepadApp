package com.example.notepadApp.service.serviceImpl;

import com.example.notepadApp.controller.NoteController;
import com.example.notepadApp.entities.User;
import com.example.notepadApp.service.UserService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Service
public class JwtTokenServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenServiceImpl.class);

    UserService userService;
    SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    long TOKEN_EXPIRATION_TIME = 3600000;
@Autowired
    public JwtTokenServiceImpl(UserService userService) {
        this.userService = userService;
    }
    public String generateJwtToken(User user) {

        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME)) // Popraw datę ważności
                .signWith(key)
                .compact();
    }

    public String getUsername(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parse(token);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

}
