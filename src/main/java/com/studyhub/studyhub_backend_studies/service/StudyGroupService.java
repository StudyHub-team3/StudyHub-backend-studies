package com.studyhub.studyhub_backend_studies.service;

import com.studyhub.studyhub_backend_studies.common.exception.NotFound;
import com.studyhub.studyhub_backend_studies.common.exception.Unauthorized;
import com.studyhub.studyhub_backend_studies.common.web.context.GatewayRequestHeaderUtils;
import com.studyhub.studyhub_backend_studies.domain.StudyGroup;
import com.studyhub.studyhub_backend_studies.domain.dto.*;
import com.studyhub.studyhub_backend_studies.domain.repository.StudyGroupRepository;
import com.studyhub.studyhub_backend_studies.event.producer.service.StudyGroupProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudyGroupService {

    private final StudyGroupRepository studyGroupRepository;
    private final StudyGroupProducerService studyGroupProducerService;

    @Transactional
    public Map<String, Object> createStudyGroup(StudyCreateRequest request) {
        StudyGroup studyGroup = request.toEntity();
        studyGroupRepository.save(studyGroup);
        studyGroupProducerService.sendCreateStudyGroupEvent(studyGroup);

        return Map.of(
                "studyId", studyGroup.getId(),
                "createdAt", studyGroup.getCreatedAt().toString()
        );
    }

    @Transactional
    public Map<String, Object> updateStudyGroup(Long id, StudyUpdateRequest request) {
        StudyGroup studyGroup = studyGroupRepository.findById(id)
                .orElseThrow(() -> new NotFound("스터디가 존재 하지 않습니다"));

        Long userId = GatewayRequestHeaderUtils.getUserIdOrThrowException();

        if (!studyGroup.getCreatedBy().equals(userId)) {
            throw new Unauthorized("수정 권한이 없습니다");
        }

        studyGroup.setGroupName(request.getName());
        studyGroup.setDescription(request.getDescription());
        studyGroup.setEndDate(request.getEndDate());

        return Map.of(
                "studyId", studyGroup.getId(),
                "updatedAt", studyGroup.getUpdatedAt().toString()
        );
    }

    @Transactional
    public Map<String, Object> deleteStudyGroup(Long id) {
        StudyGroup studyGroup = studyGroupRepository.findById(id)
                .orElseThrow(() -> new NotFound("스터디가 존재 하지 않습니다"));

        Long userId = GatewayRequestHeaderUtils.getUserIdOrThrowException();

        if (!studyGroup.getCreatedBy().equals(userId)) {
            throw new Unauthorized("삭제 권한이 없습니다");
        }

        studyGroupRepository.delete(studyGroup);
        studyGroupProducerService.sendDeleteStudyGroupEvent(studyGroup);

        return Map.of(
                "studyId", studyGroup.getId()
        );
    }

    public StudyDetailResponse getStudyGroup(Long id){

        StudyGroup studyGroup = studyGroupRepository.findById(id)
                .orElseThrow(() -> new NotFound("스터디가 존재 하지 않습니다"));

        return StudyDetailResponse.fromEntity(studyGroup);

    }

    public List<StudyListResponse> getAllStudyGroups() {
        return studyGroupRepository.findAll().stream()
                .map(StudyListResponse::of)
                .collect(Collectors.toList());
    }

    public List<StudyListResponse> searchStudies(StudySearchCondition condition) {
        return studyGroupRepository.findAll().stream()
                .filter(study -> {
                    if (condition.getGroupName() != null && !condition.getGroupName().isBlank()) {
                        if (!study.getGroupName().contains(condition.getGroupName())) {
                            return false;
                        }
                    }

                    if (condition.getCategory() != null) {
                        if (study.getCategory() != condition.getCategory()) {
                            return false;
                        }
                    }

                    return true;
                })
                .map(StudyListResponse::of)
                .collect(Collectors.toList());
    }

    public List<StudyListResponse> getMyStudyGroups(){

        Long userId = GatewayRequestHeaderUtils.getUserIdOrThrowException();

        return studyGroupRepository.findAllByCreatedBy(userId).stream()
                .map(StudyListResponse::of)
                .collect(Collectors.toList());
    }

    public StudyGroup findByIdOrThrow(Long studyId) {
        return studyGroupRepository.findById(studyId)
                .orElseThrow(() -> new NotFound("스터디가 존재하지 않습니다."));
    }

    public boolean existsById(Long studyId) {
        return studyGroupRepository.existsById(studyId);
    }

}
