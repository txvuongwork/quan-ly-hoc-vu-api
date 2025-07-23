package com.backend.quan_ly_hoc_vu_api.model;

import com.backend.quan_ly_hoc_vu_api.helper.enumeration.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username", unique = true, nullable = false, length = 50)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private UserRole role = UserRole.STUDENT;

    @Column(name = "student_code", unique = true, length = 20)
    private String studentCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "major_id")
    private Major major;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    // Relationships
    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Builder.Default
    private List<UserSession> sessions = new ArrayList<>();

//    @JsonIgnore
//    @ToString.Exclude
//    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
//    @Builder.Default
//    private List<Enrollment> enrollments = new ArrayList<>();
//
//    @JsonIgnore
//    @ToString.Exclude
//    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
//    @Builder.Default
//    private List<Attendance> attendances = new ArrayList<>();

    // Helper methods
    public void setNewSession(UserSession session) {
        session.setUser(this);
        this.sessions.clear();
        this.sessions.add(session);
    }

    public boolean isStudent() {
        return UserRole.STUDENT.equals(this.role);
    }

    public boolean isAdmin() {
        return UserRole.ADMIN.equals(this.role);
    }

    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
        updatedAt = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }
}