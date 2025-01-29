package com.springboot.blog.security;

import com.springboot.blog.exception.BlogApiException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${app.jwt-secret}")
private String jwtSecret;
    @Value("${app-jwt-expiration-milliseconds}")
private long jwtExpirationDate;

    //generate JWT token

    public String generateToken( Authentication authentication){
        String username=authentication.getName();
        Date currentDate=new Date();
        Date expireDate=new Date(currentDate.getTime()+jwtExpirationDate);
        String token= Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(key())
                .compact();
        return  token;

    }
    private Key key(){
       return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    //get username from jwt token
    public String getUsername(String token){
        // Get username from JWT token
            return Jwts.parserBuilder()
                    .setSigningKey(key()) // Use the signing key
                    .build()
                    .parseClaimsJws(token) // Parse the token
                    .getBody() // Get the claims body
                    .getSubject(); // Extract the subject (username)
        }
//validate JWT token
    public boolean validateToken(String token){
        try {
            Jwts.parserBuilder().
                    setSigningKey(key()).build()
                    .parse(token);
            return true;
        }catch (MalformedJwtException e){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Invalid JWT Token");
        }catch (ExpiredJwtException ex){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Expired Jwt Token");
        }catch (UnsupportedJwtException uex){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Unssuported JWt Token");
        }catch (IllegalArgumentException illegalArgumentException){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Jwt claims string is empty");
        }
    }

}
