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
                            "text": "Para dominar o braço, comece decorando as cordas E (Mizona) e A. Veja as notas naturais destacadas.",
                            "imageUrl": "https://necklogic.com/assets/anchors.png",
                            "fretboardConfig": {
                                "frets": 12,
                                "highlightNotes": ["C", "D", "E", "F", "G", "A", "B"]
                            }
                        },
                        {
                            "type": "DRILL",
                            "title": "Encontre o A",
                            "question": "Toque a nota A na corda E (Mizona).",
                            "targetNote": "A",
                            "fretboardConfig": {
                                "frets": 12
                            }
                        }
                    ]
                """;

                String contentOctaves = """
                    [
                        {
                            "type": "THEORY",
                            "title": "A Forma de Oitava",
                            "text": "Encontre a nota raiz na corda E (6ª corda) e pule duas cordas e duas casas para encontrar sua oitava.",
                            "fretboardConfig": {
                                "frets": 15,
                                "explicitNotes": [
                                    { "string": 6, "fret": 5, "label": "A", "color": "#00D9FF" },
                                    { "string": 4, "fret": 7, "label": "A", "color": "#A1A1AA" }
                                ]
                            }
                        },
                        {
                            "type": "DRILL",
                            "title": "Prática de Oitavas",
                            "question": "Encontre a oitava de G na 4ª corda.",
                            "targetShape": [
                                { "string": 6, "fret": 3 },
                                { "string": 4, "fret": 5 }
                            ],
                            "fretboardConfig": {
                                "frets": 15
                            }
                        }
                    ]
                """;

                String contentPowerChords = """
                    [
                        {
                            "type": "THEORY",
                            "title": "O Power Chord (C5)",
                            "text": "Formado pela Tônica e a 5ª Justa. Veja o shape do C5 a partir da corda A.",
                            "fretboardConfig": {
                                "frets": 12,
                                "explicitNotes": [
                                    { "string": 5, "fret": 3, "label": "C", "color": "#00D9FF" },
                                    { "string": 4, "fret": 5, "label": "G", "color": "#A1A1AA" }
                                ]
                            }
                        },
                        {
                            "type": "DRILL",
                            "title": "Prática: Power Chord",
                            "question": "Monte o shape de C5 na 5ª corda.",
                            "targetShape": [
                                { "string": 5, "fret": 3 },
                                { "string": 4, "fret": 5 }
                            ],
                            "fretboardConfig": {
                                "frets": 12
                            }
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
                    new Module("The Octave Shape", 2, secNavigation, contentOctaves),
                    new Module("Major 3rds", 3, secIntervals, "[]"),
                    new Module("Perfect 5ths (Power Chords)", 4, secIntervals, contentPowerChords),
                    new Module("Understanding Pulse", 5, secRhythm, contentRhythm)
                ));
            }
        };
    }
}