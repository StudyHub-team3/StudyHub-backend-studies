package com.studyhub.studyhub_backend_study.domain.dto;

import com.studyhub.studyhub_backend_study.common.web.context.GatewayRequestHeaderUtils;
import com.studyhub.studyhub_backend_study.domain.StudyGroup;
import com.studyhub.studyhub_backend_study.domain.StudyGroupCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudyCreateRequest {

    @NotBlank(message = "그룹명을 입력하세요")
    private String groupName;

    private String description;

    @NotNull(message = "카테고리를 입력하세요")
    private StudyGroupCategory category;

    @NotNull(message = "최대 멘토수를 입력하세요")
    private Integer maxMentor;

    @NotNull(message = "최대 멘티수를 입력하세요")
    private Integer maxMentee;

    @NotBlank(message = "자기 롤을 입력하세요.")
    private String creatorRole;

    @NotNull(message = "끝나는 날짜를 입력하세요")
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