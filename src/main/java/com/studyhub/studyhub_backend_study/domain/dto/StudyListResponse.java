package com.studyhub.studyhub_backend_study.domain.dto;

import com.studyhub.studyhub_backend_study.domain.StudyGroup;
import com.studyhub.studyhub_backend_study.domain.StudyGroupCategory;
import com.studyhub.studyhub_backend_study.domain.StudyStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class StudyListResponse {

    private String groupName;
    private String description;
    private StudyGroupCategory category;
    private StudyStatus status;

    private int mentorCount;
    private int maxMentor;

    private int menteeCount;
    private int maxMentee;

    public static StudyListResponse of(StudyGroup studyGroup) {
        StudyListResponse dto = new StudyListResponse();
        dto.setGroupName(studyGroup.getGroupName());
        dto.setDescription(studyGroup.getDescription());
        dto.setCategory(studyGroup.getCategory());
        dto.setMentorCount(studyGroup.getMentorCount());
        dto.setMaxMentor(studyGroup.getMaxMentor());
        dto.setMenteeCount(studyGroup.getMenteeCount());
        dto.setMaxMentee(studyGroup.getMaxMentee());
        dto.setStatus(studyGroup.getStatus());
        return dto;
    }

}
