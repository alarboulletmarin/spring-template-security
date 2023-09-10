package app.backend.controller;

import app.backend.model.entity.LoginRequest;
import app.backend.model.entity.RegisterRequest;
import app.backend.model.entity.User;
import app.backend.service.AuthService;
import app.backend.service.CookieService;
import app.backend.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * AuthController class is used to handle the authentication requests
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    // Autowire the UserService, AuthService and CookieService beans
    private final UserService userService;
    private final AuthService authService;
    private final CookieService cookieService;

    /**
     * AuthController constructor
     *
     * @param userService   UserService bean
     * @param authService   AuthService bean
     * @param cookieService CookieService bean
     */
    public AuthController(UserService userService, AuthService authService, CookieService cookieService) {
        this.userService = userService;
        this.authService = authService;
        this.cookieService = cookieService;
    }

    /**
     * This method is used to register a new user
     *
     * @param request RegisterRequest object
     * @return ResponseEntity object
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            User user = userService.createUser(request.getUsername(), request.getPassword(), request.getRoleName());
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            e.printStackTrace();  // Ceci imprimera l'exception et sa trace dans la console.
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * This method is used to login a user
     *
     * @param request  LoginRequest object
     * @param response HttpServletResponse object
     * @return ResponseEntity object
     */
    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        try {
            String token = authService.authenticate(request.getUsername(), request.getPassword());

            HttpHeaders header = new HttpHeaders();
            header.add("X-App-Session-Token", token);

            // On se contente d'utiliser un header pour le token
            // On crée un cookie JWT
            // Cookie jwtCookie = cookieService.createJwtCookie(token);
            // response.addCookie(jwtCookie);

            return ResponseEntity.ok().headers(header).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    /**
     * This method is used to logout a user
     *
     * @param response HttpServletResponse object
     * @return ResponseEntity object
     */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        // Utile ici car on utilise le header pour le token et donc lors du logout
        // on surpprimera le localstorage
        // On supprime le cookie JWT
        Cookie jwtCookie = cookieService.deleteJwtCookie();
        response.addCookie(jwtCookie);
        return ResponseEntity.ok().build();
    }
}