package com.mouridedev.security_token;

import com.mouridedev.security_token.entities.User;
import com.mouridedev.security_token.enums.Role;
import com.mouridedev.security_token.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SecurityTokenApplication implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    public static void main(final String[] args) {
        SpringApplication.run(SecurityTokenApplication.class, args);
    }

    @Override
    public void run(final String... args) {
        final User admin = userRepository.findByRole(Role.ADMIN);
        if (admin == null) {
            final User user = User.builder()
                    .firstname("Admin")
                    .secondname("Admin")
                    .email("admin@gmail.com")
                    .password(new BCryptPasswordEncoder().encode("admin"))
                    .role(Role.ADMIN)
                    .build();
            userRepository.save(user);
        }
    }
}
