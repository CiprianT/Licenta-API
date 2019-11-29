package config;


import entity.UserEntity;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import service.UserService;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;

/**
 * @author rmorea
 */

@Component
public class JwtTokenProvider {

    @Value("${security.jwt.token.secret-key:chan}")
    private String secretKey;

    @Value("${security.jwt.token.expire-length:9600000}")
    private String validityInMilliseconds; // 1h
    private UserService userService;

    @Autowired
    public JwtTokenProvider(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(UserEntity user) {

        Claims claims = Jwts.claims().setSubject(user.getUsername());
        claims.put("id", user.getId());
        claims.put("firstName", user.getFirstName());
        claims.put("lastName", user.getLastName());
        claims.put("email", user.getEmail());

        Date now = new Date();

        Date validity;
        validity =  new Date(now.getTime() + Long.valueOf(validityInMilliseconds));

        String token = Jwts.builder()//
                .setClaims(claims)//
                .setIssuedAt(now)//
                .setExpiration(validity)//
                .signWith(SignatureAlgorithm.HS256, secretKey)//
                .compact();
        UsernamePasswordAuthenticationToken auth = getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(auth);
        return token;
    }

    UsernamePasswordAuthenticationToken getAuthentication(String token) {
        UserDetails userDetails = userService.findByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    private String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            throw new RuntimeException("Expired or invalid JWT token", e);
        }
    }
}
