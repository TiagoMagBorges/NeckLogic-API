package com.necklogic.api.model;

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

    @OneToMany(mappedBy = "section", cascade = CascadeType.ALL)
    private List<Module> modules = new ArrayList<>();

    public Section(String title, String description, Integer orderIndex) {
        this.title = title;
        this.description = description;
        this.orderIndex = orderIndex;
    }
}