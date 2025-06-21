package com.studyhub.studyhub_backend_studies.service;

import com.studyhub.studyhub_backend_studies.common.web.context.GatewayRequestHeaderUtils;
import com.studyhub.studyhub_backend_studies.domain.StudyGroup;
import com.studyhub.studyhub_backend_studies.domain.dto.StudyCreateRequest;
import com.studyhub.studyhub_backend_studies.domain.repository.StudyGroupRepository;
import com.studyhub.studyhub_backend_studies.domain.StudyGroupCategory;
import com.studyhub.studyhub_backend_studies.event.producer.service.StudyGroupProducerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudyGroupServiceTest {

    @Mock
    private StudyGroupRepository studyGroupRepository;

    @Mock
    private StudyGroupProducerService studyGroupProducerService;

    @InjectMocks
    private StudyGroupService studyGroupService;


    @Test
    @DisplayName("스터디 그룹 생성")
    public void createStudyGroup() throws Exception {
        // given
        try (MockedStatic<GatewayRequestHeaderUtils> mockedStatic = mockStatic(GatewayRequestHeaderUtils.class)) {
            mockedStatic.when(GatewayRequestHeaderUtils::getUserIdOrThrowException)
                    .thenReturn(1L); // 유저 ID 고정

            StudyCreateRequest request = StudyCreateRequest.builder()
                    .groupName("Java 스터디")
                    .description("자바 마스터 스터디")
                    .category(StudyGroupCategory.AI)
                    .endDate(LocalDate.now().plusWeeks(2))
                    .maxMentor(2)
                    .maxMentee(3)
                    .build();

            // 저장된 StudyGroup에 createdAt, id, updatedAt 설정
            when(studyGroupRepository.save(any())).thenAnswer(invocation -> {
                StudyGroup sg = invocation.getArgument(0);
                sg.setId(100L);
                sg.setCreatedAt(LocalDateTime.now());
                sg.setUpdatedAt(LocalDateTime.now());
                return sg;
            });

            // when
            Map<String, Object> result = studyGroupService.createStudyGroup(request);

            // then
            verify(studyGroupRepository).save(any(StudyGroup.class));
            verify(studyGroupProducerService).sendCreateStudyGroupEvent(any(StudyGroup.class));
            assertThat(result).containsKeys("studyId", "createdAt");
        }
    }

    @Test
    @DisplayName("스터디 그룹 수정")
    void updateStudyGroup() throws Exception{
        //given

        //when

        //then
    }

    @Test
    @DisplayName("스터디 그룹 삭제")
    void deleteStudyGroup() throws Exception{
        //given

        //when

        //then
    }

    @Test
    @DisplayName("스터디 그룹 상세 조회")
    void getStudyGroup() throws Exception{
        //given

        //when

        //then
    }

    @Test
    @DisplayName("스터디 그룹 목록 조회")
    void getAllStudyGroups() throws Exception{
        //given

        //when

        //then
    }

    @Test
    @DisplayName("스터디 그룹 검색")
    void searchStudies() throws Exception{
        //given

        //when

        //then
    }

    @Test
    @DisplayName("내 스터디 그룹 목록 조회")
    void getMyStudyGroups() throws Exception{
        //given

        //when

        //then
    }

    @Test
    @DisplayName("스터디 그룹 존재 확인")
    void existsById() throws Exception{
        //given

        //when

        //then
    }
}
