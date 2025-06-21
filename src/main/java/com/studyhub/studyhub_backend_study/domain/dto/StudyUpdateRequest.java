package com.studyhub.studyhub_backend_study.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class StudyUpdateRequest {

    @NotBlank(message = "그룹명을 입력하세요")
    private String name;
    private String description;
    private LocalDate endDate;

}
