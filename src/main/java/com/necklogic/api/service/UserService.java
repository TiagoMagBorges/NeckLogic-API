package com.necklogic.api.service;

import com.necklogic.api.dto.UpdatePasswordRequestDTO;
import com.necklogic.api.dto.UpdateProfileRequestDTO;
import com.necklogic.api.model.User;
import com.necklogic.api.repository.UserProgressRepository;
import com.necklogic.api.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserProgressRepository progressRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserProgressRepository progressRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.progressRepository = progressRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User updateProfile(User user, UpdateProfileRequestDTO dto) {
        if (dto.name() != null && !dto.name().isBlank()) {
            user.setName(dto.name());
        }

        if (dto.email() != null && !dto.email().isBlank() && !user.getEmail().equals(dto.email())) {
            if (userRepository.findByEmail(dto.email()) != null) {
                throw new IllegalArgumentException("E-mail já está em uso por outra conta.");
            }
            user.setEmail(dto.email());
        }

        return userRepository.save(user);
    }

    @Transactional
    public void updatePassword(User user, UpdatePasswordRequestDTO dto) {
        if (!passwordEncoder.matches(dto.currentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("A senha atual está incorreta.");
        }

        user.setPassword(passwordEncoder.encode(dto.newPassword()));
        userRepository.save(user);
    }

    @Transactional
    public void deleteAccount(User user) {
        progressRepository.deleteByUser(user);
        userRepository.delete(user);
    }
}