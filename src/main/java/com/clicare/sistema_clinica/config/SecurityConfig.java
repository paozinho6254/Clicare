package com.clicare.sistema_clinica.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // Ativa a configuração de segurança web
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Desabilita CSRF, pois nossa API será 'stateless' (não usará sessões)
                .csrf(AbstractHttpConfigurer::disable)
                // Define a política de criação de sessão como STATELESS, essencial para APIs REST
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Configura as regras de autorização para as requisições HTTP
                .authorizeHttpRequests(authorize -> authorize
                        // Permite que QUALQUER UM acesse os endpoints de cadastro e login
                        .requestMatchers("/api/pacientes/cadastrar").permitAll()
                        .requestMatchers("/api/login").permitAll() // Futuro endpoint de login
                        // Exige que o usuário esteja autenticado para qualquer outra requisição
                        .anyRequest().authenticated()
                );
        return http.build();
    }
}