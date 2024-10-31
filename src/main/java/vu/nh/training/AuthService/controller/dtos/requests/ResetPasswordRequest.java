package vu.nh.training.AuthService.controller.dtos.requests;

public record ResetPasswordRequest(
        String oldPassword,
        String newPassword,
        String passwordConfirm

) {
}
