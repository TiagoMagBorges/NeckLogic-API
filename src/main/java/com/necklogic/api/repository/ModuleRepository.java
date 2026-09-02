package com.necklogic.api.repository;

import com.necklogic.api.model.Module;
import com.necklogic.api.model.Section;
import com.necklogic.api.model.Track;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ModuleRepository extends JpaRepository<Module, Long> {
    Optional<Module> findBySectionAndOrderIndex(Section section, Integer orderIndex);
    Optional<Module> findFirstBySectionOrderByOrderIndexAsc(Section section);
    List<Module> findBySectionTrack(Track track);
    Optional<Module> findBySectionTrackAndOrderIndex(Track track, Integer orderIndex);
}
