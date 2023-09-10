package app.backend.config.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
 * JwtTokenFilter class is used to intercept the incoming requests
 * and validate the JWT token
 * */
public class JwtTokenFilter extends OncePerRequestFilter {

    // Autowire the JwtTokenProvider bean
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * JwtTokenFilter constructor
     *
     * @param jwtTokenProvider JwtTokenProvider bean
     */
    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * This method is used to intercept the incoming requests
     * and validate the JWT token
     *
     * @param request     the incoming request
     * @param response    the response
     * @param filterChain the filter chain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        String token = getTokenFromRequest(request);

        if (token != null && jwtTokenProvider.validateToken(token)) {
            Authentication auth = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request, response);
    }

    /**
     * This method is used to get the JWT token from the request
     *
     * @param request the incoming request
     * @return JWT token
     */
    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}
