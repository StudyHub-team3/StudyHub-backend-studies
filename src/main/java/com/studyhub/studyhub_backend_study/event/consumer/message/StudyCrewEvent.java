package com.studyhub.studyhub_backend_study.event.consumer.message;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudyCrewEvent {

    private String eventType; // "STUDY_CREATED", "STUDY_DELETED"
    private Data data;
    private String timestamp;

    public static final String Topic = "study-member";

    @Getter
    @Setter
    public static class Data {
        private Long studyId;
        private Long userId;
        private String userName;
        private String role;

        @Override
        public String toString() {
            return " {\n" +
                    "\t\tstudyId=" + studyId + '\n' +
                    "\t\tuserId=" + userId + '\n' +
                    "\t\tuserName=" + userName + '\n' +
                    "\t\trole=" + role + '\n' +
                    "\t}";
        }
    }

    @Override
    public String toString() {
        return "\nStudyEvent{  \n" +
                "\teventType='" + eventType + '\n' +
                "\tdata=" + data + '\n' +
                "\ttimestamp='" + timestamp + '\n' +
                '}';
    }
}