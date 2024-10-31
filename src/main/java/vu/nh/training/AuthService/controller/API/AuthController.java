package vu.nh.training.AuthService.controller.API;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vu.nh.training.AuthService.controller.dtos.requests.LoginRequest;
import vu.nh.training.AuthService.controller.dtos.requests.ResetPasswordRequest;
import vu.nh.training.AuthService.controller.dtos.responses.JwtResponse;
import vu.nh.training.AuthService.services.AuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest request) {
        JwtResponse jwtResponse = authService.verifierAccount(request);
        return ResponseEntity.ok(jwtResponse);
        // Return the 401 Unauthorized
    }

    @PostMapping("/introspection")
    public ResponseEntity<JwtResponse> introspection(@RequestHeader("Authorization") String accessToken,
                                                          @RequestHeader("refreshToken") String refreshToken) {
        // Use the authToken for introspection, if needed
        return ResponseEntity.ok(new JwtResponse("", "", 0));
        // Return the 401 Unauthorized
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authToken) {
        // Use the authToken for logging out, if needed
        SecurityContextHolder.clearContext();
        // Return the 401 Unauthorized
        return ResponseEntity.ok("User logged out successfully.");
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody String email ) {
        // Implement forgot password logic here. 404 if email is not existing
        return ResponseEntity.ok("Password reset email sent to " + email);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest newPassword ) {
        // Implement reset password logic here. 401 if token is not valid
        return ResponseEntity.ok("Password reset successfully.");
    }
}
