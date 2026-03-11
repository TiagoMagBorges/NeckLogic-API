package com.necklogic.api.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Table(name = "users")
@Entity(name = "User")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true)
    private String email;

    private String password;

    private String name;

    @Column(name = "onboarding_completed", nullable = false)
    private boolean onboardingCompleted = false;

    @Column(nullable = false)
    private Integer xp = 0;

    @Column(name = "user_level", nullable = false)
    private Integer level = 1;

    @Column(name = "last_activity_date")
    private LocalDate lastActivityDate;

    @Column(name = "current_streak", nullable = false)
    private Integer currentStreak = 0;

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.onboardingCompleted = false;
        this.xp = 0;
        this.level = 1;
        this.currentStreak = 0;
    }

    public void addXp(Integer gainedXp) {
        this.xp += gainedXp;
        this.level = calculateLevel(this.xp);
    }

    private Integer calculateLevel(Integer totalXp) {
        return (int) (0.1 * Math.sqrt(totalXp)) + 1;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}