package com.studyhub.studyhub_backend_studies.api.backend;

import com.studyhub.studyhub_backend_studies.common.dto.ApiResponseDto;
import com.studyhub.studyhub_backend_studies.domain.StudyGroup;
import com.studyhub.studyhub_backend_studies.domain.dto.InternalStudyInfoResponse;
import com.studyhub.studyhub_backend_studies.service.StudyGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "backend/studygroup")
@RequiredArgsConstructor
public class BackendController {

    private final StudyGroupService studyGroupService;

    @GetMapping("/{studyId}")
    public ApiResponseDto<InternalStudyInfoResponse> getStudyInfoForInternalUse(@PathVariable Long studyId) {
        StudyGroup studyGroup = studyGroupService.findByIdOrThrow(studyId);
        return ApiResponseDto.createOk(InternalStudyInfoResponse.from(studyGroup));
    }


    @GetMapping("/{studyId}/exists")
    public ApiResponseDto<Boolean> checkStudyExists(@PathVariable Long studyId) {
        boolean exists = studyGroupService.existsById(studyId);
        return ApiResponseDto.createOk(exists);
    }

}
