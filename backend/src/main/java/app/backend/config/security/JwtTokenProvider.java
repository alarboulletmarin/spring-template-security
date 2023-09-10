package app.backend.config.security;

import app.backend.service.CustomUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.List;

/*
 * JwtTokenProvider class is used to generate and validate JWT tokens
 * */
public class JwtTokenProvider {

    // Secret key used to generate and validate JWT tokens
    private final String secretKey;

    // Token validity in milliseconds
    private final long tokenValidity;

    // Autowire the CustomUserDetailsService bean
    private final CustomUserDetailsService userDetailsService;

    /**
     * JwtTokenProvider constructor
     *
     * @param secretKey          Secret key used to generate and validate JWT tokens
     * @param tokenValidity      Token validity in milliseconds
     * @param userDetailsService CustomUserDetailsService bean
     */
    public JwtTokenProvider(String secretKey, long tokenValidity, CustomUserDetailsService userDetailsService) {
        this.secretKey = secretKey;
        this.tokenValidity = tokenValidity;
        this.userDetailsService = userDetailsService;
    }

    /**
     * This method is used to generate JWT token
     *
     * @param username Username
     * @param roles    User roles
     * @return JWT token
     */
    public String createToken(String username, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", roles);

        Date now = new Date();
        Date validity = new Date(now.getTime() + tokenValidity);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    /**
     * This method is used to get Authentication object from JWT token
     *
     * @param token JWT token
     * @return Authentication object
     */
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    /**
     * This method is used to get username from JWT token
     *
     * @param token JWT token
     * @return Username
     */
    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * This method is used to validate JWT token
     *
     * @param token JWT token
     * @return true if token is valid, false otherwise
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
