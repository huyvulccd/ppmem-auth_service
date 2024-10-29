package vu.nh.training.AuthService.services;

import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import vu.nh.training.AuthService.controller.dtos.UserInfoAndProject;
import vu.nh.training.AuthService.controller.dtos.requests.LoginRequest;
import vu.nh.training.AuthService.controller.dtos.responses.JwtResponse;
import vu.nh.training.AuthService.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PACKAGE)
public class AuthService {
    UserRepository userRepository;

    public JwtResponse verifierAccount(LoginRequest request) {
//        Optional<UserInfoAndProject> byUserName = userRepository.getByUserName(request.username());

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'verifierAccount'");
    }

}
