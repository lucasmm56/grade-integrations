package br.edu.imepac.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) throws Exception{
        http
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration corsConfiguration = new CorsConfiguration();
                    corsConfiguration.setAllowedOrigins(Collections.singletonList("*"));
                    corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
                    corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
                    return corsConfiguration;
                }));
        http.csrf(csrf -> csrf.disable());
        http.authorizeExchange(path -> path.pathMatchers("/auth/**", "/api/**").permitAll())
                .authorizeExchange(any -> any.anyExchange().authenticated());
        http.oauth2Login(Customizer.withDefaults()).oauth2Client(Customizer.withDefaults());
        return http.build();
    }
}
