package app.backend.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/*
 * PasswordEncoderConfig class is used to configure the PasswordEncoder bean
 * PasswordEncoder bean is used to encode the passwords
 * */
@Configuration
public class PasswordEncoderConfig {

    /**
     * PasswordEncoder bean is used to encode the passwords
     *
     * @return PasswordEncoder bean
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
