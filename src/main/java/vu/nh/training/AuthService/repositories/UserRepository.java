package vu.nh.training.AuthService.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vu.nh.training.AuthService.repositories.entities.UserApp;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserApp, Integer> {
    Optional<UserApp> getByUsername(String username);
}
