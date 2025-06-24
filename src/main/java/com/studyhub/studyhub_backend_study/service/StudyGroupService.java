package com.studyhub.studyhub_backend_study.service;

import com.studyhub.studyhub_backend_study.common.exception.NotFound;
import com.studyhub.studyhub_backend_study.common.exception.Unauthorized;
import com.studyhub.studyhub_backend_study.common.web.context.GatewayRequestHeaderUtils;
import com.studyhub.studyhub_backend_study.domain.StudyGroup;
import com.studyhub.studyhub_backend_study.domain.dto.*;
import com.studyhub.studyhub_backend_study.domain.repository.StudyGroupRepository;
import com.studyhub.studyhub_backend_study.event.consumer.message.StudyCrewEvent;
import com.studyhub.studyhub_backend_study.event.producer.KafkaMessageProducer;
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
    private final KafkaMessageProducer kafkaMessageProducer;

    @Transactional
    public Map<String, Object> createStudyGroup(StudyCreateRequest request, String userName) {
        StudyGroup studyGroup = request.toEntity();

        String role = request.getCreatorRole();
        if ("MENTOR".equalsIgnoreCase(role)) {
            studyGroup.setMentorCount(1);
            studyGroup.setMenteeCount(0);
        } else if ("MENTEE".equalsIgnoreCase(role)) {
            studyGroup.setMentorCount(0);
            studyGroup.setMenteeCount(1);
        } else {
            // 유효하지 않은 값 처리
            throw new IllegalArgumentException("Invalid role: " + role);
        }

        studyGroupRepository.save(studyGroup);

        kafkaMessageProducer.sendCreateStudyGroupEvent(studyGroup,userName,role);

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
        kafkaMessageProducer.sendDeleteStudyGroupEvent(studyGroup);

        return Map.of(
                "studyId", studyGroup.getId()
        );
    }

    @Transactional
    public StudyDetailResponse getStudyGroup(Long id) {
        StudyGroup studyGroup = studyGroupRepository.findById(id)
                .orElseThrow(() -> new NotFound("스터디가 존재 하지 않습니다"));

        studyGroup.updateStatusIfExpired(); // 상태 갱신

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

    public void handleMemberJoin(StudyCrewEvent event) {
        StudyGroup study = studyGroupRepository.findById(event.getStudyId())
                .orElseThrow(() -> new RuntimeException("스터디 없음"));

        if (study.isFull()) {
            // 정원이 꽉 찬 경우 예외 처리 또는 로그만 남기고 리턴
            log.warn("스터디 정원 초과로 추가 불가 - studyId: {}", event.getStudyId());
            return;
        }

        if ("MENTOR".equalsIgnoreCase(event.getRole())) {
            study.increaseMentorCount();
        } else if ("MENTEE".equalsIgnoreCase(event.getRole())) {
            study.increaseMenteeCount();
        }

        studyGroupRepository.save(study);
    }

    public void handleMemberQuit(Long studyId, String role) {
        StudyGroup group = studyGroupRepository.findById(studyId)
                .orElseThrow(() -> new RuntimeException("스터디 없음"));

        if ("MENTOR".equalsIgnoreCase(role)) {
            group.decreaseMentorCount();
        } else if ("MENTEE".equalsIgnoreCase(role)) {
            group.decreaseMenteeCount();
        }

        studyGroupRepository.save(group);
    }

}
