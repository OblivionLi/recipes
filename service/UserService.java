package recipes.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import recipes.model.user.User;
import recipes.model.user.UserDetailsImpl;
import recipes.model.user.UserRegisterRequest;
import recipes.repository.UserRepository;

@Service
@Slf4j
public class UserService implements UserDetailsService {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    public ResponseEntity<?> register(UserRegisterRequest request) {
        User existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        String encryptedPassword = passwordEncoder.encode(request.getPassword());

        User user = new User(
                request.getEmail(),
                encryptedPassword
        );

        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found.");
        }

        return new UserDetailsImpl(user);
    }
}
