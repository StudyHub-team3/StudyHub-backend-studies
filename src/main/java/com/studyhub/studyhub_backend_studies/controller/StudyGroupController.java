package com.studyhub.studyhub_backend_studies.controller;

import com.studyhub.studyhub_backend_studies.common.dto.ApiResponseDto;
import com.studyhub.studyhub_backend_studies.service.StudyGroupService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/studies")
@RequiredArgsConstructor
public class StudyGroupController {

    private final StudyGroupService studyGroupService;

    @GetMapping
    public ApiResponseDto<String> getStudyList(){
        return ApiResponseDto.defaultOk();
    }

    @PostMapping
    public ApiResponseDto<String> createStudyGroup() {
        return ApiResponseDto.defaultOk();
    }

    @GetMapping(value = "/{studyId}")
    public ApiResponseDto<String> getStudyDetail() {
        return ApiResponseDto.defaultOk();
    }

    @PutMapping(value = "/studyId")
    public ApiResponseDto<String> editStudyDetail(){
        return ApiResponseDto.defaultOk();
    }

    @DeleteMapping(value="{studyId}")
    public ApiResponseDto<String> deleteStudyDetail() {
        return ApiResponseDto.defaultOk();
    }



}
