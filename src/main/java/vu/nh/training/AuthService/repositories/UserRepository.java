package vu.nh.training.AuthService.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vu.nh.training.AuthService.controller.dtos.UserInfoAndProject;
import vu.nh.training.AuthService.repositories.entities.UserApp;

@Repository
public interface UserRepository extends JpaRepository<UserApp, Integer>{
    Optional<UserApp> getByUsername(String username);
    
    @Query(value = "SELECT " +
    "user_app.username, user_app.role_user " +
    "LEFT JOIN access_project ON user_app.id = access_project.user_id " +
    "FROM user_app " +
    "WHERE username = :username", nativeQuery = true)
    Optional<UserInfoAndProject> getByUserName(@Param("username") String username);
}
