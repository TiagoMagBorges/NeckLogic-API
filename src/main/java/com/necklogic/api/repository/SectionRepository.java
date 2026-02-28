package com.necklogic.api.repository;

import com.necklogic.api.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SectionRepository extends JpaRepository<Section, Long> {
    Optional<Section> findByOrderIndex(Integer orderIndex);
}