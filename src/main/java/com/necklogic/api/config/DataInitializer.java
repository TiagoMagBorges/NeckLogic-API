package com.necklogic.api.config;

import com.necklogic.api.model.Module;
import com.necklogic.api.repository.ModuleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(ModuleRepository moduleRepository) {
        return args -> {
            if (moduleRepository.count() == 0) {
                moduleRepository.saveAll(Arrays.asList(
                    new Module(null, "Finding Notes", 1),
                    new Module(null, "Major Scales", 2),
                    new Module(null, "Intervals", 3),
                    new Module(null, "Chord Construction", 4),
                    new Module(null, "Minor Scales", 5),
                    new Module(null, "Modes", 6),
                    new Module(null, "Arpeggios", 7),
                    new Module(null, "Advanced Harmony", 8)
                ));
                System.out.println("✅ Módulos da trilha criados com sucesso!");
            }
        };
    }
}