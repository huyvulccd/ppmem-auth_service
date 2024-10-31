package vu.nh.training.AuthService.repositories.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import vu.nh.training.AuthService.component.enums.RoleUser;
import vu.nh.training.AuthService.component.enums.StatusUser;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserApp implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(length = 18, unique = true, nullable = false)
    String username;
    String email;
    String password;
    RoleUser roleUser;
    @Column(nullable = false)
    StatusUser status;
    Date createdAt;
    Date expiredPassword;
    Date updatedAt;

    @OneToMany(mappedBy = "userApp", cascade = CascadeType.ALL)
    List<SessionTable> sessionTables;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(roleUser);
    }

    @Override
    public boolean isAccountNonExpired() {
        return !this.status.equals(StatusUser.DELETED);
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.status.equals(StatusUser.BLOCKED);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        if (!isEnabled()) {
            return true;
        }
        return this.expiredPassword.after(new Date());
    }

    @Override
    public boolean isEnabled() {
        return this.status.equals(StatusUser.ACTIVE);
    }
}
