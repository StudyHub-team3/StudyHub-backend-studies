package com.studyhub.studyhub_backend_study.domain.dto;

import com.studyhub.studyhub_backend_study.domain.StudyGroup;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class InternalStudyInfoResponse {
    private Long studyId;
    private String groupName;
    private String category;
    private String status;
    private LocalDateTime createdAt;

    public static InternalStudyInfoResponse from(StudyGroup studyGroup) {
        return new InternalStudyInfoResponse(
                studyGroup.getId(),
                studyGroup.getGroupName(),
                studyGroup.getCategory().name(),
                studyGroup.getStatus().name(),
                studyGroup.getCreatedAt()
        );
    }
}