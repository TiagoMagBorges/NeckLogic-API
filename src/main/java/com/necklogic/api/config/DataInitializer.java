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

                String contentFretboardBasics = """
                    [
                        {
                            "type": "THEORY",
                            "title": "As Cordas",
                            "text": "O braço do instrumento funciona como um mapa de coordenadas. A organização vertical começa na 1ª corda (mais aguda) até a 6ª corda (mais grave).",
                            "fretboardConfig": {
                                "frets": 0,
                                "explicitNotes": [
                                    { "string": 6, "fret": 0, "label": "6", "color": "#00D9FF" },
                                    { "string": 5, "fret": 0, "label": "5", "color": "#A1A1AA" },
                                    { "string": 4, "fret": 0, "label": "4", "color": "#A1A1AA" },
                                    { "string": 3, "fret": 0, "label": "3", "color": "#A1A1AA" },
                                    { "string": 2, "fret": 0, "label": "2", "color": "#A1A1AA" },
                                    { "string": 1, "fret": 0, "label": "1", "color": "#00D9FF" }
                                ]
                            }
                        },
                        {
                            "type": "DRILL",
                            "title": "Corda Grave",
                            "question": "Selecione a 6ª corda solta.",
                            "targetShape": [{"string": 6, "fret": 0}],
                            "fretboardConfig": {"frets": 5}
                        },
                        {
                            "type": "DRILL",
                            "title": "Corda Aguda",
                            "question": "Selecione a 1ª corda solta.",
                            "targetShape": [{"string": 1, "fret": 0}],
                            "fretboardConfig": {"frets": 5}
                        },
                        {
                            "type": "THEORY",
                            "title": "As Casas e Trastes",
                            "text": "A navegação horizontal ocorre pelas casas (frets). Avançar 1 casa eleva a nota em 1 semitom. As marcações (inlays) nas casas 3, 5, 7 e 9 servem como pontos de referência visual.",
                            "fretboardConfig": {
                                "frets": 12
                            }
                        },
                        {
                            "type": "DRILL",
                            "title": "Cruzando Informações",
                            "question": "Localize a 6ª corda, casa 5 (segunda marcação).",
                            "targetShape": [{"string": 6, "fret": 5}],
                            "fretboardConfig": {"frets": 12}
                        },
                        {
                            "type": "THEORY",
                            "title": "Marcadores Centrais",
                            "text": "As marcações ajudam a visualizar o braço por inteiro. A marcação da casa 7, por exemplo, alinha visualmente as notas em todas as cordas de forma rápida.",
                            "fretboardConfig": {
                                "frets": 12,
                                "highlightNotes": ["B", "F#", "D", "A", "E", "B"]
                            }
                        },
                        {
                            "type": "DRILL",
                            "title": "Localização Rápida",
                            "question": "Navegue diretamente para a 3ª corda, casa 7.",
                            "targetShape": [{"string": 3, "fret": 7}],
                            "fretboardConfig": {"frets": 12}
                        }
                    ]
                """;

                String contentString6Natural = """
                    [
                        {
                            "type": "THEORY",
                            "title": "O Padrão de 1 Tom",
                            "text": "As notas naturais são A, B, C, D, E, F, G. Na guitarra, a distância de 1 Tom equivale a pular 1 casa inteira (avançar 2 casas).",
                            "fretboardConfig": {
                                "frets": 12,
                                "explicitNotes": [
                                    { "string": 6, "fret": 3, "label": "G", "color": "#A1A1AA" },
                                    { "string": 6, "fret": 5, "label": "A", "color": "#00D9FF" },
                                    { "string": 6, "fret": 7, "label": "B", "color": "#A1A1AA" }
                                ]
                            }
                        },
                        {
                            "type": "DRILL",
                            "title": "Nota G",
                            "question": "Localize a nota G na 6ª corda (primeira marcação).",
                            "targetShape": [{"string": 6, "fret": 3}],
                            "fretboardConfig": {"frets": 12}
                        },
                        {
                            "type": "DRILL",
                            "title": "Nota A",
                            "question": "Avance 1 Tom a partir do G para localizar a nota A (segunda marcação).",
                            "targetShape": [{"string": 6, "fret": 5}],
                            "fretboardConfig": {"frets": 12}
                        },
                        {
                            "type": "THEORY",
                            "title": "Distância de Semitom: E e F",
                            "text": "As notas E e F possuem apenas 1 Semitom de distância (casas vizinhas). Como a 6ª corda solta é E, a casa 1 é automaticamente o F.",
                            "fretboardConfig": {
                                "frets": 5,
                                "explicitNotes": [
                                    { "string": 6, "fret": 0, "label": "E", "color": "#A1A1AA" },
                                    { "string": 6, "fret": 1, "label": "F", "color": "#EF4444" }
                                ]
                            }
                        },
                        {
                            "type": "DRILL",
                            "title": "Nota F",
                            "question": "Localize a nota F na 6ª corda.",
                            "targetShape": [{"string": 6, "fret": 1}],
                            "fretboardConfig": {"frets": 12}
                        },
                        {
                            "type": "THEORY",
                            "title": "Distância de Semitom: B e C",
                            "text": "Assim como E e F, as notas B e C distam apenas 1 Semitom. Sabendo que o B está na casa 7, o C estará na casa seguinte.",
                            "fretboardConfig": {
                                "frets": 12,
                                "explicitNotes": [
                                    { "string": 6, "fret": 7, "label": "B", "color": "#A1A1AA" },
                                    { "string": 6, "fret": 8, "label": "C", "color": "#EF4444" }
                                ]
                            }
                        },
                        {
                            "type": "DRILL",
                            "title": "Nota C",
                            "question": "Localize a nota C na 6ª corda.",
                            "targetShape": [{"string": 6, "fret": 8}],
                            "fretboardConfig": {"frets": 12}
                        },
                        {
                            "type": "DRILL",
                            "title": "Dedução de 1 Tom",
                            "question": "Aplicando a regra de 1 Tom a partir do C, localize a nota D.",
                            "targetShape": [{"string": 6, "fret": 10}],
                            "fretboardConfig": {"frets": 12}
                        }
                    ]
                """;

                String contentString6Accidentals = """
                    [
                        {
                            "type": "THEORY",
                            "title": "O Sustenido (#)",
                            "text": "O sustenido eleva a nota em 1 semitom, o que significa avançar 1 casa em direção ao corpo da guitarra.",
                            "fretboardConfig": {
                                "frets": 5,
                                "explicitNotes": [
                                    { "string": 6, "fret": 1, "label": "F", "color": "#A1A1AA" },
                                    { "string": 6, "fret": 2, "label": "F#", "color": "#10B981" }
                                ]
                            }
                        },
                        {
                            "type": "DRILL",
                            "title": "Nota F#",
                            "question": "Encontre o F natural e avance 1 semitom para marcar o F#.",
                            "targetShape": [{"string": 6, "fret": 2}],
                            "fretboardConfig": {"frets": 12}
                        },
                        {
                            "type": "DRILL",
                            "title": "Nota G#",
                            "question": "Encontre a nota G e avance 1 semitom para marcar o G#.",
                            "targetShape": [{"string": 6, "fret": 4}],
                            "fretboardConfig": {"frets": 12}
                        },
                        {
                            "type": "THEORY",
                            "title": "O Bemol (b)",
                            "text": "O bemol abaixa a nota em 1 semitom, o que significa recuar 1 casa em direção à mão (headstock) do instrumento.",
                            "fretboardConfig": {
                                "frets": 12,
                                "explicitNotes": [
                                    { "string": 6, "fret": 7, "label": "B", "color": "#A1A1AA" },
                                    { "string": 6, "fret": 6, "label": "Bb", "color": "#10B981" }
                                ]
                            }
                        },
                        {
                            "type": "DRILL",
                            "title": "Nota Bb",
                            "question": "Encontre o B natural e recue 1 semitom para marcar o Bb.",
                            "targetShape": [{"string": 6, "fret": 6}],
                            "fretboardConfig": {"frets": 12}
                        },
                        {
                            "type": "THEORY",
                            "title": "Enarmonia",
                            "text": "Avançar de G (G#) ou recuar de A (Ab) leva à mesma casa. Enarmonia ocorre quando a mesma posição física possui dois nomes dependendo da escala.",
                            "fretboardConfig": {
                                "frets": 7,
                                "explicitNotes": [
                                    { "string": 6, "fret": 3, "label": "G", "color": "#A1A1AA" },
                                    { "string": 6, "fret": 4, "label": "G#/Ab", "color": "#00D9FF" },
                                    { "string": 6, "fret": 5, "label": "A", "color": "#A1A1AA" }
                                ]
                            }
                        },
                        {
                            "type": "DRILL",
                            "title": "Identificando Gb",
                            "question": "Localize a nota Gb. Dica: parta da nota G natural e recue 1 semitom.",
                            "targetShape": [{"string": 6, "fret": 2}],
                            "fretboardConfig": {"frets": 12}
                        }
                    ]
                """;

                String contentString5Natural = """
                    [
                        {
                            "type": "THEORY",
                            "title": "A Corda A",
                            "text": "A 5ª corda solta é a nota A. As regras de distância se mantêm: 1 Tom para a maioria das notas, e 1 Semitom entre as exceções B-C e E-F.",
                            "fretboardConfig": { "frets": 12, "explicitNotes": [ { "string": 5, "fret": 0, "label": "A", "color": "#00D9FF" } ] }
                        },
                        {
                            "type": "DRILL",
                            "title": "A Corda Solta",
                            "question": "Selecione a 5ª corda solta.",
                            "targetShape": [{"string": 5, "fret": 0}],
                            "fretboardConfig": {"frets": 12}
                        },
                        {
                            "type": "THEORY",
                            "title": "Avançando 1 Tom",
                            "text": "Avançando 1 Tom (2 casas) a partir da corda A solta, localizamos a nota B na casa 2.",
                            "fretboardConfig": { "frets": 12, "explicitNotes": [ { "string": 5, "fret": 2, "label": "B", "color": "#10B981" } ] }
                        },
                        {
                            "type": "DRILL",
                            "title": "Nota C",
                            "question": "Aplicando a distância de 1 Semitom entre B e C, localize o C na 5ª corda.",
                            "targetShape": [{"string": 5, "fret": 3}],
                            "fretboardConfig": {"frets": 12}
                        },
                        {
                            "type": "DRILL",
                            "title": "Nota D",
                            "question": "Avance 1 Tom a partir do C e localize a nota D na 5ª corda.",
                            "targetShape": [{"string": 5, "fret": 5}],
                            "fretboardConfig": {"frets": 12}
                        },
                        {
                            "type": "THEORY",
                            "title": "Notas E e F",
                            "text": "Continuando a escala, encontramos o E na casa 7. Em seguida, aplica-se novamente a regra de 1 Semitom para chegar ao F.",
                            "fretboardConfig": { "frets": 12, "explicitNotes": [ { "string": 5, "fret": 7, "label": "E", "color": "#A1A1AA" }, { "string": 5, "fret": 8, "label": "F", "color": "#EF4444" } ] }
                        },
                        {
                            "type": "DRILL",
                            "title": "Nota F",
                            "question": "Localize a nota F na 5ª corda.",
                            "targetShape": [{"string": 5, "fret": 8}],
                            "fretboardConfig": {"frets": 12}
                        }
                    ]
                """;

                String contentString5Accidentals = """
                    [
                        {
                            "type": "THEORY",
                            "title": "Sustenidos na Corda A",
                            "text": "A lógica de acidentes se mantém de forma universal. Para encontrar a nota C#, basta localizar o C e avançar 1 semitom.",
                            "fretboardConfig": { "frets": 12, "explicitNotes": [ { "string": 5, "fret": 3, "label": "C", "color": "#A1A1AA" }, { "string": 5, "fret": 4, "label": "C#", "color": "#00D9FF" } ] }
                        },
                        {
                            "type": "DRILL",
                            "title": "Nota C#",
                            "question": "Localize o C# na 5ª corda.",
                            "targetShape": [{"string": 5, "fret": 4}],
                            "fretboardConfig": {"frets": 12}
                        },
                        {
                            "type": "DRILL",
                            "title": "Nota Eb",
                            "question": "Localize o E natural (casa 7) e aplique o bemol recuando 1 semitom para encontrar o Eb.",
                            "targetShape": [{"string": 5, "fret": 6}],
                            "fretboardConfig": {"frets": 12}
                        },
                        {
                            "type": "THEORY",
                            "title": "O Bb na Corda 5",
                            "text": "A casa 1 da 5ª corda corresponde ao Bb (ou A#), recuando 1 semitom a partir do B (casa 2) ou avançando a partir do A solto.",
                            "fretboardConfig": { "frets": 12, "explicitNotes": [ { "string": 5, "fret": 1, "label": "Bb/A#", "color": "#10B981" } ] }
                        },
                        {
                            "type": "DRILL",
                            "title": "Nota Bb",
                            "question": "Localize a nota Bb na 5ª corda.",
                            "targetShape": [{"string": 5, "fret": 1}],
                            "fretboardConfig": {"frets": 12}
                        }
                    ]
                """;

                String contentOctaveShape64 = """
                    [
                        {
                            "type": "THEORY",
                            "title": "Oitavas",
                            "text": "Uma oitava é a repetição da mesma nota em uma região mais aguda. O shape visual mais comum parte da 6ª corda e alcança a 4ª corda.",
                            "fretboardConfig": { "frets": 12, "explicitNotes": [ { "string": 6, "fret": 3, "label": "G", "color": "#A1A1AA" }, { "string": 4, "fret": 5, "label": "G", "color": "#00D9FF" } ] }
                        },
                        {
                            "type": "THEORY",
                            "title": "O Shape 6-4",
                            "text": "Para encontrar a oitava de uma nota na 6ª corda: pule a 5ª corda e avance 2 casas na 4ª corda.",
                            "fretboardConfig": { "frets": 12, "explicitNotes": [ { "string": 6, "fret": 5, "label": "A", "color": "#A1A1AA" }, { "string": 4, "fret": 7, "label": "A", "color": "#10B981" } ] }
                        },
                        {
                            "type": "DRILL",
                            "title": "Oitava de A",
                            "question": "Selecione as duas notas que formam a oitava de A (Tônica na 6ª corda, casa 5).",
                            "targetShape": [{"string": 6, "fret": 5}, {"string": 4, "fret": 7}],
                            "fretboardConfig": {"frets": 12}
                        },
                        {
                            "type": "DRILL",
                            "title": "Oitava de C",
                            "question": "Encontre o C na 6ª corda e marque sua respectiva oitava na 4ª corda.",
                            "targetShape": [{"string": 6, "fret": 8}, {"string": 4, "fret": 10}],
                            "fretboardConfig": {"frets": 12}
                        },
                        {
                            "type": "DRILL",
                            "title": "Encontrando a Tônica",
                            "question": "Dado que a 4ª corda, casa 2, é um E, encontre esta nota e selecione o E de origem na 6ª corda solta.",
                            "targetShape": [{"string": 6, "fret": 0}, {"string": 4, "fret": 2}],
                            "fretboardConfig": {"frets": 12}
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
                    new Module("O Braço do Instrumento", 1, secNavigation, contentFretboardBasics),
                    new Module("Corda 6: Notas Naturais", 2, secNavigation, contentString6Natural),
                    new Module("Corda 6: Acidentes", 3, secNavigation, contentString6Accidentals),
                    new Module("Corda 5: Notas Naturais", 4, secNavigation, contentString5Natural),
                    new Module("Corda 5: Acidentes", 5, secNavigation, contentString5Accidentals),
                    new Module("A Oitava (6-4)", 6, secNavigation, contentOctaveShape64),
                    new Module("Perfect 5ths (Power Chords)", 7, secIntervals, contentPowerChords),
                    new Module("Understanding Pulse", 8, secRhythm, contentRhythm)
                ));
            }
        };
    }
}