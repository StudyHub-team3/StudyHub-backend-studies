package com.studyhub.studyhub_backend_studies.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@Entity
@Table(name="study_group")
@Getter @Setter
public class StudyGroup {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="created_by", nullable = false)
    private Long createdBy;

    @Column(name="group_name", nullable = false)
    private String groupName;

    @Column(name="description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name="category", nullable = false)
    private StudyGroupCategory category;

    @Column(name="created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name="updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name="end_date", nullable = false)
    private LocalDate endDate;

    @Column(name="max_mentor", nullable = false)
    private int maxMentor;

    @Column(name = "mentor_count", nullable = false, columnDefinition = "int default 0")
    private int mentorCount = 0;

    @Column(name="max_mentee", nullable = false)
    private int maxMentee;

    @Column(name="mentee_count", nullable = false, columnDefinition = "int default 0")
    private int menteeCount = 0;


    // 생성/수정 시간 자동 세팅
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
