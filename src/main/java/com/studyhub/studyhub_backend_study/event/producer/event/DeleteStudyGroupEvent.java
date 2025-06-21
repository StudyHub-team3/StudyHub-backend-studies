package com.studyhub.studyhub_backend_study.event.producer.event;

import com.studyhub.studyhub_backend_study.domain.StudyGroup;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeleteStudyGroupEvent {

    public static final String Topic = "study_group_delete";

    private Long id;           // 삭제된 StudyGroup ID
    private String groupName;  // 삭제 당시 그룹명 (로그나 알림에 필요할 수 있음)
    private Long deletedBy;    // 삭제 요청자 ID
    private LocalDateTime deletedAt; // 삭제 시각

    public static DeleteStudyGroupEvent fromEntity(StudyGroup studyGroup) {
        return DeleteStudyGroupEvent.builder()
                .id(studyGroup.getId())
                .groupName(studyGroup.getGroupName())
                .deletedBy(studyGroup.getCreatedBy())
                .deletedAt(LocalDateTime.now())
                .build();
    }
}
