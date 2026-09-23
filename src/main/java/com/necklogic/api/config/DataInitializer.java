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

                Track newOfficialTrack = new Track("Trilha Oficial", "Do zero à improvisação: domine o braço, a harmonia e as escalas necessárias para tocar de ouvido e compor.", null, true, true);
                newOfficialTrack.setApproved(true);
                Track officialTrack = trackRepository.save(newOfficialTrack);

                Section sec01FundamentosBraco = new Section("Fundamentos do Braço", "Domine cada nota do braço, casa por casa.", 1);
                Section sec02TeoriaEssencial = new Section("Teoria Musical Essencial", "A geometria da música: intervalos, escalas e tonalidades.", 2);
                Section sec03AcordesAbertos = new Section("Acordes Abertos e Power Chords", "Seus primeiros acordes e progressões.", 3);
                Section sec04CampoHarmonico = new Section("Campo Harmônico e Progressões", "Como os acordes se conectam dentro de um tom.", 4);
                Section sec05Caged = new Section("Sistema CAGED", "O mesmo acorde, cinco posições pelo braço inteiro.", 5);
                Section sec06Triades = new Section("Tríades e Inversões pelo Braço", "Acordes compactos em qualquer região do braço.", 6);
                Section sec07Pentatonica = new Section("Escalas Pentatônicas e Blues", "A base de todo solo: pentatônica e blue note.", 7);
                Section sec08EscalaModos = new Section("Escala Maior e Modos", "Os 7 modos e como usá-los para improvisar.", 8);
                Section sec09Arpejos = new Section("Arpejos e Acordes de 7ª", "Tétrades e arpejos para um improviso mais rico.", 9);
                Section sec10EscalasMenores = new Section("Escala Menor Natural, Harmônica e Melódica", "As três faces da tonalidade menor.", 10);
                Section sec11Improvisacao = new Section("Improvisação e Vocabulário", "Construindo frases musicais de verdade.", 11);
                Section sec12Composicao = new Section("Composição e Forma Musical", "Escreva sua própria música do zero.", 12);
                Section secQa = new Section("QA - Tipos de Exercício", "Trilha de teste para validar todos os tipos de exercício implementados.", 13);

                for (Section section : List.of(
                        sec01FundamentosBraco, sec02TeoriaEssencial, sec03AcordesAbertos, sec04CampoHarmonico,
                        sec05Caged, sec06Triades, sec07Pentatonica, sec08EscalaModos, sec09Arpejos,
                        sec10EscalasMenores, sec11Improvisacao, sec12Composicao, secQa
                )) {
                    section.setTrack(officialTrack);
                }

                sectionRepository.saveAll(List.of(
                        sec01FundamentosBraco, sec02TeoriaEssencial, sec03AcordesAbertos, sec04CampoHarmonico,
                        sec05Caged, sec06Triades, sec07Pentatonica, sec08EscalaModos, sec09Arpejos,
                        sec10EscalasMenores, sec11Improvisacao, sec12Composicao, secQa
                ));

                // Seção 1 - Módulo 1: Corda Mi grave (casas 0-5)
                String contentSec01Mod01 = """
                    [
                        {
                            "type": "THEORY",
                            "title": "Corda Mi Grave: Primeiras Notas",
                            "text": "A 6ª corda (a mais grave) solta é a nota E. Entre as casas 0 e 5, encontramos apenas 4 notas naturais: E (solta), F (casa 1), G (casa 3) e A (casa 5). Repare que de E para F é apenas 1 casa de distância (semitom), enquanto as outras distâncias são de 2 casas (tom).",
                            "illustration": {
                                "kind": "fretboard",
                                "notes": [
                                    { "string": 6, "fret": 0 },
                                    { "string": 6, "fret": 1 },
                                    { "string": 6, "fret": 3 },
                                    { "string": 6, "fret": 5 }
                                ]
                            }
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Nota E (Corda Solta)",
                            "question": "Selecione a 6ª corda solta (E).",
                            "targetShape": [ { "string": 6, "fret": 0 } ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Nota F",
                            "question": "A partir do E, avance 1 casa (semitom) e localize a nota F.",
                            "targetShape": [ { "string": 6, "fret": 1 } ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Nota G",
                            "question": "A partir do F, avance 1 tom (2 casas) e localize a nota G.",
                            "targetShape": [ { "string": 6, "fret": 3 } ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Nota A",
                            "question": "A partir do G, avance mais 1 tom e localize a nota A.",
                            "targetShape": [ { "string": 6, "fret": 5 } ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "MULTIPLE_CHOICE",
                            "title": "Identifique a Nota",
                            "question": "Qual é o nome da nota marcada (6ª corda, casa 3)?",
                            "options": [ "F", "F#", "G", "G#" ],
                            "correctAnswer": "G",
                            "markedPosition": { "string": 6, "fret": 3 }
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Revisão: As 4 Notas",
                            "question": "Selecione, na 6ª corda, uma ocorrência de cada nota aprendida nesta aula: E, F, G e A.",
                            "targetNotes": [ "E", "F", "G", "A" ]
                        }
                    ]
                """;

                // Seção 1 - Módulo 2: Corda Mi grave (casas 5-12)
                String contentSec01Mod02 = """
                    [
                        {
                            "type": "THEORY",
                            "title": "Corda Mi Grave: Casas 5 a 12",
                            "text": "Continuando a partir do A (casa 5), encontramos mais 3 notas naturais até a casa 12: B (casa 7), C (casa 8) e D (casa 10). Repare que, assim como E-F, o par B-C também tem apenas 1 semitom de distância. Na casa 12 chegamos na oitava do E: a mesma nota da corda solta, um oitava acima.",
                            "illustration": {
                                "kind": "fretboard",
                                "notes": [
                                    { "string": 6, "fret": 5 },
                                    { "string": 6, "fret": 7 },
                                    { "string": 6, "fret": 8 },
                                    { "string": 6, "fret": 10 },
                                    { "string": 6, "fret": 12 }
                                ]
                            }
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Nota B",
                            "question": "A partir do A (casa 5), avance 1 tom (2 casas) e localize a nota B.",
                            "targetShape": [ { "string": 6, "fret": 7 } ]
                        },
                        {
                            "type": "THEORY",
                            "title": "Semitom: B e C",
                            "text": "Assim como E e F, as notas B e C distam apenas 1 semitom (1 casa). Sabendo que o B está na casa 7, o C estará bem ao lado, na casa seguinte.",
                            "illustration": {
                                "kind": "fretboard",
                                "notes": [
                                    { "string": 6, "fret": 7 },
                                    { "string": 6, "fret": 8 }
                                ]
                            }
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Nota C",
                            "question": "Localize a nota C, 1 semitom acima do B.",
                            "targetShape": [ { "string": 6, "fret": 8 } ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Nota D",
                            "question": "A partir do C, avance 1 tom (2 casas) e localize a nota D.",
                            "targetShape": [ { "string": 6, "fret": 10 } ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Oitava do E",
                            "question": "A partir do D, avance 1 tom e localize a oitava do E (a mesma nota da corda solta, uma oitava acima).",
                            "targetShape": [ { "string": 6, "fret": 12 } ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "MULTIPLE_CHOICE",
                            "title": "Identifique a Nota",
                            "question": "Qual é o nome da nota marcada (6ª corda, casa 10)?",
                            "options": [ "C", "C#", "D", "D#" ],
                            "correctAnswer": "D",
                            "markedPosition": { "string": 6, "fret": 10 }
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Revisão: Casas 5 a 12",
                            "question": "Selecione, na 6ª corda, uma ocorrência de cada nota aprendida nesta aula: B, C e D.",
                            "targetNotes": [ "B", "C", "D" ]
                        }
                    ]
                """;

                // Seção 1 - Módulo 3: Tablatura - revisão da corda Mi grave
                String contentSec01Mod03 = """
                    [
                        {
                            "type": "THEORY",
                            "title": "Lendo Tablatura",
                            "text": "Uma tablatura (tab) representa cada corda do instrumento como uma linha horizontal, da mais grave (embaixo) à mais aguda (em cima). Os números escritos sobre cada linha indicam a casa a ser tocada naquela corda. Vamos praticar lendo e tocando sequências apenas na 6ª corda, revisando todas as notas das últimas duas aulas."
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "TAB_READING",
                            "title": "Sequência 1: E-F-G-A",
                            "question": "Reproduza no braço a sequência mostrada na tablatura acima.",
                            "targetSequence": [
                                { "string": 6, "fret": 0 }, { "string": 6, "fret": 1 }, { "string": 6, "fret": 3 }, { "string": 6, "fret": 5 }
                            ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "TAB_READING",
                            "title": "Sequência 2: B-C-D-E",
                            "question": "Reproduza no braço a sequência mostrada na tablatura acima.",
                            "targetSequence": [
                                { "string": 6, "fret": 7 }, { "string": 6, "fret": 8 }, { "string": 6, "fret": 10 }, { "string": 6, "fret": 12 }
                            ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "TAB_READING",
                            "title": "Sequência 3: Frase Melódica",
                            "question": "Reproduza no braço a sequência mostrada na tablatura acima.",
                            "targetSequence": [
                                { "string": 6, "fret": 0 }, { "string": 6, "fret": 3 }, { "string": 6, "fret": 7 },
                                { "string": 6, "fret": 12 }, { "string": 6, "fret": 7 }, { "string": 6, "fret": 3 }, { "string": 6, "fret": 0 }
                            ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "TAB_READING",
                            "title": "Sequência 4: Escala Completa Descendente",
                            "question": "Reproduza no braço a sequência mostrada na tablatura acima, revisando todas as notas da corda Mi grave.",
                            "targetSequence": [
                                { "string": 6, "fret": 12 }, { "string": 6, "fret": 10 }, { "string": 6, "fret": 8 }, { "string": 6, "fret": 7 },
                                { "string": 6, "fret": 5 }, { "string": 6, "fret": 3 }, { "string": 6, "fret": 1 }, { "string": 6, "fret": 0 }
                            ]
                        }
                    ]
                """;

                // Seção 1 - Módulo 4: Corda Lá (casas 0-5)
                String contentSec01Mod04 = """
                    [
                        {
                            "type": "THEORY",
                            "title": "Corda Lá: Primeiras Notas",
                            "text": "A 5ª corda solta é a nota A. Entre as casas 0 e 5, encontramos as notas A (solta), B (casa 2), C (casa 3) e D (casa 5). Assim como em E-F na corda Mi grave, o par B-C aqui também tem apenas 1 semitom de distância.",
                            "illustration": {
                                "kind": "fretboard",
                                "notes": [
                                    { "string": 5, "fret": 0 },
                                    { "string": 5, "fret": 2 },
                                    { "string": 5, "fret": 3 },
                                    { "string": 5, "fret": 5 }
                                ]
                            }
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Nota A (Corda Solta)",
                            "question": "Selecione a 5ª corda solta (A).",
                            "targetShape": [ { "string": 5, "fret": 0 } ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Nota B",
                            "question": "A partir do A, avance 1 tom (2 casas) e localize a nota B.",
                            "targetShape": [ { "string": 5, "fret": 2 } ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Nota C",
                            "question": "A partir do B, avance 1 semitom (1 casa) e localize a nota C.",
                            "targetShape": [ { "string": 5, "fret": 3 } ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Nota D",
                            "question": "A partir do C, avance 1 tom e localize a nota D.",
                            "targetShape": [ { "string": 5, "fret": 5 } ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "MULTIPLE_CHOICE",
                            "title": "Identifique a Nota",
                            "question": "Qual é o nome da nota marcada (5ª corda, casa 3)?",
                            "options": [ "B", "B#", "C", "C#" ],
                            "correctAnswer": "C",
                            "markedPosition": { "string": 5, "fret": 3 }
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Revisão: As 4 Notas",
                            "question": "Selecione, na 5ª corda, uma ocorrência de cada nota aprendida nesta aula: A, B, C e D.",
                            "targetNotes": [ "A", "B", "C", "D" ]
                        }
                    ]
                """;

                // Seção 1 - Módulo 5: Corda Lá (casas 5-12)
                String contentSec01Mod05 = """
                    [
                        {
                            "type": "THEORY",
                            "title": "Corda Lá: Casas 5 a 12",
                            "text": "Continuando a partir do D (casa 5), encontramos mais 3 notas naturais até a casa 12: E (casa 7), F (casa 8) e G (casa 10). Assim como em B-C, o par E-F aqui também tem apenas 1 semitom de distância. Na casa 12 chegamos na oitava do A: a mesma nota da corda solta, uma oitava acima.",
                            "illustration": {
                                "kind": "fretboard",
                                "notes": [
                                    { "string": 5, "fret": 5 },
                                    { "string": 5, "fret": 7 },
                                    { "string": 5, "fret": 8 },
                                    { "string": 5, "fret": 10 },
                                    { "string": 5, "fret": 12 }
                                ]
                            }
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Nota E",
                            "question": "A partir do D (casa 5), avance 1 tom (2 casas) e localize a nota E.",
                            "targetShape": [ { "string": 5, "fret": 7 } ]
                        },
                        {
                            "type": "THEORY",
                            "title": "Semitom: E e F",
                            "text": "Assim como B e C, as notas E e F distam apenas 1 semitom (1 casa). Sabendo que o E está na casa 7, o F estará bem ao lado, na casa seguinte.",
                            "illustration": {
                                "kind": "fretboard",
                                "notes": [
                                    { "string": 5, "fret": 7 },
                                    { "string": 5, "fret": 8 }
                                ]
                            }
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Nota F",
                            "question": "Localize a nota F, 1 semitom acima do E.",
                            "targetShape": [ { "string": 5, "fret": 8 } ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Nota G",
                            "question": "A partir do F, avance 1 tom (2 casas) e localize a nota G.",
                            "targetShape": [ { "string": 5, "fret": 10 } ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Oitava do A",
                            "question": "A partir do G, avance 1 tom e localize a oitava do A (a mesma nota da corda solta, uma oitava acima).",
                            "targetShape": [ { "string": 5, "fret": 12 } ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "MULTIPLE_CHOICE",
                            "title": "Identifique a Nota",
                            "question": "Qual é o nome da nota marcada (5ª corda, casa 10)?",
                            "options": [ "F", "F#", "G", "G#" ],
                            "correctAnswer": "G",
                            "markedPosition": { "string": 5, "fret": 10 }
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Revisão: Casas 5 a 12",
                            "question": "Selecione, na 5ª corda, uma ocorrência de cada nota aprendida nesta aula: E, F e G.",
                            "targetNotes": [ "E", "F", "G" ]
                        }
                    ]
                """;

                String contentSec01Mod06 = """
                    [
                        {
                            "type": "THEORY",
                            "title": "Tablatura: Corda Lá",
                            "text": "Vamos revisar a corda Lá com tablatura, praticando as notas aprendidas nas últimas duas aulas."
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "TAB_READING",
                            "title": "Sequência 1: A-B-C-D",
                            "question": "Reproduza no braço a sequência mostrada na tablatura acima.",
                            "targetSequence": [
                                { "string": 5, "fret": 0 }, { "string": 5, "fret": 2 }, { "string": 5, "fret": 3 }, { "string": 5, "fret": 5 }
                            ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "TAB_READING",
                            "title": "Sequência 2: E-F-G-A",
                            "question": "Reproduza no braço a sequência mostrada na tablatura acima.",
                            "targetSequence": [
                                { "string": 5, "fret": 7 }, { "string": 5, "fret": 8 }, { "string": 5, "fret": 10 }, { "string": 5, "fret": 12 }
                            ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "TAB_READING",
                            "title": "Sequência 3: Frase Melódica",
                            "question": "Reproduza no braço a sequência mostrada na tablatura acima.",
                            "targetSequence": [
                                { "string": 5, "fret": 0 }, { "string": 5, "fret": 3 }, { "string": 5, "fret": 7 },
                                { "string": 5, "fret": 12 }, { "string": 5, "fret": 7 }, { "string": 5, "fret": 3 }, { "string": 5, "fret": 0 }
                            ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "TAB_READING",
                            "title": "Sequência 4: Escala Completa Descendente",
                            "question": "Reproduza no braço a sequência mostrada na tablatura acima, revisando todas as notas da corda Lá.",
                            "targetSequence": [
                                { "string": 5, "fret": 12 }, { "string": 5, "fret": 10 }, { "string": 5, "fret": 8 }, { "string": 5, "fret": 7 },
                                { "string": 5, "fret": 5 }, { "string": 5, "fret": 3 }, { "string": 5, "fret": 2 }, { "string": 5, "fret": 0 }
                            ]
                        }
                    ]
                """;

                String contentSec01Mod07 = """
                    [
                        {
                            "type": "THEORY",
                            "title": "Corda Ré: Primeiras Notas",
                            "text": "A 4ª corda solta é a nota D. Entre as casas 0 e 5, encontramos as notas D (solta), E (casa 2), F (casa 3) e G (casa 5). O par E-F, como em outras cordas, tem apenas 1 semitom de distância.",
                            "illustration": {
                                "kind": "fretboard",
                                "notes": [
                                    { "string": 4, "fret": 0 },
                                    { "string": 4, "fret": 2 },
                                    { "string": 4, "fret": 3 },
                                    { "string": 4, "fret": 5 }
                                ]
                            }
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Nota D (Corda Solta)",
                            "question": "Selecione a 4ª corda solta (D).",
                            "targetShape": [ { "string": 4, "fret": 0 } ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Nota E",
                            "question": "A partir do D, avance 1 tom (2 casas) e localize a nota E.",
                            "targetShape": [ { "string": 4, "fret": 2 } ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Nota F",
                            "question": "A partir do E, avance 1 semitom (1 casa) e localize a nota F.",
                            "targetShape": [ { "string": 4, "fret": 3 } ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Nota G",
                            "question": "A partir do F, avance 1 tom e localize a nota G.",
                            "targetShape": [ { "string": 4, "fret": 5 } ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "MULTIPLE_CHOICE",
                            "title": "Identifique a Nota",
                            "question": "Qual é o nome da nota marcada (4ª corda, casa 3)?",
                            "options": [ "E", "E#", "F", "F#" ],
                            "correctAnswer": "F",
                            "markedPosition": { "string": 4, "fret": 3 }
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Revisão: As 4 Notas",
                            "question": "Selecione, na 4ª corda, uma ocorrência de cada nota aprendida nesta aula: D, E, F e G.",
                            "targetNotes": [ "D", "E", "F", "G" ]
                        }
                    ]
                """;

                String contentSec01Mod08 = """
                    [
                        {
                            "type": "THEORY",
                            "title": "Corda Ré: Casas 5 a 12",
                            "text": "Continuando a partir do G (casa 5), encontramos mais 3 notas naturais até a casa 12: A (casa 7), B (casa 9) e C (casa 10). O par B-C tem apenas 1 semitom de distância. Na casa 12 chegamos na oitava do D: a mesma nota da corda solta, uma oitava acima.",
                            "illustration": {
                                "kind": "fretboard",
                                "notes": [
                                    { "string": 4, "fret": 5 },
                                    { "string": 4, "fret": 7 },
                                    { "string": 4, "fret": 9 },
                                    { "string": 4, "fret": 10 },
                                    { "string": 4, "fret": 12 }
                                ]
                            }
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Nota A",
                            "question": "A partir do G (casa 5), avance 1 tom (2 casas) e localize a nota A.",
                            "targetShape": [ { "string": 4, "fret": 7 } ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Nota B",
                            "question": "A partir do A, avance 1 tom e localize a nota B.",
                            "targetShape": [ { "string": 4, "fret": 9 } ]
                        },
                        {
                            "type": "THEORY",
                            "title": "Semitom: B e C",
                            "text": "Assim como em outras cordas, as notas B e C aqui também distam apenas 1 semitom (1 casa). Sabendo que o B está na casa 9, o C estará bem ao lado, na casa seguinte.",
                            "illustration": {
                                "kind": "fretboard",
                                "notes": [
                                    { "string": 4, "fret": 9 },
                                    { "string": 4, "fret": 10 }
                                ]
                            }
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Nota C",
                            "question": "Localize a nota C, 1 semitom acima do B.",
                            "targetShape": [ { "string": 4, "fret": 10 } ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Oitava do D",
                            "question": "A partir do C, avance 1 tom e localize a oitava do D (a mesma nota da corda solta, uma oitava acima).",
                            "targetShape": [ { "string": 4, "fret": 12 } ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "MULTIPLE_CHOICE",
                            "title": "Identifique a Nota",
                            "question": "Qual é o nome da nota marcada (4ª corda, casa 10)?",
                            "options": [ "B", "B#", "C", "C#" ],
                            "correctAnswer": "C",
                            "markedPosition": { "string": 4, "fret": 10 }
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Revisão: Casas 5 a 12",
                            "question": "Selecione, na 4ª corda, uma ocorrência de cada nota aprendida nesta aula: A, B e C.",
                            "targetNotes": [ "A", "B", "C" ]
                        }
                    ]
                """;

                String contentSec01Mod09 = """
                    [
                        {
                            "type": "THEORY",
                            "title": "Tablatura: Corda Ré",
                            "text": "Vamos revisar a corda Ré com tablatura, praticando as notas aprendidas nas últimas duas aulas."
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "TAB_READING",
                            "title": "Sequência 1: D-E-F-G",
                            "question": "Reproduza no braço a sequência mostrada na tablatura acima.",
                            "targetSequence": [
                                { "string": 4, "fret": 0 }, { "string": 4, "fret": 2 }, { "string": 4, "fret": 3 }, { "string": 4, "fret": 5 }
                            ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "TAB_READING",
                            "title": "Sequência 2: A-B-C-D",
                            "question": "Reproduza no braço a sequência mostrada na tablatura acima.",
                            "targetSequence": [
                                { "string": 4, "fret": 7 }, { "string": 4, "fret": 9 }, { "string": 4, "fret": 10 }, { "string": 4, "fret": 12 }
                            ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "TAB_READING",
                            "title": "Sequência 3: Frase Melódica",
                            "question": "Reproduza no braço a sequência mostrada na tablatura acima.",
                            "targetSequence": [
                                { "string": 4, "fret": 0 }, { "string": 4, "fret": 3 }, { "string": 4, "fret": 7 },
                                { "string": 4, "fret": 12 }, { "string": 4, "fret": 7 }, { "string": 4, "fret": 3 }, { "string": 4, "fret": 0 }
                            ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "TAB_READING",
                            "title": "Sequência 4: Escala Completa Descendente",
                            "question": "Reproduza no braço a sequência mostrada na tablatura acima, revisando todas as notas da corda Ré.",
                            "targetSequence": [
                                { "string": 4, "fret": 12 }, { "string": 4, "fret": 10 }, { "string": 4, "fret": 9 }, { "string": 4, "fret": 7 },
                                { "string": 4, "fret": 5 }, { "string": 4, "fret": 3 }, { "string": 4, "fret": 2 }, { "string": 4, "fret": 0 }
                            ]
                        }
                    ]
                """;

                String contentSec01Mod10 = """
                    [
                        {
                            "type": "THEORY",
                            "title": "Corda Sol: Primeiras Notas",
                            "text": "A 3ª corda solta é a nota G. Entre as casas 0 e 5, encontramos as notas G (solta), A (casa 2), B (casa 4) e C (casa 5). O par B-C tem apenas 1 semitom de distância.",
                            "illustration": {
                                "kind": "fretboard",
                                "notes": [
                                    { "string": 3, "fret": 0 },
                                    { "string": 3, "fret": 2 },
                                    { "string": 3, "fret": 4 },
                                    { "string": 3, "fret": 5 }
                                ]
                            }
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Nota G (Corda Solta)",
                            "question": "Selecione a 3ª corda solta (G).",
                            "targetShape": [ { "string": 3, "fret": 0 } ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Nota A",
                            "question": "A partir do G, avance 1 tom (2 casas) e localize a nota A.",
                            "targetShape": [ { "string": 3, "fret": 2 } ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Nota B",
                            "question": "A partir do A, avance 1 tom e localize a nota B.",
                            "targetShape": [ { "string": 3, "fret": 4 } ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Nota C",
                            "question": "A partir do B, avance 1 semitom (1 casa) e localize a nota C.",
                            "targetShape": [ { "string": 3, "fret": 5 } ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "MULTIPLE_CHOICE",
                            "title": "Identifique a Nota",
                            "question": "Qual é o nome da nota marcada (3ª corda, casa 4)?",
                            "options": [ "A#", "B", "C", "C#" ],
                            "correctAnswer": "B",
                            "markedPosition": { "string": 3, "fret": 4 }
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Revisão: As 4 Notas",
                            "question": "Selecione, na 3ª corda, uma ocorrência de cada nota aprendida nesta aula: G, A, B e C.",
                            "targetNotes": [ "G", "A", "B", "C" ]
                        }
                    ]
                """;

                String contentSec01Mod11 = """
                    [
                        {
                            "type": "THEORY",
                            "title": "Corda Sol: Casas 5 a 12",
                            "text": "Continuando a partir do C (casa 5), encontramos mais 3 notas naturais até a casa 12: D (casa 7), E (casa 9) e F (casa 10). O par E-F tem apenas 1 semitom de distância. Na casa 12 chegamos na oitava do G: a mesma nota da corda solta, uma oitava acima.",
                            "illustration": {
                                "kind": "fretboard",
                                "notes": [
                                    { "string": 3, "fret": 5 },
                                    { "string": 3, "fret": 7 },
                                    { "string": 3, "fret": 9 },
                                    { "string": 3, "fret": 10 },
                                    { "string": 3, "fret": 12 }
                                ]
                            }
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Nota D",
                            "question": "A partir do C (casa 5), avance 1 tom (2 casas) e localize a nota D.",
                            "targetShape": [ { "string": 3, "fret": 7 } ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Nota E",
                            "question": "A partir do D, avance 1 tom e localize a nota E.",
                            "targetShape": [ { "string": 3, "fret": 9 } ]
                        },
                        {
                            "type": "THEORY",
                            "title": "Semitom: E e F",
                            "text": "Assim como em outras cordas, as notas E e F aqui também distam apenas 1 semitom (1 casa). Sabendo que o E está na casa 9, o F estará bem ao lado, na casa seguinte.",
                            "illustration": {
                                "kind": "fretboard",
                                "notes": [
                                    { "string": 3, "fret": 9 },
                                    { "string": 3, "fret": 10 }
                                ]
                            }
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Nota F",
                            "question": "Localize a nota F, 1 semitom acima do E.",
                            "targetShape": [ { "string": 3, "fret": 10 } ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Oitava do G",
                            "question": "A partir do F, avance 1 tom e localize a oitava do G (a mesma nota da corda solta, uma oitava acima).",
                            "targetShape": [ { "string": 3, "fret": 12 } ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "MULTIPLE_CHOICE",
                            "title": "Identifique a Nota",
                            "question": "Qual é o nome da nota marcada (3ª corda, casa 7)?",
                            "options": [ "C#", "D", "D#", "E" ],
                            "correctAnswer": "D",
                            "markedPosition": { "string": 3, "fret": 7 }
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Revisão: Casas 5 a 12",
                            "question": "Selecione, na 3ª corda, uma ocorrência de cada nota aprendida nesta aula: D, E e F.",
                            "targetNotes": [ "D", "E", "F" ]
                        }
                    ]
                """;

                String contentSec01Mod12 = """
                    [
                        {
                            "type": "THEORY",
                            "title": "Tablatura: Corda Sol",
                            "text": "Vamos revisar a corda Sol com tablatura, praticando as notas aprendidas nas últimas duas aulas."
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "TAB_READING",
                            "title": "Sequência 1: G-A-B-C",
                            "question": "Reproduza no braço a sequência mostrada na tablatura acima.",
                            "targetSequence": [
                                { "string": 3, "fret": 0 }, { "string": 3, "fret": 2 }, { "string": 3, "fret": 4 }, { "string": 3, "fret": 5 }
                            ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "TAB_READING",
                            "title": "Sequência 2: D-E-F-G",
                            "question": "Reproduza no braço a sequência mostrada na tablatura acima.",
                            "targetSequence": [
                                { "string": 3, "fret": 7 }, { "string": 3, "fret": 9 }, { "string": 3, "fret": 10 }, { "string": 3, "fret": 12 }
                            ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "TAB_READING",
                            "title": "Sequência 3: Frase Melódica",
                            "question": "Reproduza no braço a sequência mostrada na tablatura acima.",
                            "targetSequence": [
                                { "string": 3, "fret": 0 }, { "string": 3, "fret": 4 }, { "string": 3, "fret": 7 },
                                { "string": 3, "fret": 12 }, { "string": 3, "fret": 7 }, { "string": 3, "fret": 4 }, { "string": 3, "fret": 0 }
                            ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "TAB_READING",
                            "title": "Sequência 4: Escala Completa Descendente",
                            "question": "Reproduza no braço a sequência mostrada na tablatura acima, revisando todas as notas da corda Sol.",
                            "targetSequence": [
                                { "string": 3, "fret": 12 }, { "string": 3, "fret": 10 }, { "string": 3, "fret": 9 }, { "string": 3, "fret": 7 },
                                { "string": 3, "fret": 5 }, { "string": 3, "fret": 4 }, { "string": 3, "fret": 2 }, { "string": 3, "fret": 0 }
                            ]
                        }
                    ]
                """;

                String contentSec01Mod13 = """
                    [
                        {
                            "type": "THEORY",
                            "title": "Corda Si: Primeiras Notas",
                            "text": "A 2ª corda solta é a nota B. Entre as casas 0 e 5, encontramos as notas B (solta), C (casa 1), D (casa 3) e E (casa 5). O par B-C tem apenas 1 semitom de distância, então logo na primeira casa já mudamos de nota.",
                            "illustration": {
                                "kind": "fretboard",
                                "notes": [
                                    { "string": 2, "fret": 0 },
                                    { "string": 2, "fret": 1 },
                                    { "string": 2, "fret": 3 },
                                    { "string": 2, "fret": 5 }
                                ]
                            }
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Nota B (Corda Solta)",
                            "question": "Selecione a 2ª corda solta (B).",
                            "targetShape": [ { "string": 2, "fret": 0 } ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Nota C",
                            "question": "A partir do B, avance 1 semitom (1 casa) e localize a nota C.",
                            "targetShape": [ { "string": 2, "fret": 1 } ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Nota D",
                            "question": "A partir do C, avance 1 tom (2 casas) e localize a nota D.",
                            "targetShape": [ { "string": 2, "fret": 3 } ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Nota E",
                            "question": "A partir do D, avance 1 tom e localize a nota E.",
                            "targetShape": [ { "string": 2, "fret": 5 } ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "MULTIPLE_CHOICE",
                            "title": "Identifique a Nota",
                            "question": "Qual é o nome da nota marcada (2ª corda, casa 1)?",
                            "options": [ "A#", "B", "C", "C#" ],
                            "correctAnswer": "C",
                            "markedPosition": { "string": 2, "fret": 1 }
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Revisão: As 4 Notas",
                            "question": "Selecione, na 2ª corda, uma ocorrência de cada nota aprendida nesta aula: B, C, D e E.",
                            "targetNotes": [ "B", "C", "D", "E" ]
                        }
                    ]
                """;

                String contentSec01Mod14 = """
                    [
                        {
                            "type": "THEORY",
                            "title": "Corda Si: Casas 5 a 12",
                            "text": "Continuando a partir do E (casa 5), encontramos mais 3 notas naturais até a casa 12: F (casa 6), G (casa 8) e A (casa 10). Assim como B-C, o par E-F também tem apenas 1 semitom de distância, então logo na próxima casa já mudamos de nota. Na casa 12 chegamos na oitava do B: a mesma nota da corda solta, uma oitava acima.",
                            "illustration": {
                                "kind": "fretboard",
                                "notes": [
                                    { "string": 2, "fret": 5 },
                                    { "string": 2, "fret": 6 },
                                    { "string": 2, "fret": 8 },
                                    { "string": 2, "fret": 10 },
                                    { "string": 2, "fret": 12 }
                                ]
                            }
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Nota F",
                            "question": "A partir do E (casa 5), avance 1 semitom (1 casa) e localize a nota F.",
                            "targetShape": [ { "string": 2, "fret": 6 } ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Nota G",
                            "question": "A partir do F, avance 1 tom (2 casas) e localize a nota G.",
                            "targetShape": [ { "string": 2, "fret": 8 } ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Nota A",
                            "question": "A partir do G, avance 1 tom e localize a nota A.",
                            "targetShape": [ { "string": 2, "fret": 10 } ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Oitava do B",
                            "question": "A partir do A, avance 1 tom e localize a oitava do B (a mesma nota da corda solta, uma oitava acima).",
                            "targetShape": [ { "string": 2, "fret": 12 } ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "MULTIPLE_CHOICE",
                            "title": "Identifique a Nota",
                            "question": "Qual é o nome da nota marcada (2ª corda, casa 8)?",
                            "options": [ "F#", "G", "G#", "A" ],
                            "correctAnswer": "G",
                            "markedPosition": { "string": 2, "fret": 8 }
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Revisão: Casas 5 a 12",
                            "question": "Selecione, na 2ª corda, uma ocorrência de cada nota aprendida nesta aula: F, G e A.",
                            "targetNotes": [ "F", "G", "A" ]
                        }
                    ]
                """;

                String contentSec01Mod15 = """
                    [
                        {
                            "type": "THEORY",
                            "title": "Tablatura: Corda Si",
                            "text": "Vamos revisar a corda Si com tablatura, praticando as notas aprendidas nas últimas duas aulas."
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "TAB_READING",
                            "title": "Sequência 1: B-C-D-E",
                            "question": "Reproduza no braço a sequência mostrada na tablatura acima.",
                            "targetSequence": [
                                { "string": 2, "fret": 0 }, { "string": 2, "fret": 1 }, { "string": 2, "fret": 3 }, { "string": 2, "fret": 5 }
                            ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "TAB_READING",
                            "title": "Sequência 2: F-G-A-B",
                            "question": "Reproduza no braço a sequência mostrada na tablatura acima.",
                            "targetSequence": [
                                { "string": 2, "fret": 6 }, { "string": 2, "fret": 8 }, { "string": 2, "fret": 10 }, { "string": 2, "fret": 12 }
                            ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "TAB_READING",
                            "title": "Sequência 3: Frase Melódica",
                            "question": "Reproduza no braço a sequência mostrada na tablatura acima.",
                            "targetSequence": [
                                { "string": 2, "fret": 0 }, { "string": 2, "fret": 3 }, { "string": 2, "fret": 6 },
                                { "string": 2, "fret": 12 }, { "string": 2, "fret": 6 }, { "string": 2, "fret": 3 }, { "string": 2, "fret": 0 }
                            ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "TAB_READING",
                            "title": "Sequência 4: Escala Completa Descendente",
                            "question": "Reproduza no braço a sequência mostrada na tablatura acima, revisando todas as notas da corda Si.",
                            "targetSequence": [
                                { "string": 2, "fret": 12 }, { "string": 2, "fret": 10 }, { "string": 2, "fret": 8 }, { "string": 2, "fret": 6 },
                                { "string": 2, "fret": 5 }, { "string": 2, "fret": 3 }, { "string": 2, "fret": 1 }, { "string": 2, "fret": 0 }
                            ]
                        }
                    ]
                """;

                String contentSec01Mod16 = """
                    [
                        {
                            "type": "THEORY",
                            "title": "Corda Mi Agudo: Primeiras Notas",
                            "text": "A 1ª corda (a mais aguda) solta também é a nota E, assim como a 6ª corda (a mais grave) — só que uma oitava mais alta. Por isso, o padrão de casas é idêntico ao que você já aprendeu: E (solta), F (casa 1), G (casa 3) e A (casa 5).",
                            "illustration": {
                                "kind": "fretboard",
                                "notes": [
                                    { "string": 1, "fret": 0 },
                                    { "string": 1, "fret": 1 },
                                    { "string": 1, "fret": 3 },
                                    { "string": 1, "fret": 5 }
                                ]
                            }
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Nota E (Corda Solta)",
                            "question": "Selecione a 1ª corda solta (E).",
                            "targetShape": [ { "string": 1, "fret": 0 } ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Nota F",
                            "question": "A partir do E, avance 1 casa (semitom) e localize a nota F.",
                            "targetShape": [ { "string": 1, "fret": 1 } ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Nota G",
                            "question": "A partir do F, avance 1 tom (2 casas) e localize a nota G.",
                            "targetShape": [ { "string": 1, "fret": 3 } ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Nota A",
                            "question": "A partir do G, avance mais 1 tom e localize a nota A.",
                            "targetShape": [ { "string": 1, "fret": 5 } ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "MULTIPLE_CHOICE",
                            "title": "Identifique a Nota",
                            "question": "Qual é o nome da nota marcada (1ª corda, casa 3)?",
                            "options": [ "F", "F#", "G", "G#" ],
                            "correctAnswer": "G",
                            "markedPosition": { "string": 1, "fret": 3 }
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Revisão: As 4 Notas",
                            "question": "Selecione, na 1ª corda, uma ocorrência de cada nota aprendida nesta aula: E, F, G e A.",
                            "targetNotes": [ "E", "F", "G", "A" ]
                        }
                    ]
                """;

                String contentSec01Mod17 = """
                    [
                        {
                            "type": "THEORY",
                            "title": "Corda Mi Agudo: Casas 5 a 12",
                            "text": "Continuando a partir do A (casa 5), o padrão se repete igual à corda Mi grave: B (casa 7), C (casa 8) e D (casa 10), com o mesmo semitom entre B e C. Na casa 12 chegamos na oitava do E.",
                            "illustration": {
                                "kind": "fretboard",
                                "notes": [
                                    { "string": 1, "fret": 5 },
                                    { "string": 1, "fret": 7 },
                                    { "string": 1, "fret": 8 },
                                    { "string": 1, "fret": 10 },
                                    { "string": 1, "fret": 12 }
                                ]
                            }
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Nota B",
                            "question": "A partir do A (casa 5), avance 1 tom (2 casas) e localize a nota B.",
                            "targetShape": [ { "string": 1, "fret": 7 } ]
                        },
                        {
                            "type": "THEORY",
                            "title": "Semitom: B e C",
                            "text": "Mais uma vez, B e C distam apenas 1 semitom (1 casa). Sabendo que o B está na casa 7, o C estará bem ao lado, na casa seguinte.",
                            "illustration": {
                                "kind": "fretboard",
                                "notes": [
                                    { "string": 1, "fret": 7 },
                                    { "string": 1, "fret": 8 }
                                ]
                            }
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Nota C",
                            "question": "Localize a nota C, 1 semitom acima do B.",
                            "targetShape": [ { "string": 1, "fret": 8 } ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Nota D",
                            "question": "A partir do C, avance 1 tom (2 casas) e localize a nota D.",
                            "targetShape": [ { "string": 1, "fret": 10 } ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Oitava do E",
                            "question": "A partir do D, avance 1 tom e localize a oitava do E (a mesma nota da corda solta, uma oitava acima).",
                            "targetShape": [ { "string": 1, "fret": 12 } ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "MULTIPLE_CHOICE",
                            "title": "Identifique a Nota",
                            "question": "Qual é o nome da nota marcada (1ª corda, casa 10)?",
                            "options": [ "C", "C#", "D", "D#" ],
                            "correctAnswer": "D",
                            "markedPosition": { "string": 1, "fret": 10 }
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Revisão: Casas 5 a 12",
                            "question": "Selecione, na 1ª corda, uma ocorrência de cada nota aprendida nesta aula: B, C e D.",
                            "targetNotes": [ "B", "C", "D" ]
                        }
                    ]
                """;

                String contentSec01Mod18 = """
                    [
                        {
                            "type": "THEORY",
                            "title": "Tablatura: Corda Mi Agudo",
                            "text": "Vamos revisar a corda Mi agudo com tablatura, praticando as notas aprendidas nas últimas duas aulas."
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "TAB_READING",
                            "title": "Sequência 1: E-F-G-A",
                            "question": "Reproduza no braço a sequência mostrada na tablatura acima.",
                            "targetSequence": [
                                { "string": 1, "fret": 0 }, { "string": 1, "fret": 1 }, { "string": 1, "fret": 3 }, { "string": 1, "fret": 5 }
                            ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "TAB_READING",
                            "title": "Sequência 2: B-C-D-E",
                            "question": "Reproduza no braço a sequência mostrada na tablatura acima.",
                            "targetSequence": [
                                { "string": 1, "fret": 7 }, { "string": 1, "fret": 8 }, { "string": 1, "fret": 10 }, { "string": 1, "fret": 12 }
                            ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "TAB_READING",
                            "title": "Sequência 3: Frase Melódica",
                            "question": "Reproduza no braço a sequência mostrada na tablatura acima.",
                            "targetSequence": [
                                { "string": 1, "fret": 0 }, { "string": 1, "fret": 3 }, { "string": 1, "fret": 7 },
                                { "string": 1, "fret": 12 }, { "string": 1, "fret": 7 }, { "string": 1, "fret": 3 }, { "string": 1, "fret": 0 }
                            ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "TAB_READING",
                            "title": "Sequência 4: Escala Completa Descendente",
                            "question": "Reproduza no braço a sequência mostrada na tablatura acima, revisando todas as notas da corda Mi agudo.",
                            "targetSequence": [
                                { "string": 1, "fret": 12 }, { "string": 1, "fret": 10 }, { "string": 1, "fret": 8 }, { "string": 1, "fret": 7 },
                                { "string": 1, "fret": 5 }, { "string": 1, "fret": 3 }, { "string": 1, "fret": 1 }, { "string": 1, "fret": 0 }
                            ]
                        }
                    ]
                """;

                String contentSec01Mod19 = """
                    [
                        {
                            "type": "THEORY",
                            "title": "O Sustenido (#)",
                            "text": "O sustenido eleva a nota em 1 semitom, ou seja, avança 1 casa em direção ao corpo do instrumento. Vamos praticar sustenidos nas 3 cordas mais graves: Mi grave, Lá e Ré.",
                            "illustration": {
                                "kind": "fretboard",
                                "notes": [
                                    { "string": 6, "fret": 1 },
                                    { "string": 6, "fret": 2 }
                                ]
                            }
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "F# na Corda Mi Grave",
                            "question": "Encontre o F natural na 6ª corda e avance 1 semitom para marcar o F#.",
                            "targetShape": [ { "string": 6, "fret": 2 } ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "C# na Corda Lá",
                            "question": "Encontre o C natural na 5ª corda e avance 1 semitom para marcar o C#.",
                            "targetShape": [ { "string": 5, "fret": 4 } ]
                        },
                        {
                            "type": "THEORY",
                            "title": "O Bemol (b)",
                            "text": "O bemol abaixa a nota em 1 semitom, ou seja, recua 1 casa em direção à mão (headstock) do instrumento.",
                            "illustration": {
                                "kind": "fretboard",
                                "notes": [
                                    { "string": 5, "fret": 2 },
                                    { "string": 5, "fret": 1 }
                                ]
                            }
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Bb na Corda Lá",
                            "question": "Encontre o B natural na 5ª corda e recue 1 semitom para marcar o Bb.",
                            "targetShape": [ { "string": 5, "fret": 1 } ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Eb na Corda Ré",
                            "question": "Encontre o E natural na 4ª corda e recue 1 semitom para marcar o Eb.",
                            "targetShape": [ { "string": 4, "fret": 1 } ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "MULTIPLE_CHOICE",
                            "title": "Identifique a Nota",
                            "question": "Qual é o nome da nota marcada (6ª corda, casa 2)?",
                            "options": [ "F", "F#", "G", "G#" ],
                            "correctAnswer": "F#",
                            "markedPosition": { "string": 6, "fret": 2 }
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Revisão: Acidentes nas Cordas Graves",
                            "question": "Marque, ao mesmo tempo, o F# na 6ª corda e o C# na 5ª corda.",
                            "targetShape": [ { "string": 6, "fret": 2 }, { "string": 5, "fret": 4 } ]
                        }
                    ]
                """;

                String contentSec01Mod20 = """
                    [
                        {
                            "type": "THEORY",
                            "title": "Acidentes nas Cordas Agudas",
                            "text": "A mesma lógica de sustenidos e bemóis vale para qualquer corda: avance 1 casa para o sustenido, recue 1 casa para o bemol. Vamos praticar nas 3 cordas mais agudas: Sol, Si e Mi agudo."
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "G# na Corda Sol",
                            "question": "Encontre o G natural (corda solta) na 3ª corda e avance 1 semitom para marcar o G#.",
                            "targetShape": [ { "string": 3, "fret": 1 } ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "C# na Corda Si",
                            "question": "Encontre o C natural na 2ª corda e avance 1 semitom para marcar o C#.",
                            "targetShape": [ { "string": 2, "fret": 2 } ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Bb na Corda Mi Agudo",
                            "question": "Encontre o B natural na 1ª corda e recue 1 semitom para marcar o Bb.",
                            "targetShape": [ { "string": 1, "fret": 6 } ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Eb na Corda Si",
                            "question": "Encontre o E natural na 2ª corda e recue 1 semitom para marcar o Eb.",
                            "targetShape": [ { "string": 2, "fret": 4 } ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "MULTIPLE_CHOICE",
                            "title": "Identifique a Nota",
                            "question": "Qual é o nome da nota marcada (3ª corda, casa 1)?",
                            "options": [ "G", "G#", "A", "A#" ],
                            "correctAnswer": "G#",
                            "markedPosition": { "string": 3, "fret": 1 }
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Revisão: Acidentes nas Cordas Agudas",
                            "question": "Marque, ao mesmo tempo, o G# na 3ª corda e o Bb na 1ª corda.",
                            "targetShape": [ { "string": 3, "fret": 1 }, { "string": 1, "fret": 6 } ]
                        }
                    ]
                """;

                String contentSec01Mod21 = """
                    [
                        {
                            "type": "THEORY",
                            "title": "Oitavas",
                            "text": "Uma oitava é a repetição da mesma nota em uma região mais aguda. O shape visual mais comum parte da 6ª corda e alcança a 4ª corda.",
                            "illustration": {
                                "kind": "fretboard",
                                "notes": [
                                    { "string": 6, "fret": 3 },
                                    { "string": 4, "fret": 5 }
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
                                    { "string": 6, "fret": 5 },
                                    { "string": 4, "fret": 7 }
                                ]
                            }
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Oitava de A",
                            "question": "Selecione as duas notas que formam a oitava de A (tônica na 6ª corda, casa 5).",
                            "targetShape": [ { "string": 6, "fret": 5 }, { "string": 4, "fret": 7 } ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Oitava de C",
                            "question": "Encontre o C na 6ª corda e marque sua respectiva oitava na 4ª corda.",
                            "targetShape": [ { "string": 6, "fret": 8 }, { "string": 4, "fret": 10 } ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Encontrando a Tônica",
                            "question": "Dado que a 4ª corda, casa 2, é um E, encontre esta nota e selecione o E de origem na 6ª corda solta.",
                            "targetShape": [ { "string": 6, "fret": 0 }, { "string": 4, "fret": 2 } ]
                        }
                    ]
                """;

                String contentSec01Mod22 = """
                    [
                        {
                            "type": "THEORY",
                            "title": "O Shape 5-3",
                            "text": "O mesmo princípio de oitava vale para outros pares de corda: pulando a 4ª corda, a oitava de uma nota na 5ª corda aparece 2 casas à frente na 3ª corda — a mesma distância do shape 6-4.",
                            "illustration": {
                                "kind": "fretboard",
                                "notes": [
                                    { "string": 5, "fret": 3 },
                                    { "string": 3, "fret": 5 }
                                ]
                            }
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Oitava de D",
                            "question": "Selecione as duas notas que formam a oitava de D (tônica na 5ª corda, casa 5).",
                            "targetShape": [ { "string": 5, "fret": 5 }, { "string": 3, "fret": 7 } ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Oitava de E",
                            "question": "Encontre o E na 5ª corda e marque sua respectiva oitava na 3ª corda.",
                            "targetShape": [ { "string": 5, "fret": 7 }, { "string": 3, "fret": 9 } ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Encontrando a Tônica",
                            "question": "Dado que a 3ª corda, casa 2, é um A, encontre esta nota e selecione o A de origem na 5ª corda solta.",
                            "targetShape": [ { "string": 5, "fret": 0 }, { "string": 3, "fret": 2 } ]
                        }
                    ]
                """;

                String contentSec01Mod23 = """
                    [
                        {
                            "type": "THEORY",
                            "title": "O Shape 4-2 (Atenção à Mudança)",
                            "text": "Aqui o shape muda! Entre as cordas Sol e Si existe um intervalo especial: uma 3ª maior, em vez da 4ª justa que existe entre as outras cordas vizinhas. Por causa disso, pulando a 3ª corda, a oitava de uma nota na 4ª corda aparece 3 casas à frente na 2ª corda — uma casa a mais que nos outros shapes.",
                            "illustration": {
                                "kind": "fretboard",
                                "notes": [
                                    { "string": 4, "fret": 0 },
                                    { "string": 2, "fret": 3 }
                                ]
                            }
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Oitava de E",
                            "question": "Selecione as duas notas que formam a oitava de E (tônica na 4ª corda, casa 2).",
                            "targetShape": [ { "string": 4, "fret": 2 }, { "string": 2, "fret": 5 } ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Oitava de G",
                            "question": "Encontre o G na 4ª corda e marque sua respectiva oitava na 2ª corda.",
                            "targetShape": [ { "string": 4, "fret": 5 }, { "string": 2, "fret": 8 } ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Encontrando a Tônica",
                            "question": "Marque o D na 4ª corda solta e sua respectiva oitava na 2ª corda.",
                            "targetShape": [ { "string": 4, "fret": 0 }, { "string": 2, "fret": 3 } ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "MULTIPLE_CHOICE",
                            "title": "Por Que Esse Shape Muda?",
                            "question": "Por que o shape de oitava entre as cordas Ré e Si precisa de 3 casas em vez de 2, diferente dos outros pares de corda?",
                            "options": [
                                "Porque a corda Ré está desafinada",
                                "Por causa do intervalo especial (3ª maior) entre as cordas Sol e Si",
                                "Porque pulamos 2 cordas em vez de 1",
                                "Na verdade não muda, é igual aos outros"
                            ],
                            "correctAnswer": "Por causa do intervalo especial (3ª maior) entre as cordas Sol e Si"
                        }
                    ]
                """;

                String contentSec01Mod24 = """
                    [
                        {
                            "type": "THEORY",
                            "title": "Melodia com Todas as Cordas",
                            "text": "Chegou a hora de tocar uma melodia usando as 6 cordas juntas. Vamos começar com as cordas soltas, subindo da mais grave para a mais aguda e voltando."
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "TAB_READING",
                            "title": "Sequência 1: Cordas Soltas (Sobe e Desce)",
                            "question": "Reproduza no braço a sequência mostrada na tablatura acima.",
                            "targetSequence": [
                                { "string": 6, "fret": 0 }, { "string": 5, "fret": 0 }, { "string": 4, "fret": 0 },
                                { "string": 3, "fret": 0 }, { "string": 2, "fret": 0 }, { "string": 1, "fret": 0 },
                                { "string": 2, "fret": 0 }, { "string": 3, "fret": 0 }, { "string": 4, "fret": 0 },
                                { "string": 5, "fret": 0 }, { "string": 6, "fret": 0 }
                            ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "TAB_READING",
                            "title": "Sequência 2: Alcançando o Topo",
                            "question": "Reproduza no braço a sequência mostrada na tablatura acima.",
                            "targetSequence": [
                                { "string": 6, "fret": 0 }, { "string": 5, "fret": 0 }, { "string": 4, "fret": 0 },
                                { "string": 3, "fret": 0 }, { "string": 2, "fret": 0 }, { "string": 1, "fret": 0 },
                                { "string": 1, "fret": 3 }, { "string": 1, "fret": 0 }, { "string": 2, "fret": 0 },
                                { "string": 3, "fret": 0 }, { "string": 4, "fret": 0 }, { "string": 5, "fret": 0 },
                                { "string": 6, "fret": 0 }
                            ]
                        }
                    ]
                """;

                String contentSec01Mod25 = """
                    [
                        {
                            "type": "THEORY",
                            "title": "Revisão Geral",
                            "text": "Você já conhece as notas naturais em todas as 6 cordas. Agora vamos testar sua velocidade de reconhecimento, misturando cordas diferentes sem aviso prévio."
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Reconhecimento Rápido: C",
                            "question": "Localize rapidamente a nota C na 6ª corda.",
                            "targetShape": [ { "string": 6, "fret": 8 } ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Reconhecimento Rápido: G",
                            "question": "Localize rapidamente a nota G na 5ª corda.",
                            "targetShape": [ { "string": 5, "fret": 10 } ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Reconhecimento Rápido: E",
                            "question": "Localize rapidamente a nota E na 3ª corda.",
                            "targetShape": [ { "string": 3, "fret": 9 } ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "SHAPE_MATCH",
                            "title": "Reconhecimento Rápido: F",
                            "question": "Localize rapidamente a nota F na 2ª corda.",
                            "targetShape": [ { "string": 2, "fret": 6 } ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "MULTIPLE_CHOICE",
                            "title": "Identifique a Nota",
                            "question": "Qual é o nome da nota marcada (1ª corda, casa 5)?",
                            "options": [ "G#", "A", "A#", "B" ],
                            "correctAnswer": "A",
                            "markedPosition": { "string": 1, "fret": 5 }
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "FIND_ALL_OCCURRENCES",
                            "title": "Todas as Ocorrências de E",
                            "question": "Agora que você conhece todas as cordas, marque todas as ocorrências da nota E até a casa 12, em qualquer corda.",
                            "targetNote": "E",
                            "maxFret": 12
                        }
                    ]
                """;

                String contentSec01Mod26 = """
                    [
                        {
                            "type": "THEORY",
                            "title": "Chegamos ao Fim da Seção!",
                            "text": "Para fechar esta seção, vamos tocar duas sequências finais que usam tudo o que você aprendeu, misturando várias cordas: um arpejo de C maior e uma escala ascendente e descendente."
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "TAB_READING",
                            "title": "Sequência 1: Arpejo de C Maior",
                            "question": "Reproduza no braço a sequência mostrada na tablatura acima.",
                            "targetSequence": [
                                { "string": 5, "fret": 3 }, { "string": 4, "fret": 2 }, { "string": 3, "fret": 0 }, { "string": 2, "fret": 1 },
                                { "string": 1, "fret": 0 }, { "string": 2, "fret": 1 }, { "string": 3, "fret": 0 }, { "string": 4, "fret": 2 },
                                { "string": 5, "fret": 3 }
                            ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "TAB_READING",
                            "title": "Sequência 2: Escala Ascendente",
                            "question": "Reproduza no braço a sequência mostrada na tablatura acima.",
                            "targetSequence": [
                                { "string": 6, "fret": 0 }, { "string": 6, "fret": 1 }, { "string": 6, "fret": 3 }, { "string": 6, "fret": 5 },
                                { "string": 5, "fret": 2 }, { "string": 5, "fret": 3 }, { "string": 5, "fret": 5 }, { "string": 4, "fret": 2 }
                            ]
                        },
                        {
                            "type": "DRILL",
                            "exerciseType": "TAB_READING",
                            "title": "Sequência 3: Escala Descendente",
                            "question": "Reproduza no braço a sequência mostrada na tablatura acima, de volta ao início.",
                            "targetSequence": [
                                { "string": 4, "fret": 2 }, { "string": 5, "fret": 5 }, { "string": 5, "fret": 3 }, { "string": 5, "fret": 2 },
                                { "string": 6, "fret": 5 }, { "string": 6, "fret": 3 }, { "string": 6, "fret": 1 }, { "string": 6, "fret": 0 }
                            ]
                        }
                    ]
                """;

                String contentSec01ModShape31 = """
                    [
                        {
                            "type": "THEORY",
                            "title": "O Shape 3-1 (Mesma Lógica do 4-2)",
                            "text": "Assim como no shape 4-2, pular a 2ª corda (Si) também exige 3 casas em vez de 2 — porque a dupla Sol-Si é uma 3ª maior, não uma 4ª justa. Esse é o último shape de oitava 'pula 1 corda' que cabe no braço.",
                            "illustration": { "kind": "fretboard", "notes": [ { "string": 3, "fret": 0 }, { "string": 1, "fret": 3 } ] }
                        },
                        { "type": "DRILL", "exerciseType": "SHAPE_MATCH", "title": "Oitava de G", "question": "Selecione as duas notas que formam a oitava de G (tônica na 3ª corda solta).", "targetShape": [ { "string": 3, "fret": 0 }, { "string": 1, "fret": 3 } ] },
                        { "type": "DRILL", "exerciseType": "SHAPE_MATCH", "title": "Oitava de A", "question": "Encontre o A na 3ª corda e marque sua respectiva oitava na 1ª corda.", "targetShape": [ { "string": 3, "fret": 2 }, { "string": 1, "fret": 5 } ] },
                        { "type": "DRILL", "exerciseType": "SHAPE_MATCH", "title": "Encontrando a Tônica", "question": "Dado que a 1ª corda, casa 3, é um G, encontre esta nota e selecione o G de origem na 3ª corda solta.", "targetShape": [ { "string": 3, "fret": 0 }, { "string": 1, "fret": 3 } ] },
                        {
                            "type": "DRILL", "exerciseType": "MULTIPLE_CHOICE", "title": "Por Que Esse Shape Também Muda?",
                            "question": "Por que o shape de oitava entre as cordas Sol e Mi agudo também precisa de 3 casas em vez de 2?",
                            "options": [ "Porque a corda Sol está desafinada", "Por causa do intervalo especial (3ª maior) entre as cordas Sol e Si", "Porque pulamos 2 cordas em vez de 1", "Só esse shape muda, os outros não" ],
                            "correctAnswer": "Por causa do intervalo especial (3ª maior) entre as cordas Sol e Si"
                        }
                    ]
                """;

                String contentSec01ModScaleGrave = """
                    [
                        {
                            "type": "THEORY",
                            "title": "A Escala de Dó Maior (Graves)",
                            "text": "Você já conhece, sem perceber, todas as notas da escala de Dó maior: ela usa só as notas naturais (sem sustenido nem bemol) que você vem aprendendo desde o início. Vamos tocar a primeira metade dela começando no Dó da 6ª corda, cruzando as três cordas graves.",
                            "illustration": { "kind": "fretboard", "notes": [ { "string": 6, "fret": 8 }, { "string": 6, "fret": 10 }, { "string": 5, "fret": 7 }, { "string": 5, "fret": 8 }, { "string": 5, "fret": 10 }, { "string": 4, "fret": 7 }, { "string": 4, "fret": 9 }, { "string": 4, "fret": 10 } ] }
                        },
                        {
                            "type": "DRILL", "exerciseType": "TAB_READING", "title": "Sequência 1: Dó a Dó (Subindo)", "question": "Reproduza no braço a sequência mostrada na tablatura acima.",
                            "targetSequence": [ { "string": 6, "fret": 8 }, { "string": 6, "fret": 10 }, { "string": 5, "fret": 7 }, { "string": 5, "fret": 8 }, { "string": 5, "fret": 10 }, { "string": 4, "fret": 7 }, { "string": 4, "fret": 9 }, { "string": 4, "fret": 10 } ]
                        },
                        {
                            "type": "DRILL", "exerciseType": "TAB_READING", "title": "Sequência 2: Dó a Dó (Descendo)", "question": "Reproduza no braço a sequência mostrada na tablatura acima, de volta ao início.",
                            "targetSequence": [ { "string": 4, "fret": 10 }, { "string": 4, "fret": 9 }, { "string": 4, "fret": 7 }, { "string": 5, "fret": 10 }, { "string": 5, "fret": 8 }, { "string": 5, "fret": 7 }, { "string": 6, "fret": 10 }, { "string": 6, "fret": 8 } ]
                        },
                        {
                            "type": "THEORY",
                            "title": "Um Punhado de Notas, Vários Nomes",
                            "text": "Essa mesma sequência de notas, se você começar a contar a partir do Lá em vez do Dó, também é a escala de Lá menor natural — e se tirar o Ré e o Si, vira a pentatônica de Lá menor. Mesmas notas, ponto de partida diferente."
                        }
                    ]
                """;

                String contentSec01ModScaleAgudo = """
                    [
                        {
                            "type": "THEORY",
                            "title": "A Escala de Dó Maior (Agudas)",
                            "text": "Continuando a escala do bloco anterior, vamos completar as notas que faltam — do Ré até o Dó, uma oitava acima — agora usando as cordas Sol, Si e Mi agudo.",
                            "illustration": { "kind": "fretboard", "notes": [ { "string": 3, "fret": 7 }, { "string": 3, "fret": 9 }, { "string": 3, "fret": 10 }, { "string": 3, "fret": 12 }, { "string": 2, "fret": 10 }, { "string": 2, "fret": 12 }, { "string": 1, "fret": 8 } ] }
                        },
                        {
                            "type": "DRILL", "exerciseType": "TAB_READING", "title": "Sequência 1: Ré ao Dó (Subindo)", "question": "Reproduza no braço a sequência mostrada na tablatura acima.",
                            "targetSequence": [ { "string": 3, "fret": 7 }, { "string": 3, "fret": 9 }, { "string": 3, "fret": 10 }, { "string": 3, "fret": 12 }, { "string": 2, "fret": 10 }, { "string": 2, "fret": 12 }, { "string": 1, "fret": 8 } ]
                        },
                        {
                            "type": "DRILL", "exerciseType": "TAB_READING", "title": "Sequência 2: Dó ao Ré (Descendo)", "question": "Reproduza no braço a sequência mostrada na tablatura acima, de volta ao início.",
                            "targetSequence": [ { "string": 1, "fret": 8 }, { "string": 2, "fret": 12 }, { "string": 2, "fret": 10 }, { "string": 3, "fret": 12 }, { "string": 3, "fret": 10 }, { "string": 3, "fret": 9 }, { "string": 3, "fret": 7 } ]
                        }
                    ]
                """;

                String contentSec01ModScaleFull = """
                    [
                        {
                            "type": "THEORY",
                            "title": "A Escala Completa: Duas Oitavas",
                            "text": "Hora de juntar os dois pedaços que você aprendeu nos blocos anteriores numa escala só, do Dó grave ao Dó agudo duas oitavas acima, usando o braço inteiro."
                        },
                        {
                            "type": "DRILL", "exerciseType": "TAB_READING", "title": "Sequência 1: Duas Oitavas (Subindo)", "question": "Reproduza no braço a sequência mostrada na tablatura acima.",
                            "targetSequence": [
                                { "string": 6, "fret": 8 }, { "string": 6, "fret": 10 }, { "string": 5, "fret": 7 }, { "string": 5, "fret": 8 }, { "string": 5, "fret": 10 }, { "string": 4, "fret": 7 }, { "string": 4, "fret": 9 }, { "string": 4, "fret": 10 },
                                { "string": 3, "fret": 7 }, { "string": 3, "fret": 9 }, { "string": 3, "fret": 10 }, { "string": 3, "fret": 12 }, { "string": 2, "fret": 10 }, { "string": 2, "fret": 12 }, { "string": 1, "fret": 8 }
                            ]
                        },
                        {
                            "type": "DRILL", "exerciseType": "TAB_READING", "title": "Sequência 2: Duas Oitavas (Descendo)", "question": "Reproduza no braço a sequência mostrada na tablatura acima, de volta ao início.",
                            "targetSequence": [
                                { "string": 1, "fret": 8 }, { "string": 2, "fret": 12 }, { "string": 2, "fret": 10 }, { "string": 3, "fret": 12 }, { "string": 3, "fret": 10 }, { "string": 3, "fret": 9 }, { "string": 3, "fret": 7 },
                                { "string": 4, "fret": 10 }, { "string": 4, "fret": 9 }, { "string": 4, "fret": 7 }, { "string": 5, "fret": 10 }, { "string": 5, "fret": 8 }, { "string": 5, "fret": 7 }, { "string": 6, "fret": 10 }, { "string": 6, "fret": 8 }
                            ]
                        }
                    ]
                """;

                String contentSec01ModTabGraves = """
                    [
                        {
                            "type": "THEORY",
                            "title": "Tablatura: Cordas Graves Juntas",
                            "text": "Hora de tirar cada corda do seu canto e tocar as três juntas numa frase só. Vamos praticar trocando de corda no meio da sequência, do jeito que aparece numa tablatura de verdade."
                        },
                        {
                            "type": "DRILL", "exerciseType": "TAB_READING", "title": "Sequência 1: Cruzando as Cordas", "question": "Reproduza no braço a sequência mostrada na tablatura acima.",
                            "targetSequence": [ { "string": 6, "fret": 0 }, { "string": 5, "fret": 0 }, { "string": 4, "fret": 0 }, { "string": 6, "fret": 3 }, { "string": 5, "fret": 3 }, { "string": 4, "fret": 3 }, { "string": 6, "fret": 5 }, { "string": 5, "fret": 5 }, { "string": 4, "fret": 5 } ]
                        },
                        {
                            "type": "DRILL", "exerciseType": "TAB_READING", "title": "Sequência 2: De Volta ao Início", "question": "Reproduza no braço a sequência mostrada na tablatura acima, de volta ao início.",
                            "targetSequence": [ { "string": 4, "fret": 5 }, { "string": 5, "fret": 5 }, { "string": 6, "fret": 5 }, { "string": 4, "fret": 3 }, { "string": 5, "fret": 3 }, { "string": 6, "fret": 3 }, { "string": 4, "fret": 0 }, { "string": 5, "fret": 0 }, { "string": 6, "fret": 0 } ]
                        }
                    ]
                """;

                String contentSec01ModTabAgudas = """
                    [
                        {
                            "type": "THEORY",
                            "title": "Tablatura: Cordas Agudas Juntas",
                            "text": "Mesma ideia do bloco anterior, agora com o trio agudo: Sol, Si e Mi agudo. Pratique trocando de corda no meio da frase."
                        },
                        {
                            "type": "DRILL", "exerciseType": "TAB_READING", "title": "Sequência 1: Cruzando as Cordas", "question": "Reproduza no braço a sequência mostrada na tablatura acima.",
                            "targetSequence": [ { "string": 3, "fret": 0 }, { "string": 2, "fret": 0 }, { "string": 1, "fret": 0 }, { "string": 3, "fret": 5 }, { "string": 2, "fret": 5 }, { "string": 1, "fret": 5 }, { "string": 3, "fret": 10 }, { "string": 2, "fret": 10 }, { "string": 1, "fret": 10 } ]
                        },
                        {
                            "type": "DRILL", "exerciseType": "TAB_READING", "title": "Sequência 2: De Volta ao Início", "question": "Reproduza no braço a sequência mostrada na tablatura acima, de volta ao início.",
                            "targetSequence": [ { "string": 1, "fret": 10 }, { "string": 2, "fret": 10 }, { "string": 3, "fret": 10 }, { "string": 1, "fret": 5 }, { "string": 2, "fret": 5 }, { "string": 3, "fret": 5 }, { "string": 1, "fret": 0 }, { "string": 2, "fret": 0 }, { "string": 3, "fret": 0 } ]
                        }
                    ]
                """;

                String contentSec01ModRepeatGraves = """
                    [
                        {
                            "type": "THEORY",
                            "title": "O Braço se Repete (Cordas Graves)",
                            "text": "A casa 12 é a oitava da corda solta: mesma nota, mesmo nome, só que mais aguda. A partir dali, todo o padrão de casas que você aprendeu se repete de novo, idêntico — a casa 13 tem a mesma nota que a casa 1, a casa 15 a mesma da casa 3, e assim por diante. Basta somar 12.",
                            "illustration": { "kind": "fretboard", "notes": [ { "string": 6, "fret": 1 }, { "string": 6, "fret": 13 } ] }
                        },
                        {
                            "type": "DRILL", "exerciseType": "SHAPE_MATCH", "title": "Nota F, uma Oitava Acima", "question": "Você já sabe que a casa 1 da 6ª corda é F. Encontre o F na oitava seguinte, depois da casa 12.",
                            "targetShape": [ { "string": 6, "fret": 13 } ]
                        },
                        {
                            "type": "DRILL", "exerciseType": "SHAPE_MATCH", "title": "Nota C, uma Oitava Acima", "question": "Encontre o C na 5ª corda, na oitava seguinte, depois da casa 12.",
                            "targetShape": [ { "string": 5, "fret": 15 } ]
                        },
                        {
                            "type": "DRILL", "exerciseType": "SHAPE_MATCH", "title": "Nota A, uma Oitava Acima", "question": "Encontre o A na 4ª corda, na oitava seguinte, depois da casa 12.",
                            "targetShape": [ { "string": 4, "fret": 19 } ]
                        },
                        {
                            "type": "DRILL", "exerciseType": "MULTIPLE_CHOICE", "title": "Some 12 e Descubra", "question": "Sabendo que a casa 5 da 4ª corda é G, qual nota está na casa 17?",
                            "options": [ "F#", "G", "G#", "A" ], "correctAnswer": "G", "markedPosition": { "string": 4, "fret": 17 }
                        },
                        {
                            "type": "DRILL", "exerciseType": "SHAPE_MATCH", "title": "Duas Oitavas da Mesma Nota", "question": "Selecione o C na 6ª corda tanto antes quanto depois da casa 12.",
                            "targetShape": [ { "string": 6, "fret": 8 }, { "string": 6, "fret": 20 } ]
                        }
                    ]
                """;

                String contentSec01ModRepeatAgudas = """
                    [
                        {
                            "type": "THEORY",
                            "title": "O Braço se Repete (Cordas Agudas)",
                            "text": "A mesma lógica vale para as cordas agudas: some 12 à casa que você já conhece e chega na oitava seguinte, idêntica em nome."
                        },
                        {
                            "type": "DRILL", "exerciseType": "SHAPE_MATCH", "title": "Nota A, uma Oitava Acima", "question": "Encontre o A na 3ª corda, na oitava seguinte, depois da casa 12.",
                            "targetShape": [ { "string": 3, "fret": 14 } ]
                        },
                        {
                            "type": "DRILL", "exerciseType": "SHAPE_MATCH", "title": "Nota E, uma Oitava Acima", "question": "Encontre o E na 2ª corda, na oitava seguinte, depois da casa 12.",
                            "targetShape": [ { "string": 2, "fret": 17 } ]
                        },
                        {
                            "type": "DRILL", "exerciseType": "SHAPE_MATCH", "title": "Nota G, uma Oitava Acima", "question": "Encontre o G na 1ª corda, na oitava seguinte, depois da casa 12.",
                            "targetShape": [ { "string": 1, "fret": 15 } ]
                        },
                        {
                            "type": "DRILL", "exerciseType": "MULTIPLE_CHOICE", "title": "Some 12 e Descubra", "question": "Sabendo que a casa 1 da 2ª corda é C, qual nota está na casa 13?",
                            "options": [ "B", "C", "C#", "D" ], "correctAnswer": "C", "markedPosition": { "string": 2, "fret": 13 }
                        },
                        {
                            "type": "DRILL", "exerciseType": "SHAPE_MATCH", "title": "Duas Oitavas da Mesma Nota", "question": "Selecione o B na 1ª corda tanto antes quanto depois da casa 12.",
                            "targetShape": [ { "string": 1, "fret": 7 }, { "string": 1, "fret": 19 } ]
                        }
                    ]
                """;

                String contentSec01ModRepeatTab = """
                    [
                        {
                            "type": "THEORY",
                            "title": "Tablatura: Casas 12 a 22",
                            "text": "Agora que você sabe que tudo se repete depois da casa 12, aqui está uma frase inteira tocada só na região mais aguda do braço, cruzando várias cordas."
                        },
                        {
                            "type": "DRILL", "exerciseType": "TAB_READING", "title": "Sequência 1: Subindo", "question": "Reproduza no braço a sequência mostrada na tablatura acima.",
                            "targetSequence": [ { "string": 6, "fret": 13 }, { "string": 5, "fret": 15 }, { "string": 4, "fret": 19 }, { "string": 3, "fret": 14 }, { "string": 2, "fret": 17 }, { "string": 1, "fret": 15 } ]
                        },
                        {
                            "type": "DRILL", "exerciseType": "TAB_READING", "title": "Sequência 2: Descendo", "question": "Reproduza no braço a sequência mostrada na tablatura acima, de volta ao início.",
                            "targetSequence": [ { "string": 1, "fret": 15 }, { "string": 2, "fret": 17 }, { "string": 3, "fret": 14 }, { "string": 4, "fret": 19 }, { "string": 5, "fret": 15 }, { "string": 6, "fret": 13 } ]
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
                            "title": "Imagem de Apoio",
                            "text": "Passos também podem trazer uma imagem de apoio, além ou no lugar de uma ilustração interativa.",
                            "imageUrl": "https://upload.wikimedia.org/wikipedia/commons/thumb/6/6f/Guitar_neck.jpg/640px-Guitar_neck.jpg"
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

                moduleRepository.saveAll(List.of(
                        new Module("Corda Mi grave: casas 0-5", 1, sec01FundamentosBraco, contentSec01Mod01),
                        new Module("Corda Mi grave: casas 5-12", 2, sec01FundamentosBraco, contentSec01Mod02),
                        new Module("Tablatura: revisão da corda Mi grave", 3, sec01FundamentosBraco, contentSec01Mod03),
                        new Module("Corda Lá: casas 0-5", 4, sec01FundamentosBraco, contentSec01Mod04),
                        new Module("Corda Lá: casas 5-12", 5, sec01FundamentosBraco, contentSec01Mod05),
                        new Module("Tablatura: revisão da corda Lá", 6, sec01FundamentosBraco, contentSec01Mod06),
                        new Module("Corda Ré: casas 0-5", 7, sec01FundamentosBraco, contentSec01Mod07),
                        new Module("Corda Ré: casas 5-12", 8, sec01FundamentosBraco, contentSec01Mod08),
                        new Module("Tablatura: revisão da corda Ré", 9, sec01FundamentosBraco, contentSec01Mod09),
                        new Module("Tablatura: cordas graves juntas", 10, sec01FundamentosBraco, contentSec01ModTabGraves),
                        new Module("Oitavas: padrão Mi grave → Ré (Shape 6-4)", 11, sec01FundamentosBraco, contentSec01Mod21),
                        new Module("Sustenidos e bemóis: cordas graves (Mi, Lá, Ré)", 12, sec01FundamentosBraco, contentSec01Mod19),
                        new Module("Escala de Dó maior: cordas graves", 13, sec01FundamentosBraco, contentSec01ModScaleGrave),
                        new Module("Corda Sol: casas 0-5", 14, sec01FundamentosBraco, contentSec01Mod10),
                        new Module("Corda Sol: casas 5-12", 15, sec01FundamentosBraco, contentSec01Mod11),
                        new Module("Tablatura: revisão da corda Sol", 16, sec01FundamentosBraco, contentSec01Mod12),
                        new Module("Corda Si: casas 0-5", 17, sec01FundamentosBraco, contentSec01Mod13),
                        new Module("Corda Si: casas 5-12", 18, sec01FundamentosBraco, contentSec01Mod14),
                        new Module("Tablatura: revisão da corda Si", 19, sec01FundamentosBraco, contentSec01Mod15),
                        new Module("Corda Mi agudo: casas 0-5", 20, sec01FundamentosBraco, contentSec01Mod16),
                        new Module("Corda Mi agudo: casas 5-12", 21, sec01FundamentosBraco, contentSec01Mod17),
                        new Module("Tablatura: revisão da corda Mi agudo", 22, sec01FundamentosBraco, contentSec01Mod18),
                        new Module("Tablatura: cordas agudas juntas", 23, sec01FundamentosBraco, contentSec01ModTabAgudas),
                        new Module("Oitavas: padrão Sol → Mi agudo (Shape 3-1)", 24, sec01FundamentosBraco, contentSec01ModShape31),
                        new Module("Sustenidos e bemóis: cordas agudas (Sol, Si, Mi)", 25, sec01FundamentosBraco, contentSec01Mod20),
                        new Module("Escala de Dó maior: cordas agudas", 26, sec01FundamentosBraco, contentSec01ModScaleAgudo),
                        new Module("Oitavas: padrão Lá → Sol (Shape 5-3)", 27, sec01FundamentosBraco, contentSec01Mod22),
                        new Module("Oitavas: padrão Ré → Si (Shape 4-2)", 28, sec01FundamentosBraco, contentSec01Mod23),
                        new Module("Escala de Dó maior: braço inteiro", 29, sec01FundamentosBraco, contentSec01ModScaleFull),
                        new Module("Tablatura: melodia com todas as cordas", 30, sec01FundamentosBraco, contentSec01Mod24),
                        new Module("Revisão geral: ache qualquer nota", 31, sec01FundamentosBraco, contentSec01Mod25),
                        new Module("O braço se repete (cordas graves)", 32, sec01FundamentosBraco, contentSec01ModRepeatGraves),
                        new Module("O braço se repete (cordas agudas)", 33, sec01FundamentosBraco, contentSec01ModRepeatAgudas),
                        new Module("Tablatura: casas 12 a 22", 34, sec01FundamentosBraco, contentSec01ModRepeatTab),
                        new Module("Tablatura: revisão final da seção", 35, sec01FundamentosBraco, contentSec01Mod26),
                        new Module("Teste: Todos os Tipos de Exercício", 9999, secQa, contentQaAllExerciseTypes)
                ));
            }
        };
    }
}
