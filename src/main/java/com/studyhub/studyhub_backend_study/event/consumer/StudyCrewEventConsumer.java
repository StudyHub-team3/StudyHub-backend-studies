package com.studyhub.studyhub_backend_study.event.consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.studyhub.studyhub_backend_study.event.KafkaEvent;
import com.studyhub.studyhub_backend_study.service.StudyGroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class StudyCrewEventConsumer {

    private final StudyGroupService studyGroupService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "study", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(String message, Acknowledgment ack) {
        try {
            KafkaEvent<StudyCrewEvent> event = objectMapper.readValue(
                    message,
                    new TypeReference<>() {}
            );

            StudyCrewEvent data = event.getData();
            String eventType = event.getEventType();

            switch (eventType) {
                case "STUDY_CREW_JOINED" -> {
                    studyGroupService.handleMemberJoin(data);
                }
                case "STUDY_CREW_QUITED" -> {
                    studyGroupService.handleMemberQuit(data.getStudyId(), data.getRole());
                }
                default -> log.warn("알 수 없는 이벤트 타입: {}", eventType);
            }

            ack.acknowledge(); // ✅ 메시지 정상 처리 후 커밋

        } catch (Exception e) {
            log.error("Kafka 수신 처리 실패", e);
        }
    }
}