package gr.aueb.cf.medicalcare.security;

import gr.aueb.cf.medicalcare.model.Role;
import gr.aueb.cf.medicalcare.model.Status;
import gr.aueb.cf.medicalcare.model.User;
import gr.aueb.cf.medicalcare.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class AdminInitializer {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${admin.password}")
    private String adminPassword;

    @PostConstruct
    public void init() {
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode(adminPassword));
            admin.setRole(Role.ADMIN);
            admin.setStatus(Status.APPROVED);
            admin.setEmail("gioldasis@aueb.gr");
            admin.setIsActive(true);
            userRepository.save(admin);
        }
    }
}