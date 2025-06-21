package com.studyhub.studyhub_backend_studies.domain.dto;

import com.studyhub.studyhub_backend_studies.domain.StudyGroupCategory;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class StudySearchCondition {

    private String groupName; // 검색어 (스터디명)
    private StudyGroupCategory category; // 카테고리

}
