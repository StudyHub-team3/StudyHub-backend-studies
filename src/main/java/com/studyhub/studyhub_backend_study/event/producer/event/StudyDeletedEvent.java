package com.studyhub.studyhub_backend_study.event.producer.event;

import com.studyhub.studyhub_backend_study.domain.StudyGroup;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyDeletedEvent {

    public static final String Topic = "study_group";

    private Long studyId;
    private Long userId;


    public static StudyDeletedEvent fromEntity(StudyGroup studyGroup) {
        return StudyDeletedEvent.builder()
                .studyId(studyGroup.getId())
                .userId(studyGroup.getCreatedBy())
                .build();
    }
}
