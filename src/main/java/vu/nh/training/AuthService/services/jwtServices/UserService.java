package vu.nh.training.AuthService.services.jwtServices;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vu.nh.training.AuthService.repositories.UserRepository;
import vu.nh.training.AuthService.repositories.entities.UserApp;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PACKAGE)
@AllArgsConstructor
public class UserService implements UserDetailsService {
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getUserByUsername(username);
    }

    public UserApp getUserByUsername(String username) {
        return userRepository.getByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("User not found with username: " + username));
    }
}
