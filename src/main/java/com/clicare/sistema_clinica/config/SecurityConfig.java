package com.clicare.sistema_clinica.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // ... (bean do PasswordEncoder) ...

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Habilita a configuração de CORS que definimos abaixo
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.GET, "/api/especialidades").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/pacientes/cadastrar").permitAll()
                        .anyRequest().authenticated()
                );
        return http.build();
    }

    /**
     * Bean para configurar a política de CORS (Cross-Origin Resource Sharing).
     * Permite que o frontend (rodando em outra porta/endereço) acesse a API.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Permite que o frontend rodando em localhost:3000 (React) ou 4200 (Angular) acesse a API
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:4200", "http://127.0.0.1:5500"));
        // Permite os métodos HTTP mais comuns
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        // Permite o envio de cabeçalhos comuns, como Authorization e Content-Type
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        // Permite o envio de credenciais (cookies, etc.), importante para autenticação
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Aplica esta configuração para todos os caminhos da nossa API
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}