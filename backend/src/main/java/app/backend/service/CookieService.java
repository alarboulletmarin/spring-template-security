package app.backend.service;

import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;

/*
 * CookieService class is used to create and delete the JWT cookie
 * */
@Service
public class CookieService {

    /**
     * This method is used to create the JWT cookie
     *
     * @param token JWT token
     * @return JWT cookie
     */
    public Cookie createJwtCookie(String token) {
        Cookie jwtCookie = new Cookie("JWT_TOKEN", token);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setMaxAge(60 * 60 * 24);
        jwtCookie.setPath("/");
        jwtCookie.setSecure(true);
        return jwtCookie;
    }

    /**
     * This method is used to delete the JWT cookie
     *
     * @return JWT cookie
     */
    public Cookie deleteJwtCookie() {
        Cookie jwtCookie = new Cookie("JWT_TOKEN", null);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setMaxAge(0);
        jwtCookie.setPath("/");
        jwtCookie.setSecure(true);
        return jwtCookie;
    }
}
