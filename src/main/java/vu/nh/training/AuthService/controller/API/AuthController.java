package vu.nh.training.AuthService.controller.API;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import vu.nh.training.AuthService.controller.dtos.requests.LoginRequest;
import vu.nh.training.AuthService.controller.dtos.requests.RegisterRequest;
import vu.nh.training.AuthService.controller.dtos.responses.TokenJWT;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<TokenJWT> login(@RequestBody LoginRequest request) {
        // Implement login logic here
        String tokenRF = "todo";

        return ResponseEntity.ok(new TokenJWT("", "", 0));
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
