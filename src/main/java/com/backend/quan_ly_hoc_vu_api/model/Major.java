package com.backend.quan_ly_hoc_vu_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "t_majors")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Major {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "major_code", nullable = false)
    private String majorCode;

    @Column(name = "major_name", nullable = false)
    private String majorName;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at")
    private Instant createdAt;

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "major", fetch = FetchType.LAZY)
    private List<User> users;

    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
    }

}
