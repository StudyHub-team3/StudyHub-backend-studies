package com.studyhub.studyhub_backend_study.api;

import com.studyhub.studyhub_backend_study.domain.StudyGroup;
import com.studyhub.studyhub_backend_study.domain.StudyGroupCategory;
import com.studyhub.studyhub_backend_study.event.producer.KafkaMessageProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/test/kafka")
public class ConsumerTestController {

    private final KafkaMessageProducer kafkaMessageProducer;

    @PostMapping("/create")
    public String testCreateStudyEvent() {
        log.info("✅ 컨트롤러 진입 확인");
        StudyGroup studyGroup = StudyGroup.builder()
            .createdBy(101L)
            .groupName("Kafka 테스트 스터디")
            .description("테스트용 설명입니다")
            .category(StudyGroupCategory.AI)
            .endDate(LocalDate.now().plusDays(30))
            .maxMentor(3)
            .maxMentee(5)
            .build();
        studyGroup.setId(1L);

        String userName = "홍길동";
        String role = "MENTOR";

        kafkaMessageProducer.sendCreateStudyGroupEvent(studyGroup, userName, role);
        return "✅ STUDY_CREATED 이벤트 전송 완료";
    }

    @PostMapping("/delete")
    public String testDeleteStudyEvent() {
        log.info("✅ 컨트롤러 진입 확인");
        StudyGroup studyGroup = StudyGroup.builder()
            .createdBy(101L)
            .groupName("삭제용 스터디")
            .description("삭제 테스트 설명")
            .category(StudyGroupCategory.SPRING_BOOT)
            .endDate(LocalDate.now().plusDays(30))
            .maxMentor(2)
            .maxMentee(4)
            .build();
        studyGroup.setId(1L);

        kafkaMessageProducer.sendDeleteStudyGroupEvent(studyGroup);
        return "✅ STUDY_DELETED 이벤트 전송 완료";
    }
}