package com.example.notepadApp.config;

import com.example.notepadApp.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
@Service
public class HelpJwt {
    public static final long JWT_TOKEN_VALIDITY=5*60*60;


    public String getUserNameFromToken(String token) {
        return getClaimFromToken(token,Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token,Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims=getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Key GetSigninKey() {
        byte[] key= Decoders.BASE64.decode("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");

        return Keys.hmacShaKeyFor(key);

    }

    private Claims getAllClaimsFromToken(String token) {

        return Jwts.parserBuilder().setSigningKey(GetSigninKey()).build().parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration=getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(User user) {

        Map<String,Object> claims=new HashMap<>();

        return generateToken(claims,user.getEmail());

    }

    private String generateToken(Map<String,Object> claims,String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+JWT_TOKEN_VALIDITY*1000))
                .signWith(GetSigninKey(), SignatureAlgorithm.HS512).compact();
    }

    public Boolean validateToken(String token,UserDetails userDetails) {
        final String username=getUserNameFromToken(token);

        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));

    }

}

