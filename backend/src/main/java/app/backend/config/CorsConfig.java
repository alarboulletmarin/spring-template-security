package app.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

/*
 * CorsConfig class is used to configure the CORS
 * */
@Configuration
public class CorsConfig {

    /**
     * This method is used to configure the CORS
     *
     * @return CorsFilter object
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(List.of("http://localhost:4200"));
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.addExposedHeader("x-app-session-token");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
