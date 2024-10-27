package vu.nh.training.AuthService.component.enums;

import org.springframework.security.core.GrantedAuthority;

public enum RoleUser implements GrantedAuthority {
    USER,
    ADMIN,
    SUPER_ADMIN;

    @Override
    public String getAuthority() {
        return this.toString();
    }
}
