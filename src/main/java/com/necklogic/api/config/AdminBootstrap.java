package com.necklogic.api.config;

import com.necklogic.api.model.User;
import com.necklogic.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdminBootstrap {

    @Value("${app.bootstrap-admin-email:}")
    private String bootstrapAdminEmail;

    @Bean
    CommandLineRunner promoteBootstrapAdmin(UserRepository userRepository) {
        return args -> {
            if (bootstrapAdminEmail == null || bootstrapAdminEmail.isBlank()) {
                return;
            }

            User user = userRepository.findByEmail(bootstrapAdminEmail);
            if (user != null && !user.isAdmin()) {
                user.setAdmin(true);
                userRepository.save(user);
            }
        };
    }
}
