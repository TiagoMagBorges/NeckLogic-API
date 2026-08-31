package com.necklogic.api.repository;

import com.necklogic.api.model.User;
import com.necklogic.api.model.VerificationToken;
import com.necklogic.api.model.enums.TokenType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    Optional<VerificationToken> findByTokenAndTypeAndUser(String token, TokenType type, User user);
    void deleteByUserAndType(User user, TokenType type);
    void deleteByUser(User user);
}