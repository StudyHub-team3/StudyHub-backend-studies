package com.studyhub.studyhub_backend_studies.domain.dto;

import com.studyhub.studyhub_backend_studies.domain.StudyGroup;
import com.studyhub.studyhub_backend_studies.domain.StudyGroupCategory;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
public class StudyDetailResponse {

    private Long id;
    private String groupName;
    private String description;
    private StudyGroupCategory category;

    Long createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDate endDate;

    private int mentorCount;
    private int maxMentor;
    private int menteeCount;
    private int maxMentee;

    public static StudyDetailResponse fromEntity(StudyGroup studyGroup){

        StudyDetailResponse dto = new StudyDetailResponse();

        dto.setId(studyGroup.getId());
        dto.setGroupName(studyGroup.getGroupName());
        dto.setDescription(studyGroup.getDescription());
        dto.setCategory(studyGroup.getCategory());
        dto.setCreatedBy(studyGroup.getCreatedBy());
        dto.setCreatedAt(studyGroup.getCreatedAt());
        dto.setUpdatedAt(studyGroup.getUpdatedAt());
        dto.setEndDate(studyGroup.getEndDate());
        dto.setMaxMentor(studyGroup.getMaxMentor());
        dto.setMaxMentee(studyGroup.getMaxMentee());
        dto.setMentorCount(studyGroup.getMentorCount());
        dto.setMenteeCount(studyGroup.getMenteeCount());

        return dto;
    }
}
