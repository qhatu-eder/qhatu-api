package dev.eder.qhatu_api.common.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

// Con Spring Security, el CORS se declara como un bean que Security usa (SecurityConfig: .cors(...))
@Configuration
public class CorsConfig {

    @Value("${app.cors.origen}")
    private String origenPermitido;

    @Bean 
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of(origenPermitido));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        // Para que el navegador pueda leer el nombre del archivo en las descargas de Excel
        config.setExposedHeaders(List.of("Content-Disposition"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", config);
        return source;
    }
    

}
