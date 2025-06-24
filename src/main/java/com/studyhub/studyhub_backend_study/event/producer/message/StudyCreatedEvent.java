package com.studyhub.studyhub_backend_study.event.producer.message;

import com.studyhub.studyhub_backend_study.domain.StudyGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyCreatedEvent {

    public static final String Topic = "study";

    private Long studyId;
    private Long userId;


    public static StudyCreatedEvent fromEntity(StudyGroup studyGroup) {
        return StudyCreatedEvent.builder()
                .studyId(studyGroup.getId())
                .userId(studyGroup.getCreatedBy())
                .build();
    }
}
