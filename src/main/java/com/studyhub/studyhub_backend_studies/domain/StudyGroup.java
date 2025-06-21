package com.studyhub.studyhub_backend_studies.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@Entity
@Table(name="study_group")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Enumerated(EnumType.STRING)
    @Column(name="category", nullable = false)
    private StudyGroupCategory category;

    @Enumerated(EnumType.STRING)
    @Column(name="status", nullable = false)
    private StudyStatus status = StudyStatus.RECRUITING;

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

    @Builder
    public StudyGroup(Long createdBy, String groupName, String description,
                      StudyGroupCategory category, LocalDate endDate,
                      int maxMentor, int maxMentee) {
        this.createdBy = createdBy;
        this.groupName = groupName;
        this.description = description;
        this.category = category;
        this.endDate = endDate;
        this.maxMentor = maxMentor;
        this.maxMentee = maxMentee;
    }

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


    public void increaseMentorCount() {
        this.mentorCount++;
        updateStatusIfFull();
    }

    public void increaseMenteeCount() {
        this.menteeCount++;
        updateStatusIfFull();
    }

    public void decreaseMentorCount() {
        if (this.mentorCount > 0) {
            this.mentorCount--;
            updateStatusIfAvailable();
        }
    }

    public void decreaseMenteeCount() {
        if (this.menteeCount > 0) {
            this.menteeCount--;
            updateStatusIfAvailable();
        }
    }

    // 모집 종료 조건 검사
    private void updateStatusIfFull() {
        if (this.mentorCount >= this.maxMentor && this.menteeCount >= this.maxMentee) {
            this.status = StudyStatus.COMPLETED;  // 또는 CLOSED, FULL 등
        }
    }

    // 다시 모집 가능 조건 검사
    private void updateStatusIfAvailable() {
        if (this.status == StudyStatus.COMPLETED &&
                (this.mentorCount < this.maxMentor || this.menteeCount < this.maxMentee)) {
            this.status = StudyStatus.RECRUITING;
        }
    }
}
