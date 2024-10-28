package vu.nh.training.AuthService.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vu.nh.training.AuthService.repositories.entities.SessionTable;

@Repository
public interface SessionTableRepository extends JpaRepository<SessionTable, String> {

    boolean existsByRefreshToken(String refreshToken);
}
