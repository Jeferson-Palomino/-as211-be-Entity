package com.example.t07_entidadescoperantes.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Collections;

@Component
@Configuration
public class WebClientCustomConfig {
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.setAllowedOrigins(Collections.singletonList("http://localhost:4200")); // Permitir solicitudes desde http://localhost:4200
            configuration.setAllowedMethods(Collections.singletonList("*")); // Permitir todos los métodos HTTP (GET, POST, PUT, DELETE, etc.)
            configuration.setAllowedHeaders(Collections.singletonList("*")); // Permitir todas las cabeceras
            configuration.setAllowCredentials(true); // Permitir credenciales (por ejemplo, cookies)

            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", configuration);

            return source;
        }

        @Bean
        public CorsFilter corsFilter() {
            return new CorsFilter(corsConfigurationSource());
        }
    }
