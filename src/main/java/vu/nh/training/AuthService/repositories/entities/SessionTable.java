package vu.nh.training.AuthService.repositories.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;


@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SessionTable {
    @Id
    String RefreshToken;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    UserApp userApp;
    String action;
    Timestamp timestamp;
    @Column(length = 15)
    String ipAddress;
    @Column(length = 30)
    String deviceInfo;
}
