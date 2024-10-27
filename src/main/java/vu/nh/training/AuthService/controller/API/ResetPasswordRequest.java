package vu.nh.training.AuthService.controller.API;

public record ResetPasswordRequest(
        String oldPassword,
        String newPassword,
        String passwordConfirm

) {
}
