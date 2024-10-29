package vu.nh.training.AuthService.component.configuration;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import vu.nh.training.AuthService.services.jwtServices.JwtService;

@Configuration
@EnableWebSecurity
@FieldDefaults(makeFinal = true, level = AccessLevel.PACKAGE)
@RequiredArgsConstructor
public class SecurityConfig {
    String[] urlPublic = {"/api/auth/login", "/api/auth/forgot-password", "/api/auth/introspection", "swagger-ui/index.html#/"};

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
                .formLogin(login -> login
                        .loginProcessingUrl("/api/auth/login"))
                .oauth2Login(oauth2 -> oauth2
                        .successHandler(jwtService::loginOauth2Success))
                .logout(logout -> logout
                        .addLogoutHandler((request, response, authentication) ->
                        jwtService.logoutHandler(request, authentication)))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }
}
