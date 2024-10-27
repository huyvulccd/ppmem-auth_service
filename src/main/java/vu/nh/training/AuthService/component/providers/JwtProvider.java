package vu.nh.training.AuthService.component.providers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import vu.nh.training.AuthService.services.jwtServices.JwtService;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Component
public class JwtProvider {
    JwtService jwtService;

    public void loginOauth2Success(HttpServletResponse response, Authentication authentication) {
        // Logic xử lý thành công khi login
        String accessToken = jwtService.generateToken(authentication);
        String refreshToken = jwtService.generateRefreshToken(authentication);

        saveJWT(response, authentication, refreshToken, accessToken);
    }

    private void saveJWT(HttpServletResponse response, Authentication authentication, String refreshToken, String accessToken) {
        jwtService.saveRefreshToken(authentication.getName(), refreshToken);

        response.addHeader("Authorization", "Bearer " + accessToken);
        response.addHeader("Refresh-Token", refreshToken);
    }

    public void logoutHandler(HttpServletRequest request, Authentication authentication) {
        String refreshToken = request.getHeader("Refresh-Token");
        jwtService.deleteRefreshToken(authentication.getName(), refreshToken);
    }

    public boolean validateToken(String accessToken) {
        return false;
    }

    public String getUsernameFromToken(String accessToken) {
        return null;
    }

    public boolean checkRefreshToken(String refreshToken) {
        return false;
    }

    public String generateAccessToken(String username) {
        return null;
    }

    public String processNewSession(HttpServletResponse response, String refreshToken) {
        return "";
    }
}
