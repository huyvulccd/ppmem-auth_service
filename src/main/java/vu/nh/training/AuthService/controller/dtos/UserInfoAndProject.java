package vu.nh.training.AuthService.controller.dtos;

import vu.nh.training.AuthService.component.enums.RoleUser;

public record UserInfoAndProject(
    String username,
    String password,
    RoleUser roleUser,

    String projectId
) {

}
