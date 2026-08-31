package com.necklogic.api.repository;

import com.necklogic.api.model.Section;
import com.necklogic.api.model.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface SectionRepository extends JpaRepository<Section, Long> {
    Optional<Section> findByTrackAndOrderIndex(Track track, Integer orderIndex);
    List<Section> findByTrack(Track track);
}
