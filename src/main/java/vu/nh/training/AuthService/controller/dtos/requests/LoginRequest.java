package vu.nh.training.AuthService.controller.dtos.requests;

public record LoginRequest(
        String username,
        String password
) {
}
