package com.necklogic.api.service;

import com.necklogic.api.dto.auth.AuthenticationDTO;
import com.necklogic.api.dto.auth.LoginResponseDTO;
import com.necklogic.api.dto.auth.RegisterDTO;
import com.necklogic.api.model.Track;
import com.necklogic.api.model.User;
import com.necklogic.api.model.UserTrackEnrollment;
import com.necklogic.api.model.VerificationToken;
import com.necklogic.api.model.enums.TokenType;
import com.necklogic.api.repository.TrackRepository;
import com.necklogic.api.repository.UserRepository;
import com.necklogic.api.repository.UserTrackEnrollmentRepository;
import com.necklogic.api.repository.VerificationTokenRepository;
import com.necklogic.api.security.TokenService;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final VerificationTokenRepository tokenRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final MessageSource messageSource;
    private final TrackRepository trackRepository;
    private final UserTrackEnrollmentRepository enrollmentRepository;

    public AuthenticationService(UserRepository userRepository,
                                 VerificationTokenRepository tokenRepository,
                                 EmailService emailService,
                                 PasswordEncoder passwordEncoder,
                                 AuthenticationManager authenticationManager,
                                 TokenService tokenService,
                                 MessageSource messageSource,
                                 TrackRepository trackRepository,
                                 UserTrackEnrollmentRepository enrollmentRepository) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.messageSource = messageSource;
        this.trackRepository = trackRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    private UserTrackEnrollment getOrCreateOfficialEnrollment(User user) {
        Track officialTrack = trackRepository.findByOfficialTrue()
                .orElseThrow(() -> new IllegalStateException("No official track configured"));

        return enrollmentRepository.findByUserAndTrack(user, officialTrack)
                .orElseGet(() -> enrollmentRepository.save(new UserTrackEnrollment(user, officialTrack)));
    }

    @Transactional
    public void registerUser(RegisterDTO data) {
        User existingUser = userRepository.findByEmail(data.email());

        boolean asTeacher = Boolean.TRUE.equals(data.asTeacher());

        if (existingUser != null) {
            if (existingUser.isEnabled()) {
                return;
            }
            existingUser.setName(data.name());
            existingUser.setPassword(passwordEncoder.encode(data.password()));
            existingUser.setTeacher(asTeacher);
            userRepository.save(existingUser);
            generateAndSendOtp(existingUser, TokenType.REGISTRATION);
            return;
        }

        User user = new User(data.email(), passwordEncoder.encode(data.password()), data.name());
        user.setTeacher(asTeacher);
        userRepository.save(user);

        generateAndSendOtp(user, TokenType.REGISTRATION);
    }

    public LoginResponseDTO login(AuthenticationDTO data) {
        User user = (User) userRepository.findByEmail(data.email());

        if (user != null && !user.isEnabled()) {
            throw new IllegalStateException("ACCOUNT_DISABLED");
        }

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = authenticationManager.authenticate(usernamePassword);
        User authenticatedUser = (User) auth.getPrincipal();
        var token = tokenService.generateToken(authenticatedUser);
        UserTrackEnrollment enrollment = getOrCreateOfficialEnrollment(authenticatedUser);

        return new LoginResponseDTO(
                token,
                authenticatedUser.isOnboardingCompleted(),
                enrollment.getXp(),
                enrollment.getLevel(),
                authenticatedUser.getCurrentStreak(),
                authenticatedUser.getName(),
                authenticatedUser.getEmail()
        );
    }

    @Transactional
    public LoginResponseDTO verifyAccount(String email, String token) {
        User user = validateAndGetUserByToken(email, token, TokenType.REGISTRATION);
        user.setEnabled(true);
        userRepository.save(user);
        tokenRepository.deleteByUserAndType(user, TokenType.REGISTRATION);

        String jwtToken = tokenService.generateToken(user);
        UserTrackEnrollment enrollment = getOrCreateOfficialEnrollment(user);

        return new LoginResponseDTO(
                jwtToken,
                user.isOnboardingCompleted(),
                enrollment.getXp(),
                enrollment.getLevel(),
                user.getCurrentStreak(),
                user.getName(),
                user.getEmail()
        );
    }

    @Transactional
    public void requestPasswordReset(String email) {
        User user = (User) userRepository.findByEmail(email);
        if (user != null && user.isEnabled()) {
            generateAndSendOtp(user, TokenType.PASSWORD_RESET);
        }
    }

    @Transactional
    public void resetPassword(String email, String token, String newPassword) {
        User user = validateAndGetUserByToken(email, token, TokenType.PASSWORD_RESET);
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        tokenRepository.deleteByUserAndType(user, TokenType.PASSWORD_RESET);
    }

    @Transactional
    public void resendVerification(String email) {
        User user = (User) userRepository.findByEmail(email);
        if (user != null && !user.isEnabled()) {
            generateAndSendOtp(user, TokenType.REGISTRATION);
        }
    }

    private void generateAndSendOtp(User user, TokenType type) {
        tokenRepository.deleteByUserAndType(user, type);

        String otp = String.format("%06d", new SecureRandom().nextInt(999999));

        VerificationToken token = VerificationToken.builder()
                .token(otp)
                .user(user)
                .type(type)
                .expiryDate(LocalDateTime.now().plusMinutes(15))
                .build();

        tokenRepository.save(token);

        var locale = LocaleContextHolder.getLocale();
        String subjectKey = type == TokenType.REGISTRATION ? "email.register.subject" : "email.forgot.subject";
        String bodyKey = type == TokenType.REGISTRATION ? "email.register.body" : "email.forgot.body";

        String subject = messageSource.getMessage(subjectKey, null, locale);
        String message = messageSource.getMessage(bodyKey, new Object[]{otp}, locale);

        emailService.sendOtpEmail(user.getEmail(), otp, subject, message);
    }

    private User validateAndGetUserByToken(String email, String otp, TokenType type) {
        User user = (User) userRepository.findByEmail(email);
        if (user == null) {
            throw new IllegalArgumentException("Invalid user");
        }

        Optional<VerificationToken> tokenOpt = tokenRepository.findByTokenAndTypeAndUser(otp, type, user);
        if (tokenOpt.isEmpty() || tokenOpt.get().getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Invalid or expired token");
        }

        return user;
    }
}