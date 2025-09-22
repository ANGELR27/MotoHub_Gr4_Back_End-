package com.motohub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

import com.motohub.security.JwtAuthenticationFilter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.http.HttpMethod;

@Configuration
@EnableWebSecurity  // Habilita la seguridad en Spring
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtFilter) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // Desactiva CSRF ya que estamos usando JWT
                .cors(cors -> {})  // Habilita CORS
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // Sin sesiones
                .authorizeHttpRequests(auth -> auth
                        // 🔓 Endpoints públicos
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/admin/**").permitAll()
                        .requestMatchers("/user/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/products/**").permitAll()   // Permitir listar productos
                        .requestMatchers(HttpMethod.POST, "/products").permitAll()    // Permitir crear productos
                        // 🔒 El resto requiere autenticación
                        .anyRequest().authenticated()
                )
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))  // Desactiva las cabeceras de seguridad
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);  // Filtro JWT antes del de autenticación

        return http.build();
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOriginPatterns(Arrays.asList("*"));  // Permite todos los orígenes
        config.setAllowedHeaders(Arrays.asList("*"));  // Permite todos los headers
        config.setExposedHeaders(Arrays.asList("Authorization"));  // Exponer el header Authorization
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));  // Métodos permitidos

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);  // Aplica la configuración a todas las rutas

        return new CorsFilter(source);  // Retorna el filtro CORS
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Encriptación de contraseñas con BCrypt
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();  // Proveedor de autenticación de Spring Security
    }
}
