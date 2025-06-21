package com.studyhub.studyhub_backend_studies.event.producer.event;

import com.studyhub.studyhub_backend_studies.domain.StudyGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateStudyGroupEvent {

    public static final String Topic = "study_group";

    private Long id;
    private String groupName;
    private String description;
    private String category;
    private Long createdBy;
    private int maxMentor;
    private int maxMentee;
    private LocalDate endDate;
    private LocalDateTime createdAt;

    public static CreateStudyGroupEvent fromEntity(StudyGroup studyGroup) {
        return CreateStudyGroupEvent.builder()
                .id(studyGroup.getId())
                .groupName(studyGroup.getGroupName())
                .description(studyGroup.getDescription())
                .category(studyGroup.getCategory().name()) // enum -> string
                .createdBy(studyGroup.getCreatedBy())
                .maxMentor(studyGroup.getMaxMentor())
                .maxMentee(studyGroup.getMaxMentee())
                .endDate(studyGroup.getEndDate())
                .createdAt(studyGroup.getCreatedAt())
                .build();
    }
}
