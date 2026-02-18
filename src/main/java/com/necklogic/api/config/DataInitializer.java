package com.necklogic.api.config;

import com.necklogic.api.model.Module;
import com.necklogic.api.model.Section;
import com.necklogic.api.repository.ModuleRepository;
import com.necklogic.api.repository.SectionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    @Transactional
    CommandLineRunner initDatabase(ModuleRepository moduleRepository, SectionRepository sectionRepository) {
        return args -> {
            if (sectionRepository.count() == 0) {
                moduleRepository.deleteAll();

                Section secNavigation = new Section("Navigation & Anchors", "Pare de se perder no braço.", 1);
                Section secIntervals = new Section("Intervals", "A geometria da música.", 2);
                Section secRhythm = new Section("Rhythm & Feel", "Onde colocar cada nota.", 3);

                sectionRepository.saveAll(List.of(secNavigation, secIntervals, secRhythm));

                String contentFindingNotes = """
                    [
                        {
                            "type": "THEORY",
                            "title": "As Âncoras",
                            "text": "Para dominar o braço, comece decorando apenas as cordas E (Mizona) e A.",
                            "imageUrl": "https://necklogic.com/assets/anchors.png"
                        },
                        {
                            "type": "DRILL",
                            "title": "Encontre o A",
                            "question": "Toque a nota A na corda E (Mizona).",
                            "targetNote": "A",
                            "targetString": 6
                        }
                    ]
                """;

                String contentRhythm = """
                    [
                        {
                            "type": "THEORY",
                            "title": "Semínima (Quarter Note)",
                            "text": "A pulsação básica. Uma nota por tempo (1, 2, 3, 4).",
                            "audioUrl": "quarter_note_beat.mp3"
                        },
                        {
                            "type": "RHYTHM_DRILL",
                            "title": "Sinta o tempo",
                            "notation": "4/4",
                            "pattern": ["X", "X", "X", "X"],
                            "tempo": 80
                        }
                    ]
                """;

                moduleRepository.saveAll(List.of(
                    new Module("Finding Natural Notes", 1, secNavigation, contentFindingNotes),
                    new Module("The Octave Shape", 2, secNavigation, "[]"),

                    new Module("Major 3rds", 3, secIntervals, "[]"),
                    new Module("Perfect 5ths (Power Chords)", 4, secIntervals, "[]"),

                    new Module("Understanding Pulse", 5, secRhythm, contentRhythm)
                ));
            }
        };
    }
}