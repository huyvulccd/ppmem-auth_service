package vu.nh.training.AuthService.controller.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenJwtResponse {
    private String token;
    private String refreshToken;
    private Integer expiresIn;
}
