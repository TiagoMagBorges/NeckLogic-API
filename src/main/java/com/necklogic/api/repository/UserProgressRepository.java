package com.necklogic.api.repository;

import com.necklogic.api.model.UserProgress;
import com.necklogic.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UserProgressRepository extends JpaRepository<UserProgress, Long> {
    List<UserProgress> findByUser(User user);

    Optional<UserProgress> findByUserAndModuleId(User user, Long moduleId);
}