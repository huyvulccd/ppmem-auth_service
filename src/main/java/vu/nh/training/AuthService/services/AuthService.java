package vu.nh.training.AuthService.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nimbusds.jose.proc.SecurityContext;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import vu.nh.training.AuthService.component.constants.TimeConstant;
import vu.nh.training.AuthService.component.enums.RoleUser;
import vu.nh.training.AuthService.component.enums.StatusUser;
import vu.nh.training.AuthService.controller.dtos.UserInfoAndProject;
import vu.nh.training.AuthService.controller.dtos.requests.LoginRequest;
import vu.nh.training.AuthService.controller.dtos.responses.JwtResponse;
import vu.nh.training.AuthService.repositories.UserRepository;
import vu.nh.training.AuthService.repositories.entities.UserApp;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PACKAGE)
public class AuthService {
    UserRepository userRepository;
    AuthenticationManager authenticationManager;
    JwtService jwtService;
    PasswordEncoder passwordEncoder;

    public JwtResponse verifierAccount(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.username(), request.password()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        String accessToken = jwtService.generateAccessToken(authentication);
        String refreshToken = jwtService.generateRefreshToken(authentication);
        return new JwtResponse(accessToken, refreshToken, 60);
    }
}
