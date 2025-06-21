package com.studyhub.studyhub_backend_studies.domain.dto;

import com.studyhub.studyhub_backend_studies.domain.StudyGroup;
import com.studyhub.studyhub_backend_studies.domain.StudyGroupCategory;
import lombok.Getter;
import lombok.Setter;

import java.util.stream.Collectors;

@Getter @Setter
public class StudyListResponse {

    private String groupName;
    private String description;
    private StudyGroupCategory category;

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
        return dto;
    }

}
