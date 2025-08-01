package com.studyhub.studyhub_backend_study.domain.event;

import com.studyhub.studyhub_backend_study.domain.StudyGroup;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class StudyInfoEvent {

    public static final String topic = "studyinfo";

    private String action;
    private Long userId;

    public static StudyInfoEvent fromEntity(String action, StudyGroup studyGroup) {

        StudyInfoEvent event = new StudyInfoEvent();

        event.action = action;
        event.userId = studyGroup.getCreatedBy();

        return event;
    }
}
