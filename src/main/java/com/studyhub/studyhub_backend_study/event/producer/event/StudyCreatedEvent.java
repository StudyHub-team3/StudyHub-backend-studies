package com.studyhub.studyhub_backend_study.event.producer.event;

import com.studyhub.studyhub_backend_study.domain.StudyGroup;
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
public class StudyCreatedEvent {

    public static final String Topic = "study_group";

    private Long studyId;
    private Long userId;


    public static StudyCreatedEvent fromEntity(StudyGroup studyGroup) {
        return StudyCreatedEvent.builder()
                .studyId(studyGroup.getId())
                .userId(studyGroup.getCreatedBy())
                .build();
    }
}
