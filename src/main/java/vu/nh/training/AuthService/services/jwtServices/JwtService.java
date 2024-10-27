package vu.nh.training.AuthService.services.jwtServices;

import org.springframework.security.core.Authentication;

public class JwtService {
    public void saveRefreshToken(String name, String refreshToken) {

    }

    public String generateToken(Authentication authentication) {
        return null;
    }

    public String generateRefreshToken(Authentication authentication) {
        return null;
    }

    public void deleteRefreshToken(String name, String refreshToken) {

    }
}
