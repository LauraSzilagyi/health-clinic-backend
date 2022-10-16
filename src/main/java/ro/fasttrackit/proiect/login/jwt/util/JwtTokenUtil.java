package ro.fasttrackit.proiect.login.jwt.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ro.fasttrackit.proiect.login.jwt.config.JwtConfiguration;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenUtil {

    private final JwtConfiguration configuration;

    public String getUsernameFromToken(String token) {
        String username = null;

        try {
            username = getClaimFromToken(token, Claims::getSubject);
        } catch (IllegalArgumentException e) {
            log.info("Unable to get JWT Token");
        } catch (ExpiredJwtException e) {
            log.info("Jwt token has expired");
        }
        return username;
    }

    public UserDetails buildUser(String email) {
        return User.builder()
                .username(email)
                .password("doesn't matter")
                .roles("USER")
                .build();
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(configuration.secret()).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }


    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + configuration.validity().toMillis()))
                .signWith(SignatureAlgorithm.HS512, configuration.secret()).compact();
    }

    public boolean tokenIsNotExpired(String token) {
        return !isTokenExpired(token);
    }
}