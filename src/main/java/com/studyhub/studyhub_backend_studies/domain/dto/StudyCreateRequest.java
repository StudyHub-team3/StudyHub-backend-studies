package com.studyhub.studyhub_backend_studies.domain.dto;

import com.studyhub.studyhub_backend_studies.common.web.context.GatewayRequestHeaderUtils;
import com.studyhub.studyhub_backend_studies.domain.StudyGroup;
import com.studyhub.studyhub_backend_studies.domain.StudyGroupCategory;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class StudyCreateRequest {

    @NotBlank(message = "그룹명을 입력하세요")
    private String name;
    private String description;
    @NotBlank(message = "카테고리를 입력하세요")
    private StudyGroupCategory category;
    @NotBlank(message = "최대 멘토수를 입력하세요")
    private int maxMentor;
    @NotBlank(message = "최대 멘티수를 입력하세요")
    private int maxMentee;
    @NotBlank(message = "끝나는 날짜를 입력하세요")
    private LocalDate endDate;

    public StudyGroup toEntity() {
        StudyGroup studyGroup = new StudyGroup();

        studyGroup.setCreatedBy(GatewayRequestHeaderUtils.getUserIdOrThrowException());
        studyGroup.setDescription(this.description);
        studyGroup.setEndDate(this.endDate);
        studyGroup.setGroupName(this.name);
        studyGroup.setMaxMentor(this.maxMentor);
        studyGroup.setMaxMentee(this.maxMentee);
        studyGroup.setCategory(this.category);

        return studyGroup;
    }


}
