package com.studyhub.studyhub_backend_studies.domain.dto;

import com.studyhub.studyhub_backend_studies.common.web.context.GatewayRequestHeaderUtils;
import com.studyhub.studyhub_backend_studies.domain.StudyGroup;
import com.studyhub.studyhub_backend_studies.domain.StudyGroupCategory;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@Builder
public class StudyCreateRequest {

    @NotBlank(message = "그룹명을 입력하세요")
    private String groupName;
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
        return StudyGroup.builder()
                .createdBy(GatewayRequestHeaderUtils.getUserIdOrThrowException())
                .groupName(this.groupName)
                .description(this.description)
                .category(this.category)
                .endDate(this.endDate)
                .maxMentor(this.maxMentor)
                .maxMentee(this.maxMentee)
                .build();
    }


}
