package com.studyhub.studyhub_backend_study.event.producer.message;

import com.studyhub.studyhub_backend_study.domain.StudyGroup;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudyCreatedEvent {

    public static final String Topic = "study";

    private String eventType;
    private Data data;
    private String timestamp;

    @Getter
    @Setter
    public static class Data {
        private Long studyId;
        private Long userId;
        private String userName;
        private String creatorRole;
    }

    public static StudyCreatedEvent fromEntity(StudyGroup studyGroup, String userName, String creatorRole, String timestamp) {
        Data data = new Data();
        data.setStudyId(studyGroup.getId());
        data.setUserId(studyGroup.getCreatedBy());
        data.setUserName(userName);
        data.setCreatorRole(creatorRole);

        StudyCreatedEvent event = new StudyCreatedEvent();
        event.setEventType("STUDY_CREATED");
        event.setData(data);
        event.setTimestamp(timestamp);
        return event;
    }
}
