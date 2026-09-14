package com.necklogic.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sections")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @Column(name = "order_index")
    private Integer orderIndex;

    @ManyToOne
    @JoinColumn(name = "track_id")
    @JsonIgnore
    private Track track;

    @OneToMany(mappedBy = "section", cascade = CascadeType.ALL)
    private List<Module> modules = new ArrayList<>();

    @Column(name = "skip_requires_test", nullable = false)
    private boolean skipRequiresTest = false;

    @ManyToOne
    @JoinColumn(name = "skip_test_module_id")
    @JsonIgnore
    private Module skipTestModule;

    @Column(name = "skip_pass_threshold")
    private Double skipPassThreshold;

    public Section(String title, String description, Integer orderIndex) {
        this.title = title;
        this.description = description;
        this.orderIndex = orderIndex;
    }
}