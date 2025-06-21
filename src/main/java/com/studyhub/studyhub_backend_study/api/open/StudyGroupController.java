package com.studyhub.studyhub_backend_study.api.open;

import com.studyhub.studyhub_backend_study.common.dto.ApiResponseDto;
import com.studyhub.studyhub_backend_study.domain.dto.*;
import com.studyhub.studyhub_backend_study.service.StudyGroupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/studies")
@RequiredArgsConstructor
public class StudyGroupController {

    private final StudyGroupService studyGroupService;

    @GetMapping
    public ApiResponseDto<List<StudyListResponse>> getStudyGroupList() {
        return ApiResponseDto.createOk(studyGroupService.getAllStudyGroups());
    }

    @GetMapping("/search")
    public ApiResponseDto<List<StudyListResponse>> searchStudyGroupList(@ModelAttribute StudySearchCondition condition) {
        return ApiResponseDto.createOk(studyGroupService.searchStudies(condition));
    }

    @GetMapping("/my")
    public ApiResponseDto<List<StudyListResponse>> getMyStudyGroupList() {
        return ApiResponseDto.createOk(studyGroupService.getMyStudyGroups());
    }

    @PostMapping
    public ApiResponseDto<Map<String, Object>> createStudyGroup(@RequestBody @Valid StudyCreateRequest request) {
        studyGroupService.createStudyGroup(request);
        return ApiResponseDto.createOk(studyGroupService.createStudyGroup(request));
    }

    @GetMapping("/{studyId}")
    public ApiResponseDto<StudyDetailResponse> getStudyGroupDetail(@PathVariable Long studyId) {
        return ApiResponseDto.createOk(studyGroupService.getStudyGroup(studyId));
    }

    @PutMapping(value = "/{studyId}")
    public ApiResponseDto<Map<String, Object>> editStudyGroup(@RequestBody @Valid StudyUpdateRequest request, @PathVariable Long studyId){
        studyGroupService.updateStudyGroup(studyId,request);
        return ApiResponseDto.createOk(studyGroupService.updateStudyGroup(studyId,request));
    }

    @DeleteMapping(value="/{studyId}")
    public ApiResponseDto<Map<String, Object>> deleteStudyGroup(@PathVariable @Valid Long studyId) {
        studyGroupService.deleteStudyGroup(studyId);
        return ApiResponseDto.createOk(studyGroupService.deleteStudyGroup(studyId));
    }
}
