package vu.nh.training.AuthService.component.configuration;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import vu.nh.training.AuthService.services.JwtService;

@Configuration
@EnableWebSecurity
@FieldDefaults(makeFinal = true, level = AccessLevel.PACKAGE)
@RequiredArgsConstructor
public class SecurityConfig {
    String[] urlPublic = {
            "/api/auth/login",
            "/api/auth/forgot-password",
            "/api/auth/introspection",
            "/swagger-ui/**", // Swagger UI
            "/v3/api-docs/**" };

    JwtService jwtService;
    JwtAuthenticationFilter jwtAuthenticationFilter;
    UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(o -> o
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(author -> author
                        .requestMatchers(urlPublic).permitAll()
                        .anyRequest().authenticated())
                .logout(logout -> logout
                        .addLogoutHandler((request, response, authentication) -> jwtService
                                .logoutHandler(request, authentication))
                        .invalidateHttpSession(true)
                        .clearAuthentication(true))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2Y);
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .build();
    }
}
