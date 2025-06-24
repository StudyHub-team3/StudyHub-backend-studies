package com.studyhub.studyhub_backend_study.event.producer.message;

import com.studyhub.studyhub_backend_study.domain.StudyGroup;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudyDeletedEvent {

    private String eventType; // e.g. "STUDY_DELETED"
    private Data data;
    private String timestamp;

    public static final String Topic = "study";

    @Getter
    @Setter
    @Builder
    public static class Data {
        private Long studyId;
        private Long userId;

        @Override
        public String toString() {
            return " {\n" +
                    "\t\tstudyId=" + studyId + '\n' +
                    "\t\tuserId=" + userId + '\n' +
                    "\t}";
        }
    }

    @Override
    public String toString() {
        return "\nStudyDeletedEvent{  \n" +
                "\teventType='" + eventType + '\n' +
                "\tdata=" + data + '\n' +
                "\ttimestamp='" + timestamp + '\n' +
                '}';
    }

    public static StudyDeletedEvent fromEntity(StudyGroup studyGroup) {
        return StudyDeletedEvent.builder()
                .eventType("STUDY_DELETED")
                .data(Data.builder()
                        .studyId(studyGroup.getId())
                        .userId(studyGroup.getCreatedBy())
                        .build())
                .timestamp(String.valueOf(System.currentTimeMillis()))
                .build();
    }
}
