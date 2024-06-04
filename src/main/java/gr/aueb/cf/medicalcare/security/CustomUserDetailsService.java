package gr.aueb.cf.medicalcare.security;

import gr.aueb.cf.medicalcare.model.User;
import gr.aueb.cf.medicalcare.repository.UserRepository;
import gr.aueb.cf.medicalcare.service.exception.NotActiveUserException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public CustomUserDetails loadUserByUsername(String username)  {
        User user = new User();
        user = userRepository.findByUsername(username).orElseThrow(()
                -> new UsernameNotFoundException("User not found with username: " + username));
        return user;
    }
}