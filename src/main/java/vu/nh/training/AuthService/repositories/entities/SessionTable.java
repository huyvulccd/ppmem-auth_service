package vu.nh.training.AuthService.repositories.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;


@Data
@Entity
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
