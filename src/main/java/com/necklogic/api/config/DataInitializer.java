package com.necklogic.api.config;

import com.necklogic.api.model.Module;
import com.necklogic.api.model.Section;
import com.necklogic.api.model.Track;
import com.necklogic.api.repository.ModuleRepository;
import com.necklogic.api.repository.SectionRepository;
import com.necklogic.api.repository.TrackRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    @Transactional
    CommandLineRunner initDatabase(ModuleRepository moduleRepository, SectionRepository sectionRepository, TrackRepository trackRepository) {
        return args -> {
            if (sectionRepository.count() == 0) {
                moduleRepository.deleteAll();

                Track officialTrack = trackRepository.save(
                        new Track("Trilha Oficial", "Trilha padrão do NeckLogic, mantida pela equipe.", null, true, true)
                );

                Section secNavigation = new Section("Navigation & Anchors", "Pare de se perder no braço.", 1);
                Section secIntervals = new Section("Intervals", "A geometria da música.", 2);
                Section secRhythm = new Section("Rhythm & Feel", "Onde colocar cada nota.", 3);
                Section secQa = new Section("QA - Tipos de Exercício", "Trilha de teste para validar todos os tipos de exercício implementados.", 4);

                for (Section section : List.of(secNavigation, secIntervals, secRhythm, secQa)) {
                    section.setTrack(officialTrack);
                }

                sectionRepository.saveAll(List.of(secNavigation, secIntervals, secRhythm, secQa));
                String contentFretboardBasics = """
                    [
                        {
                            "type": "THEORY",
                            "title": "As Cordas",
                            "text": "O braço do instrumento funciona como um mapa de coordenadas. A organização vertical começa na 1ª corda (mais aguda) até a 6ª corda (mais grave).",
                            "illustration": {
                                "kind": "fretboard",
                                "notes": [
                                    {
                                        "string": 6,
                                        "fret": 0
                                    },
                                    {
                                        "string": 5,
                                        "fret": 0
                                    },
                                    {
                                        "string": 4,
                                        "fret": 0
                                    },
                                    {
                                        "string": 3,
                                        "fret": 0
                                    },
                                    {
                                        "string": 2,
                                        "fret": 0
                                    },
                                    {
                                        "string": 1,
                                        "fret": 0
                                    }
                                ]
                            }
                        },
                        {
                            "type": "DRILL",
                            "title": "Corda Grave",
                            "question": "Selecione a 6ª corda solta.",
                            "targetShape": [
                                {
                                    "string": 6,
                                    "fret": 0
                                }
                            ],
                            "exerciseType": "SHAPE_MATCH"
                        },
                        {
                            "type": "DRILL",
                            "title": "Corda Aguda",
                            "question": "Selecione a 1ª corda solta.",
                            "targetShape": [
                                {
                                    "string": 1,
                                    "fret": 0
                                }
                            ],
                            "exerciseType": "SHAPE_MATCH"
                        },
                        {
                            "type": "THEORY",
                            "title": "As Casas e Trastes",
                            "text": "A navegação horizontal ocorre pelas casas (frets). Avançar 1 casa eleva a nota em 1 semitom. As marcações (inlays) nas casas 3, 5, 7 e 9 servem como pontos de referência visual.",
                            "illustration": {
                                "kind": "fretboard",
                                "notes": []
                            }
                        },
                        {
                            "type": "DRILL",
                            "title": "Cruzando Informações",
                            "question": "Localize a 6ª corda, casa 5 (segunda marcação).",
                            "targetShape": [
                                {
                                    "string": 6,
                                    "fret": 5
                                }
                            ],
                            "exerciseType": "SHAPE_MATCH"
                        },
                        {
                            "type": "THEORY",
                            "title": "Marcadores Centrais",
                            "text": "As marcações ajudam a visualizar o braço por inteiro. A marcação da casa 7, por exemplo, alinha visualmente as notas em todas as cordas de forma rápida.",
                            "illustration": {
                                "kind": "fretboard",
                                "notes": [
                                    {
                                        "string": 6,
                                        "fret": 7
                                    },
                                    {
                                        "string": 5,
                                        "fret": 7
                                    },
                                    {
                                        "string": 4,
                                        "fret": 7
                                    },
                                    {
                                        "string": 3,
                                        "fret": 7
                                    },
                                    {
                                        "string": 2,
                                        "fret": 7
                                    },
                                    {
                                        "string": 1,
                                        "fret": 7
                                    }
                                ]
                            }
                        },
                        {
                            "type": "DRILL",
                            "title": "Localização Rápida",
                            "question": "Navegue diretamente para a 3ª corda, casa 7.",
                            "targetShape": [
                                {
                                    "string": 3,
                                    "fret": 7
                                }
                            ],
                            "exerciseType": "SHAPE_MATCH"
                        }
                    ]
                """;

                String contentString6Natural = """
                    [
                        {
                            "type": "THEORY",
                            "title": "O Padrão de 1 Tom",
                            "text": "As notas naturais são A, B, C, D, E, F, G. Na guitarra, a distância de 1 Tom equivale a pular 1 casa inteira (avançar 2 casas).",
                            "illustration": {
                                "kind": "fretboard",
                                "notes": [
                                    {
                                        "string": 6,
                                        "fret": 3
                                    },
                                    {
                                        "string": 6,
                                        "fret": 5
                                    },
                                    {
                                        "string": 6,
                                        "fret": 7
                                    }
                                ]
                            }
                        },
                        {
                            "type": "DRILL",
                            "title": "Nota G",
                            "question": "Localize a nota G na 6ª corda (primeira marcação).",
                            "targetShape": [
                                {
                                    "string": 6,
                                    "fret": 3
                                }
                            ],
                            "exerciseType": "SHAPE_MATCH"
                        },
                        {
                            "type": "DRILL",
                            "title": "Nota A",
                            "question": "Avance 1 Tom a partir do G para localizar a nota A (segunda marcação).",
                            "targetShape": [
                                {
                                    "string": 6,
                                    "fret": 5
                                }
                            ],
                            "exerciseType": "SHAPE_MATCH"
                        },
                        {
                            "type": "THEORY",
                            "title": "Distância de Semitom: E e F",
                            "text": "As notas E e F possuem apenas 1 Semitom de distância (casas vizinhas). Como a 6ª corda solta é E, a casa 1 é automaticamente o F.",
                            "illustration": {
                                "kind": "fretboard",
                                "notes": [
                                    {
                                        "string": 6,
                                        "fret": 0
                                    },
                                    {
                                        "string": 6,
                                        "fret": 1
                                    }
                                ]
                            }
                        },
                        {
                            "type": "DRILL",
                            "title": "Nota F",
                            "question": "Localize a nota F na 6ª corda.",
                            "targetShape": [
                                {
                                    "string": 6,
                                    "fret": 1
                                }
                            ],
                            "exerciseType": "SHAPE_MATCH"
                        },
                        {
                            "type": "THEORY",
                            "title": "Distância de Semitom: B e C",
                            "text": "Assim como E e F, as notas B e C distam apenas 1 Semitom. Sabendo que o B está na casa 7, o C estará na casa seguinte.",
                            "illustration": {
                                "kind": "fretboard",
                                "notes": [
                                    {
                                        "string": 6,
                                        "fret": 7
                                    },
                                    {
                                        "string": 6,
                                        "fret": 8
                                    }
                                ]
                            }
                        },
                        {
                            "type": "DRILL",
                            "title": "Nota C",
                            "question": "Localize a nota C na 6ª corda.",
                            "targetShape": [
                                {
                                    "string": 6,
                                    "fret": 8
                                }
                            ],
                            "exerciseType": "SHAPE_MATCH"
                        },
                        {
                            "type": "DRILL",
                            "title": "Dedução de 1 Tom",
                            "question": "Aplicando a regra de 1 Tom a partir do C, localize a nota D.",
                            "targetShape": [
                                {
                                    "string": 6,
                                    "fret": 10
                                }
                            ],
                            "exerciseType": "SHAPE_MATCH"
                        }
                    ]
                """;

                String contentString6Accidentals = """
                    [
                        {
                            "type": "THEORY",
                            "title": "O Sustenido (#)",
                            "text": "O sustenido eleva a nota em 1 semitom, o que significa avançar 1 casa em direção ao corpo da guitarra.",
                            "illustration": {
                                "kind": "fretboard",
                                "notes": [
                                    {
                                        "string": 6,
                                        "fret": 1
                                    },
                                    {
                                        "string": 6,
                                        "fret": 2
                                    }
                                ]
                            }
                        },
                        {
                            "type": "DRILL",
                            "title": "Nota F#",
                            "question": "Encontre o F natural e avance 1 semitom para marcar o F#.",
                            "targetShape": [
                                {
                                    "string": 6,
                                    "fret": 2
                                }
                            ],
                            "exerciseType": "SHAPE_MATCH"
                        },
                        {
                            "type": "DRILL",
                            "title": "Nota G#",
                            "question": "Encontre a nota G e avance 1 semitom para marcar o G#.",
                            "targetShape": [
                                {
                                    "string": 6,
                                    "fret": 4
                                }
                            ],
                            "exerciseType": "SHAPE_MATCH"
                        },
                        {
                            "type": "THEORY",
                            "title": "O Bemol (b)",
                            "text": "O bemol abaixa a nota em 1 semitom, o que significa recuar 1 casa em direção à mão (headstock) do instrumento.",
                            "illustration": {
                                "kind": "fretboard",
                                "notes": [
                                    {
                                        "string": 6,
                                        "fret": 7
                                    },
                                    {
                                        "string": 6,
                                        "fret": 6
                                    }
                                ]
                            }
                        },
                        {
                            "type": "DRILL",
                            "title": "Nota Bb",
                            "question": "Encontre o B natural e recue 1 semitom para marcar o Bb.",
                            "targetShape": [
                                {
                                    "string": 6,
                                    "fret": 6
                                }
                            ],
                            "exerciseType": "SHAPE_MATCH"
                        },
                        {
                            "type": "THEORY",
                            "title": "Enarmonia",
                            "text": "Avançar de G (G#) ou recuar de A (Ab) leva à mesma casa. Enarmonia ocorre quando a mesma posição física possui dois nomes dependendo da escala.",
                            "illustration": {
                                "kind": "fretboard",
                                "notes": [
                                    {
                                        "string": 6,
                                        "fret": 3
                                    },
                                    {
                                        "string": 6,
                                        "fret": 4
                                    },
                                    {
                                        "string": 6,
                                        "fret": 5
                                    }
                                ]
                            }
                        },
                        {
                            "type": "DRILL",
                            "title": "Identificando Gb",
                            "question": "Localize a nota Gb. Dica: parta da nota G natural e recue 1 semitom.",
                            "targetShape": [
                                {
                                    "string": 6,
                                    "fret": 2
                                }
                            ],
                            "exerciseType": "SHAPE_MATCH"
                        }
                    ]
                """;

                String contentString5Natural = """
                    [
                        {
                            "type": "THEORY",
                            "title": "A Corda A",
                            "text": "A 5ª corda solta é a nota A. As regras de distância se mantêm: 1 Tom para a maioria das notas, e 1 Semitom entre as exceções B-C e E-F.",
                            "illustration": {
                                "kind": "fretboard",
                                "notes": [
                                    {
                                        "string": 5,
                                        "fret": 0
                                    }
                                ]
                            }
                        },
                        {
                            "type": "DRILL",
                            "title": "A Corda Solta",
                            "question": "Selecione a 5ª corda solta.",
                            "targetShape": [
                                {
                                    "string": 5,
                                    "fret": 0
                                }
                            ],
                            "exerciseType": "SHAPE_MATCH"
                        },
                        {
                            "type": "THEORY",
                            "title": "Avançando 1 Tom",
                            "text": "Avançando 1 Tom (2 casas) a partir da corda A solta, localizamos a nota B na casa 2.",
                            "illustration": {
                                "kind": "fretboard",
                                "notes": [
                                    {
                                        "string": 5,
                                        "fret": 2
                                    }
                                ]
                            }
                        },
                        {
                            "type": "DRILL",
                            "title": "Nota C",
                            "question": "Aplicando a distância de 1 Semitom entre B e C, localize o C na 5ª corda.",
                            "targetShape": [
                                {
                                    "string": 5,
                                    "fret": 3
                                }
                            ],
                            "exerciseType": "SHAPE_MATCH"
                        },
                        {
                            "type": "DRILL",
                            "title": "Nota D",
                            "question": "Avance 1 Tom a partir do C e localize a nota D na 5ª corda.",
                            "targetShape": [
                                {
                                    "string": 5,
                                    "fret": 5
                                }
                            ],
                            "exerciseType": "SHAPE_MATCH"
                        },
                        {
                            "type": "THEORY",
                            "title": "Notas E e F",
                            "text": "Continuando a escala, encontramos o E na casa 7. Em seguida, aplica-se novamente a regra de 1 Semitom para chegar ao F.",
                            "illustration": {
                                "kind": "fretboard",
                                "notes": [
                                    {
                                        "string": 5,
                                        "fret": 7
                                    },
                                    {
                                        "string": 5,
                                        "fret": 8
                                    }
                                ]
                            }
                        },
                        {
                            "type": "DRILL",
                            "title": "Nota F",
                            "question": "Localize a nota F na 5ª corda.",
                            "targetShape": [
                                {
                                    "string": 5,
                                    "fret": 8
                                }
                            ],
                            "exerciseType": "SHAPE_MATCH"
                        }
                    ]
                """;

                String contentString5Accidentals = """
                    [
                        {
                            "type": "THEORY",
                            "title": "Sustenidos na Corda A",
                            "text": "A lógica de acidentes se mantém de forma universal. Para encontrar a nota C#, basta localizar o C e avançar 1 semitom.",
                            "illustration": {
                                "kind": "fretboard",
                                "notes": [
                                    {
                                        "string": 5,
                                        "fret": 3
                                    },
                                    {
                                        "string": 5,
                                        "fret": 4
                                    }
                                ]
                            }
                        },
                        {
                            "type": "DRILL",
                            "title": "Nota C#",
                            "question": "Localize o C# na 5ª corda.",
                            "targetShape": [
                                {
                                    "string": 5,
                                    "fret": 4
                                }
                            ],
                            "exerciseType": "SHAPE_MATCH"
                        },
                        {
                            "type": "DRILL",
                            "title": "Nota Eb",
                            "question": "Localize o E natural (casa 7) e aplique o bemol recuando 1 semitom para encontrar o Eb.",
                            "targetShape": [
                                {
                                    "string": 5,
                                    "fret": 6
                                }
                            ],
                            "exerciseType": "SHAPE_MATCH"
                        },
                        {
                            "type": "THEORY",
                            "title": "O Bb na Corda 5",
                            "text": "A casa 1 da 5ª corda corresponde ao Bb (ou A#), recuando 1 semitom a partir do B (casa 2) ou avançando a partir do A solto.",
                            "illustration": {
                                "kind": "fretboard",
                                "notes": [
                                    {
                                        "string": 5,
                                        "fret": 1
                                    }
                                ]
                            }
                        },
                        {
                            "type": "DRILL",
                            "title": "Nota Bb",
                            "question": "Localize a nota Bb na 5ª corda.",
                            "targetShape": [
                                {
                                    "string": 5,
                                    "fret": 1
                                }
                            ],
                            "exerciseType": "SHAPE_MATCH"
                        }
                    ]
                """;

                String contentOctaveShape64 = """
                    [
                        {
                            "type": "THEORY",
                            "title": "Oitavas",
                            "text": "Uma oitava é a repetição da mesma nota em uma região mais aguda. O shape visual mais comum parte da 6ª corda e alcança a 4ª corda.",
                            "illustration": {
                                "kind": "fretboard",
                                "notes": [
                                    {
                                        "string": 6,
                                        "fret": 3
                                    },
                                    {
                                        "string": 4,
                                        "fret": 5
                                    }
                                ]
                            }
                        },
                        {
                            "type": "THEORY",
                            "title": "O Shape 6-4",
                            "text": "Para encontrar a oitava de uma nota na 6ª corda: pule a 5ª corda e avance 2 casas na 4ª corda.",
                            "illustration": {
                                "kind": "fretboard",
                                "notes": [
                                    {
                                        "string": 6,
                                        "fret": 5
                                    },
                                    {
                                        "string": 4,
                                        "fret": 7
                                    }
                                ]
                            }
                        },
                        {
                            "type": "DRILL",
                            "title": "Oitava de A",
                            "question": "Selecione as duas notas que formam a oitava de A (Tônica na 6ª corda, casa 5).",
                            "targetShape": [
                                {
                                    "string": 6,
                                    "fret": 5
                                },
                                {
                                    "string": 4,
                                    "fret": 7
                                }
                            ],
                            "exerciseType": "SHAPE_MATCH"
                        },
                        {
                            "type": "DRILL",
                            "title": "Oitava de C",
                            "question": "Encontre o C na 6ª corda e marque sua respectiva oitava na 4ª corda.",
                            "targetShape": [
                                {
                                    "string": 6,
                                    "fret": 8
                                },
                                {
                                    "string": 4,
                                    "fret": 10
                                }
                            ],
                            "exerciseType": "SHAPE_MATCH"
                        },
                        {
                            "type": "DRILL",
                            "title": "Encontrando a Tônica",
                            "question": "Dado que a 4ª corda, casa 2, é um E, encontre esta nota e selecione o E de origem na 6ª corda solta.",
                            "targetShape": [
                                {
                                    "string": 6,
                                    "fret": 0
                                },
                                {
                                    "string": 4,
                                    "fret": 2
                                }
                            ],
                            "exerciseType": "SHAPE_MATCH"
                        }
                    ]
                """;

                String contentPowerChords = """
                    [
                        {
                            "type": "THEORY",
                            "title": "O Power Chord (C5)",
                            "text": "Formado pela Tônica e a 5ª Justa. Veja o shape do C5 a partir da corda A.",
                            "illustration": {
                                "kind": "fretboard",
                                "notes": [
                                    {
                                        "string": 5,
                                        "fret": 3
                                    },
                                    {
                                        "string": 4,
                                        "fret": 5
                                    }
                                ]
                            }
                        },
                        {
                            "type": "DRILL",
                            "title": "Prática: Power Chord",
                            "question": "Monte o shape de C5 na 5ª corda.",
                            "targetShape": [
                                {
                                    "string": 5,
                                    "fret": 3
                                },
                                {
                                    "string": 4,
                                    "fret": 5
                                }
                            ],
                            "exerciseType": "SHAPE_MATCH"
                        }
                    ]
                """;

                String contentNotesAndIntervals = """
                    [
                        {
                            "type": "THEORY",
                            "title": "Nomear e Comparar Notas",
                            "text": "Além de encontrar uma nota a partir do nome dela, também é útil treinar o caminho inverso: olhar para uma posição no braço e identificar rapidamente qual nota (ou qual intervalo entre duas notas) ela representa."
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "MULTIPLE_CHOICE",
                            "title": "Nomeie a Nota",
                            "question": "Qual é o nome da nota marcada?",
                            "options": [
                                "F#",
                                "G",
                                "G#",
                                "A"
                            ],
                            "correctAnswer": "G"
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "FIND_ALL_OCCURRENCES",
                            "title": "Todas as Ocorrências",
                            "question": "Marque todas as ocorrências da nota E até a casa 12.",
                            "targetNote": "E",
                            "maxFret": 12
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "MULTIPLE_CHOICE",
                            "title": "Identifique o Intervalo",
                            "question": "Qual é o intervalo entre as duas notas marcadas (tônica em roxo)?",
                            "options": [
                                "2",
                                "♭3",
                                "3",
                                "4"
                            ],
                            "correctAnswer": "♭3"
                        }
                    ]
                """;

                String contentChordBuilding = """
                    [
                        {
                            "type": "THEORY",
                            "title": "Fórmula do Acorde",
                            "text": "Um acorde é formado empilhando intervalos a partir da tônica. Uma tríade maior é Tônica + 3ª maior + 5ª justa; uma tríade menor troca a 3ª maior pela 3ª menor. Em vez de decorar posições fixas, você pode montar o acorde encontrando essas notas em qualquer lugar do braço."
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "CHORD_BUILD",
                            "title": "Tríade de Dó Maior",
                            "question": "Monte a tríade de C maior (C - E - G) selecionando uma ocorrência de cada nota.",
                            "root": "C",
                            "quality": "major"
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "CHORD_BUILD",
                            "title": "Tríade de Lá Menor",
                            "question": "Monte a tríade de A menor (A - C - E) selecionando uma ocorrência de cada nota.",
                            "root": "A",
                            "quality": "minor"
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "CHORD_BUILD",
                            "title": "Tétrade de Sol Dominante",
                            "question": "Monte o G7 (G - B - D - F) selecionando uma ocorrência de cada nota.",
                            "root": "G",
                            "quality": "dom7"
                        }
                    ]
                """;

                String contentTriadInversions = """
                    [
                        {
                            "type": "THEORY",
                            "title": "Inversões de Tríade",
                            "text": "Uma tríade tem sempre as mesmas 3 notas, mas a nota mais grave (o baixo) pode mudar. Quando a fundamental está no baixo, é a posição fundamental. Quando a 3ª está no baixo, é a 1ª inversão. Quando a 5ª está no baixo, é a 2ª inversão. No violão isso significa escolher posições onde a nota mais grave selecionada seja a certa."
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "TRIAD_INVERSION",
                            "title": "C Maior - Posição Fundamental",
                            "question": "Monte a tríade de C maior (C - E - G) com o C (fundamental) sendo a nota mais grave selecionada.",
                            "root": "C",
                            "quality": "major",
                            "inversion": 0
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "TRIAD_INVERSION",
                            "title": "C Maior - 1ª Inversão",
                            "question": "Monte a tríade de C maior (C - E - G) com o E (3ª) sendo a nota mais grave selecionada.",
                            "root": "C",
                            "quality": "major",
                            "inversion": 1
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "TRIAD_INVERSION",
                            "title": "C Maior - 2ª Inversão",
                            "question": "Monte a tríade de C maior (C - E - G) com o G (5ª) sendo a nota mais grave selecionada.",
                            "root": "C",
                            "quality": "major",
                            "inversion": 2
                        },
                        {
                            "type": "THEORY",
                            "title": "Inversões de Tétrade",
                            "text": "Uma tétrade (acorde de 4 notas, como um 7ª) tem uma inversão a mais que uma tríade: quando a 7ª está no baixo, é a 3ª inversão. A lógica é a mesma: qualquer uma das 4 notas do acorde pode ser a mais grave."
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "TRIAD_INVERSION",
                            "title": "G7 - Posição Fundamental",
                            "question": "Monte a tétrade de G7 (G - B - D - F) com o G (fundamental) sendo a nota mais grave selecionada.",
                            "root": "G",
                            "quality": "dom7",
                            "inversion": 0
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "TRIAD_INVERSION",
                            "title": "G7 - 1ª Inversão",
                            "question": "Monte a tétrade de G7 (G - B - D - F) com o B (3ª) sendo a nota mais grave selecionada.",
                            "root": "G",
                            "quality": "dom7",
                            "inversion": 1
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "TRIAD_INVERSION",
                            "title": "G7 - 2ª Inversão",
                            "question": "Monte a tétrade de G7 (G - B - D - F) com o D (5ª) sendo a nota mais grave selecionada.",
                            "root": "G",
                            "quality": "dom7",
                            "inversion": 2
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "TRIAD_INVERSION",
                            "title": "G7 - 3ª Inversão",
                            "question": "Monte a tétrade de G7 (G - B - D - F) com o F (7ª) sendo a nota mais grave selecionada.",
                            "root": "G",
                            "quality": "dom7",
                            "inversion": 3
                        }
                    ]
                """;

                String contentCaged = """
                    [
                        {
                            "type": "THEORY",
                            "title": "O Sistema CAGED",
                            "text": "As formas dos acordes abertos C, A, G, E e D podem ser deslocadas pelo braço usando pestana (barra), mantendo o mesmo desenho relativo de dedos. Isso permite tocar o mesmo acorde em cinco posições diferentes, cada uma baseada em uma forma familiar. Vamos construir o mesmo acorde (G maior) a partir das cinco formas, subindo o braço: G, E, D, C e A."
                        },
                        {
                            "type": "DRILL",
                            "title": "G Maior - Formato G (Aberto)",
                            "question": "Monte o acorde de G maior na posição aberta, formato G (sem pestana).",
                            "targetShape": [
                                {
                                    "string": 6,
                                    "fret": 3
                                },
                                {
                                    "string": 5,
                                    "fret": 2
                                },
                                {
                                    "string": 4,
                                    "fret": 0
                                },
                                {
                                    "string": 3,
                                    "fret": 0
                                },
                                {
                                    "string": 2,
                                    "fret": 0
                                },
                                {
                                    "string": 1,
                                    "fret": 3
                                }
                            ],
                            "exerciseType": "SHAPE_MATCH"
                        },
                        {
                            "type": "DRILL",
                            "title": "G Maior - Formato E",
                            "question": "Monte o acorde de G maior usando o formato E, com pestana na casa 3 (6ª corda = raiz). Pressione as 6 cordas, como num acorde real.",
                            "targetShape": [
                                {
                                    "string": 6,
                                    "fret": 3
                                },
                                {
                                    "string": 5,
                                    "fret": 5
                                },
                                {
                                    "string": 4,
                                    "fret": 5
                                },
                                {
                                    "string": 3,
                                    "fret": 4
                                },
                                {
                                    "string": 2,
                                    "fret": 3
                                },
                                {
                                    "string": 1,
                                    "fret": 3
                                }
                            ],
                            "exerciseType": "SHAPE_MATCH"
                        },
                        {
                            "type": "DRILL",
                            "title": "G Maior - Formato D",
                            "question": "Agora monte o mesmo acorde de G maior usando o formato D, com pestana na casa 5 (4ª corda = raiz). Apenas as 4 cordas mais agudas são tocadas nesse formato.",
                            "targetShape": [
                                {
                                    "string": 4,
                                    "fret": 5
                                },
                                {
                                    "string": 3,
                                    "fret": 7
                                },
                                {
                                    "string": 2,
                                    "fret": 8
                                },
                                {
                                    "string": 1,
                                    "fret": 7
                                }
                            ],
                            "exerciseType": "SHAPE_MATCH"
                        },
                        {
                            "type": "DRILL",
                            "title": "G Maior - Formato C",
                            "question": "Monte o mesmo acorde de G maior usando o formato C, com pestana na casa 7 (5ª corda = raiz).",
                            "targetShape": [
                                {
                                    "string": 5,
                                    "fret": 10
                                },
                                {
                                    "string": 4,
                                    "fret": 9
                                },
                                {
                                    "string": 3,
                                    "fret": 7
                                },
                                {
                                    "string": 2,
                                    "fret": 8
                                },
                                {
                                    "string": 1,
                                    "fret": 7
                                }
                            ],
                            "exerciseType": "SHAPE_MATCH"
                        },
                        {
                            "type": "DRILL",
                            "title": "G Maior - Formato A",
                            "question": "Por fim, monte o mesmo acorde de G maior usando o formato A, com pestana na casa 10 (5ª corda = raiz). A 6ª corda não é tocada nesse formato.",
                            "targetShape": [
                                {
                                    "string": 5,
                                    "fret": 10
                                },
                                {
                                    "string": 4,
                                    "fret": 12
                                },
                                {
                                    "string": 3,
                                    "fret": 12
                                },
                                {
                                    "string": 2,
                                    "fret": 12
                                },
                                {
                                    "string": 1,
                                    "fret": 10
                                }
                            ],
                            "exerciseType": "SHAPE_MATCH"
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "MULTIPLE_CHOICE",
                            "title": "Identifique o Sistema",
                            "question": "As cinco posições que você acabou de tocar formam o mesmo acorde de G maior em regiões diferentes do braço. Esse é o princípio de qual sistema de acordes móveis?",
                            "options": [
                                "Power Chords",
                                "CAGED",
                                "Modos Gregos",
                                "Campo Harmônico"
                            ],
                            "correctAnswer": "CAGED"
                        }
                    ]
                """;

                String contentHarmonicField = """
                    [
                        {
                            "type": "THEORY",
                            "title": "Campo Harmônico",
                            "text": "Empilhando terças a partir de cada nota de uma escala maior, obtemos 7 acordes, um por grau. Os graus são numerados com algarismos romanos: maiúsculo para acordes maiores, minúsculo para menores, e um círculo (°) para o único diminuto. Em Dó maior: I=C, ii=Dm, iii=Em, IV=F, V=G, vi=Am, vii°=Bdim."
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "HARMONIC_FIELD",
                            "title": "IV Grau",
                            "question": "Toque no acorde do IV grau do campo harmônico de C maior.",
                            "key": "C",
                            "mode": "major",
                            "targetDegree": "IV"
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "HARMONIC_FIELD",
                            "title": "vi Grau",
                            "question": "Toque no acorde do vi grau do campo harmônico de C maior.",
                            "key": "C",
                            "mode": "major",
                            "targetDegree": "vi"
                        },
                        {
                            "type": "THEORY",
                            "title": "Relativa Maior e Menor",
                            "text": "Toda tonalidade maior compartilha as mesmas 7 notas com uma tonalidade menor: sua relativa. A relativa menor é sempre o vi grau da maior (uma 6ª acima da tônica). A relativa maior é sempre o III grau da menor (uma 3ª acima da tônica)."
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "MULTIPLE_CHOICE",
                            "title": "Relativa Menor",
                            "question": "Qual é a relativa menor de C maior?",
                            "options": [
                                "A menor",
                                "E menor",
                                "D menor",
                                "G menor"
                            ],
                            "correctAnswer": "A menor"
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "MULTIPLE_CHOICE",
                            "title": "Relativa Maior",
                            "question": "Qual é a relativa maior de A menor?",
                            "options": [
                                "C maior",
                                "F maior",
                                "G maior",
                                "D maior"
                            ],
                            "correctAnswer": "C maior"
                        },
                        {
                            "type": "THEORY",
                            "title": "Progressões Comuns",
                            "text": "Progressões são sequências de graus que se repetem em milhares de músicas. A I-V-vi-IV, conhecida informalmente como a progressão dos 4 acordes, é uma das mais usadas na música popular."
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "MULTIPLE_CHOICE",
                            "title": "Reconhecendo a Progressão",
                            "question": "Em C maior, a sequência de acordes C - G - Am - F corresponde a qual progressão de graus?",
                            "options": [
                                "I-V-vi-IV",
                                "ii-V-I",
                                "I-IV-V",
                                "vi-IV-I-V"
                            ],
                            "correctAnswer": "I-V-vi-IV"
                        },
                        {
                            "type": "THEORY",
                            "title": "Cadências",
                            "text": "Cadência é o movimento de acordes que marca um ponto de repouso ou conclusão. A cadência perfeita (V-I) é a mais forte, porque a tensão do V grau resolve totalmente na tônica. A progressão ii-V-I encadeia subdominante, dominante e tônica, e é a base harmônica mais comum em jazz e em milhares de músicas populares."
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "MULTIPLE_CHOICE",
                            "title": "Cadência Perfeita",
                            "question": "Qual sequência de graus forma a cadência mais forte e conclusiva?",
                            "options": [
                                "V-I",
                                "IV-I",
                                "ii-V",
                                "vi-IV"
                            ],
                            "correctAnswer": "V-I"
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "MULTIPLE_CHOICE",
                            "title": "ii-V-I",
                            "question": "Em C maior, quais acordes formam a progressão ii-V-I?",
                            "options": [
                                "Dm - G - C",
                                "Em - Am - Dm",
                                "F - G - C",
                                "Dm - F - C"
                            ],
                            "correctAnswer": "Dm - G - C"
                        },
                        {
                            "type": "THEORY",
                            "title": "Dominante Secundária",
                            "text": "Uma dominante secundária é o V grau emprestado de outra tonalidade para reforçar temporariamente a resolução de um grau que não é a tônica. Ela é identificada como V/X, onde X é o grau alvo. Em C maior, o V do V (V/V) é D7: como o V grau de C é G, D7 funciona como a dominante de G e resolve fortemente para ele, mesmo D7 não pertencendo ao campo harmônico de C maior."
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "MULTIPLE_CHOICE",
                            "title": "V da V",
                            "question": "Em C maior, qual é a dominante secundária do V grau (V/V)?",
                            "options": [
                                "D7",
                                "A7",
                                "E7",
                                "B7"
                            ],
                            "correctAnswer": "D7"
                        },
                        {
                            "type": "THEORY",
                            "title": "Círculo de Quintas",
                            "text": "O círculo de quintas organiza as 12 tonalidades em sequência de quintas justas. Partindo de C e avançando no sentido horário, cada tonalidade fica uma quinta acima da anterior: C, G, D, A, E, B, F#, C#, G#, D#, A#, F. Tonalidades vizinhas no círculo compartilham quase todas as notas, por isso soam bem em progressões e modulações."
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "CIRCLE_OF_FIFTHS",
                            "title": "Uma Quinta Acima",
                            "question": "No círculo de quintas, toque na tonalidade que fica uma quinta acima de C.",
                            "targetKey": "G"
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "CIRCLE_OF_FIFTHS",
                            "title": "Uma Quinta Abaixo",
                            "question": "No círculo de quintas, toque na tonalidade que fica uma quinta abaixo de C (uma posição anti-horária).",
                            "targetKey": "F"
                        }
                    ]
                """;

                String contentQaAllExerciseTypes = """
                    [
                        {
                            "type": "THEORY",
                            "title": "Trilha de Testes",
                            "text": "Este módulo reúne um exemplo de cada recurso implementado até agora: as 4 ilustrações estáticas de teoria e os 11 tipos de exercício. Cada exercício vem precedido de uma explicação rápida de como interagir com ele."
                        },
                        {
                            "type": "THEORY",
                            "title": "Ilustração: Braço (Fretboard)",
                            "text": "Passos de teoria podem trazer um braço estático, montado pelo professor, para ilustrar o texto. Aqui está a tríade de C maior (C - E - G) marcada na 5ª e 4ª corda.",
                            "illustration": {
                                "kind": "fretboard",
                                "notes": [
                                    { "string": 5, "fret": 3 },
                                    { "string": 4, "fret": 2 },
                                    { "string": 3, "fret": 0 }
                                ]
                            }
                        },
                        {
                            "type": "THEORY",
                            "title": "Ilustração: Círculo de Quintas",
                            "text": "Também é possível ilustrar com o círculo de quintas, destacando uma ou mais tonalidades. Aqui, C, G e D estão em destaque.",
                            "illustration": {
                                "kind": "circleOfFifths",
                                "highlightedKeys": ["C", "G", "D"]
                            }
                        },
                        {
                            "type": "THEORY",
                            "title": "Ilustração: Campo Harmônico",
                            "text": "Ou com a roda do campo harmônico de uma tonalidade, destacando um ou mais graus. Aqui, os graus I, IV e V de C maior (a cadência mais comum) estão em destaque.",
                            "illustration": {
                                "kind": "harmonicField",
                                "key": "C",
                                "mode": "major",
                                "highlightedDegrees": ["I", "IV", "V"]
                            }
                        },
                        {
                            "type": "THEORY",
                            "title": "Ilustração: Partitura",
                            "text": "E também com uma partitura estática, útil para introduzir leitura musical antes de pedir que o aluno pratique. Aqui, o arpejo de C maior ascendente (C4 - E4 - G4).",
                            "illustration": {
                                "kind": "staff",
                                "clef": "treble",
                                "beatsPerMeasure": 4,
                                "notes": [
                                    { "note": "C4", "duration": "quarter" },
                                    { "note": "E4", "duration": "quarter" },
                                    { "note": "G4", "duration": "half" }
                                ]
                            }
                        },
                        {
                            "type": "THEORY",
                            "title": "Exercício: SHAPE_MATCH (nota única)",
                            "text": "O tipo mais simples de exercício: toque em qualquer ocorrência de uma nota específica no braço. Serve para memorizar onde cada nota aparece nas diferentes cordas e casas."
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "SHAPE_MATCH: Nota Única",
                            "question": "Selecione qualquer ocorrência da nota C no braço.",
                            "targetNote": "C"
                        },
                        {
                            "type": "THEORY",
                            "title": "Exercício: SHAPE_MATCH (conjunto de notas)",
                            "text": "Uma variação: toque uma ocorrência de cada nota de um conjunto, em qualquer posição do braço. Não importa a posição exata, só que todas as notas do conjunto sejam cobertas."
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "SHAPE_MATCH: Conjunto de Notas",
                            "question": "Selecione uma ocorrência de cada nota da tríade de Dó Maior (C, E, G).",
                            "targetNotes": ["C", "E", "G"]
                        },
                        {
                            "type": "THEORY",
                            "title": "Exercício: SHAPE_MATCH (formato fixo)",
                            "text": "A variação mais exigente: reproduza um formato exato de posições no braço, corda e casa certas. Ideal para praticar shapes de acorde memorizados."
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "SHAPE_MATCH: Formato Fixo",
                            "question": "Monte o power chord de C5 exatamente nesta posição (5ª corda, casa 3 + 4ª corda, casa 5).",
                            "targetShape": [
                                { "string": 5, "fret": 3 },
                                { "string": 4, "fret": 5 }
                            ]
                        },
                        {
                            "type": "THEORY",
                            "title": "Exercício: FIND_ALL_OCCURRENCES",
                            "text": "Diferente do SHAPE_MATCH de nota única, aqui é preciso marcar TODAS as ocorrências de uma nota dentro do trecho do braço mostrado, não só uma."
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "FIND_ALL_OCCURRENCES",
                            "title": "FIND_ALL_OCCURRENCES",
                            "question": "Marque todas as ocorrências da nota G até a casa 5.",
                            "targetNote": "G",
                            "maxFret": 5
                        },
                        {
                            "type": "THEORY",
                            "title": "Exercício: MULTIPLE_CHOICE",
                            "text": "Pergunta de múltipla escolha simples, com uma pergunta e algumas alternativas de texto. Usado para teoria que não depende de tocar no braço."
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "MULTIPLE_CHOICE",
                            "title": "MULTIPLE_CHOICE: Nomear Nota",
                            "question": "Qual é o nome da nota marcada?",
                            "options": ["B", "C", "C#", "D"],
                            "correctAnswer": "C",
                            "markedPosition": { "string": 5, "fret": 3 }
                        },
                        {
                            "type": "THEORY",
                            "title": "Exercício: CHORD_BUILD",
                            "text": "Toque uma ocorrência de cada nota de um acorde (tríade ou tétrade), em qualquer posição do braço. A ordem e a posição exata não importam, só o conjunto de notas."
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "CHORD_BUILD",
                            "title": "CHORD_BUILD",
                            "question": "Monte a tríade de D menor (D - F - A) selecionando uma ocorrência de cada nota.",
                            "root": "D",
                            "quality": "minor"
                        },
                        {
                            "type": "THEORY",
                            "title": "Exercício: TRIAD_INVERSION",
                            "text": "Como o CHORD_BUILD, mas com uma exigência extra: a nota mais grave que você tocar precisa ser a nota certa da inversão pedida (fundamental, 3ª, 5ª ou 7ª no baixo)."
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "TRIAD_INVERSION",
                            "title": "TRIAD_INVERSION",
                            "question": "Monte a tríade de E menor (E - G - B) com o G (3ª) sendo a nota mais grave selecionada (1ª inversão).",
                            "root": "E",
                            "quality": "minor",
                            "inversion": 1
                        },
                        {
                            "type": "THEORY",
                            "title": "Exercício: CIRCLE_OF_FIFTHS",
                            "text": "Em vez do braço, o aluno interage com a roda do círculo de quintas, tocando na tonalidade pedida."
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "CIRCLE_OF_FIFTHS",
                            "title": "CIRCLE_OF_FIFTHS",
                            "question": "Toque na tonalidade que fica uma quinta acima de D no círculo de quintas.",
                            "targetKey": "A"
                        },
                        {
                            "type": "THEORY",
                            "title": "Exercício: HARMONIC_FIELD",
                            "text": "O aluno interage com a roda do campo harmônico de uma tonalidade, tocando no grau pedido."
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "HARMONIC_FIELD",
                            "title": "HARMONIC_FIELD",
                            "question": "Toque no acorde do V grau do campo harmônico de G maior.",
                            "key": "G",
                            "mode": "major",
                            "targetDegree": "V"
                        },
                        {
                            "type": "THEORY",
                            "title": "Exercício: SCALE_DEGREES",
                            "text": "O primeiro dos exercícios de sequência: toque as posições no braço na ordem exata pedida. Aqui, uma escala de G maior subindo pelas cordas graves."
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SCALE_DEGREES",
                            "title": "SCALE_DEGREES",
                            "question": "Toque a escala de G maior (G-A-B-C-D-E-F#-G) subindo pela 6ª, 5ª e 4ª cordas, na ordem.",
                            "targetSequence": [
                                { "string": 6, "fret": 3 }, { "string": 6, "fret": 5 }, { "string": 6, "fret": 7 },
                                { "string": 5, "fret": 3 }, { "string": 5, "fret": 5 }, { "string": 5, "fret": 7 },
                                { "string": 4, "fret": 4 }, { "string": 4, "fret": 5 }
                            ]
                        },
                        {
                            "type": "THEORY",
                            "title": "Exercício: ARPEGGIO",
                            "text": "A mesma mecânica de sequência ordenada, agora tocando as notas de um acorde uma de cada vez, em vez de todas juntas como no CHORD_BUILD."
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "ARPEGGIO",
                            "title": "ARPEGGIO",
                            "question": "Toque o arpejo de C maior (C-E-G) em ordem ascendente.",
                            "targetSequence": [
                                { "string": 5, "fret": 3 }, { "string": 4, "fret": 2 }, { "string": 3, "fret": 0 }
                            ]
                        },
                        {
                            "type": "THEORY",
                            "title": "Exercício: TAB_READING",
                            "text": "Também uma sequência ordenada, mas pensada para reproduzir literalmente uma tablatura: corda e casa exatas, na ordem em que apareceriam na tab."
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "TAB_READING",
                            "title": "TAB_READING",
                            "question": "Reproduza no braço a sequência mostrada na tablatura acima.",
                            "targetSequence": [
                                { "string": 6, "fret": 0 }, { "string": 6, "fret": 3 }, { "string": 5, "fret": 0 }, { "string": 5, "fret": 2 }
                            ]
                        },
                        {
                            "type": "THEORY",
                            "title": "Exercício: STAFF_READING",
                            "text": "O mais completo: uma partitura é exibida acima do braço, e o aluno precisa tocar as notas mostradas, na ordem, no braço (pausas não exigem toque). Funciona tanto na clave de sol quanto na de fá."
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "STAFF_READING",
                            "title": "STAFF_READING: Clave de Sol",
                            "question": "Toque no braço as notas mostradas na pauta acima, na ordem (a pausa não precisa de toque).",
                            "clef": "treble",
                            "beatsPerMeasure": 4,
                            "staffNotes": [
                                { "note": "C4", "duration": "quarter", "target": { "string": 3, "fret": 5 } },
                                { "note": "D4", "duration": "eighth", "target": { "string": 3, "fret": 7 } },
                                { "note": "D#4", "duration": "eighth", "target": { "string": 3, "fret": 8 } },
                                { "duration": "quarter" },
                                { "note": "E4", "duration": "half", "target": { "string": 1, "fret": 0 } }
                            ]
                        },
                        {
                            "type": "THEORY",
                            "title": "Exercício: STAFF_READING (clave de fá)",
                            "text": "Agora o mesmo exercício, só que com uma partitura escrita na clave de fá, tipicamente usada para as cordas mais graves."
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "STAFF_READING",
                            "title": "STAFF_READING: Clave de Fá",
                            "question": "Toque no braço as notas mostradas na pauta acima, na ordem.",
                            "clef": "bass",
                            "beatsPerMeasure": 4,
                            "staffNotes": [
                                { "note": "G2", "duration": "quarter", "target": { "string": 6, "fret": 3 } },
                                { "note": "A2", "duration": "quarter", "target": { "string": 5, "fret": 0 } },
                                { "note": "B2", "duration": "quarter", "target": { "string": 5, "fret": 2 } },
                                { "note": "C3", "duration": "quarter", "target": { "string": 5, "fret": 3 } }
                            ]
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
                            "pattern": [
                                "X",
                                "X",
                                "X",
                                "X"
                            ],
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
                        new Module("Notas e Intervalos", 8, secIntervals, contentNotesAndIntervals),
                        new Module("Understanding Pulse", 9, secRhythm, contentRhythm),
                        new Module("Construindo Acordes", 10, secIntervals, contentChordBuilding),
                        new Module("Inversões de Tríade e Tétrade", 11, secIntervals, contentTriadInversions),
                        new Module("Sistema CAGED", 12, secIntervals, contentCaged),
                        new Module("Campo Harmônico", 13, secIntervals, contentHarmonicField),
                        new Module("Teste: Todos os Tipos de Exercício", 14, secQa, contentQaAllExerciseTypes)
                ));
            }
        };
    }
}