package vu.nh.training.AuthService.controller.dtos.requests;

import org.springframework.validation.annotation.Validated;
import vu.nh.training.AuthService.component.enums.RoleUser;

@Validated
public record RegisterRequest(
        String username,
        String password,
        String email,
        String phoneNumber,
        String address,
        String identification,
        Byte typeIdentification,
        RoleUser roleUser
) {
}
/*
*
* This class represents a request to register a new user.
*
* @param username - The username of the new user.
* @param password - The password of the new user.
* @param email - The email of the new user.
* */